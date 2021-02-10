package com.fab.wallet.transaction.service;

import com.fab.wallet.Reply;
import com.fab.wallet.transaction.dto.PageableResponse;
import com.fab.wallet.transaction.dto.PassBookFilter;
import com.fab.wallet.transaction.dto.TransactionDTO;
import com.fab.wallet.transaction.dto.TransactionResponseDTO;
import com.fab.wallet.transaction.entities.TransactionTbl;
import com.fab.wallet.transaction.repo.TransactionRepo;
import com.fab.wallet.user.entities.UserTbl;
import com.fab.wallet.user.repo.UserRepo;
import com.fab.wallet.util.Const;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;
    private final EntityManager entityManager;
    public TransactionService(TransactionRepo transactionRepo, UserRepo userRepo, EntityManager entityManager) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
        this.entityManager = entityManager;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void addMoneyToWallet(TransactionDTO transactionDTO, Reply reply){
        Optional<UserTbl> optionalUserTbl=userRepo.findByIdWithLock(reply.getFromSession(Const.SessionEnum.LOGIN_ID.getKey()).toString());
        if(!optionalUserTbl.isPresent()){
            reply.setMessage("INVALID ID", Const.MessageType.ERROR);
            return ;
        }
        UserTbl userTbl=optionalUserTbl.get();
        userTbl.setWalletBalance(userTbl.getWalletBalance()+transactionDTO.getAmount());
        TransactionTbl transactionTbl=new TransactionTbl();
        transactionTbl.setToUser(optionalUserTbl.get().getUserId());
        transactionTbl.setTxnType(Const.TransactionType.ADD_MONEY.getFEConst());
        transactionTbl.setPaymentMode(transactionDTO.getPaymentMode().getFEConst());
        transactionTbl.setTxnDateTime(LocalDateTime.now());
        transactionTbl.setTxnAmount(transactionDTO.getAmount());
        transactionRepo.save(transactionTbl);
        userRepo.save(userTbl);
        reply.setMessage("MONEY ADDED SUCCESSFULLY!!", Const.MessageType.INFO);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void transferFromWallet(TransactionDTO transactionDTO, Reply reply) {
        Optional<UserTbl> optionalFromUserTbl = userRepo.findByIdWithLock(reply.getFromSession(Const.SessionEnum.LOGIN_ID.getKey()).toString());
        if (!optionalFromUserTbl.isPresent()) {
            reply.setMessage("INVALID ID", Const.MessageType.ERROR);
            return;
        }
        Optional<UserTbl> optionalToUserTbl = userRepo.findByIdWithLock(transactionDTO.getToUser());
        if (!optionalToUserTbl.isPresent()) {
            reply.setMessage("INVALID TO ID", Const.MessageType.ERROR);
            return;
        }
        UserTbl fromUserTbl=optionalFromUserTbl.get();
        if(fromUserTbl.getWalletBalance()<transactionDTO.getAmount()){
            reply.setMessage("INSUFFICIENT BALANCE", Const.MessageType.ERROR);
            return;
        }
        UserTbl toUserTbl=optionalToUserTbl.get();
        fromUserTbl.setWalletBalance(fromUserTbl.getWalletBalance()-transactionDTO.getAmount());
        toUserTbl.setWalletBalance(toUserTbl.getWalletBalance()+transactionDTO.getAmount());
        TransactionTbl transactionTbl=new TransactionTbl();
        transactionTbl.setToUser(toUserTbl.getUserId());
        transactionTbl.setFromUser(fromUserTbl.getUserId());
        transactionTbl.setTxnType(Const.TransactionType.WALLET_TO_WALLET.getFEConst());
        transactionTbl.setTxnDateTime(LocalDateTime.now());
        transactionTbl.setTxnAmount(transactionDTO.getAmount());
        transactionRepo.save(transactionTbl);
        userRepo.save(fromUserTbl);
        userRepo.save(toUserTbl);
        reply.setMessage("MONEY TRANSFERRED SUCCESSFULLY!!", Const.MessageType.INFO);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void getTransactions(PassBookFilter passBookFilter, Reply reply) {
        PageableResponse response =transactionRepo.getTransactions(entityManager,passBookFilter,reply.getFromSession(Const.SessionEnum.LOGIN_ID.getKey()).toString());
        if(((List<TransactionResponseDTO>)response.getResponse()).isEmpty()){
            reply.setMessage("NO TRANSACTION FOUND!!", Const.MessageType.ERROR);
            return;
        }
        reply.setData(response);
    }

    }
