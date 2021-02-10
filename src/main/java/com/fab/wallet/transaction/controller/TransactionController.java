package com.fab.wallet.transaction.controller;

import com.fab.wallet.Reply;
import com.fab.wallet.transaction.dto.PassBookFilter;
import com.fab.wallet.transaction.dto.TransactionDTO;
import com.fab.wallet.transaction.service.TransactionService;
import com.fab.wallet.util.TokenUtil;
import com.sun.istack.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TokenUtil tokenUtil;
    private final TransactionService transactionService;

    public TransactionController(TokenUtil tokenUtil, TransactionService transactionService) {
        this.tokenUtil = tokenUtil;
        this.transactionService = transactionService;
    }

    @PostMapping("/addMoney")
    public Reply addMoneyToWallet(@RequestBody @NotNull @Valid TransactionDTO transactionDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Reply reply=tokenUtil.getReply(token);
        transactionService.addMoneyToWallet(transactionDTO,reply);
        return reply;
    }
    @PostMapping("/walletTransfer")
    public Reply transferFromWallet(@RequestBody @NotNull @Valid TransactionDTO transactionDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Reply reply=tokenUtil.getReply(token);
        transactionService.transferFromWallet(transactionDTO,reply);
        return reply;
    }
    @PostMapping("/getTransactions")
    public Reply getTransactions(@RequestBody @NotNull @Valid PassBookFilter passBookFilter, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Reply reply=tokenUtil.getReply(token);
        transactionService.getTransactions(passBookFilter,reply);
        return reply;
    }
}
