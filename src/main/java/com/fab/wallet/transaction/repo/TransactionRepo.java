package com.fab.wallet.transaction.repo;

import com.fab.wallet.transaction.dto.PageableResponse;
import com.fab.wallet.transaction.dto.PassBookFilter;
import com.fab.wallet.transaction.dto.TransactionResponseDTO;
import com.fab.wallet.transaction.entities.TransactionTbl;
import com.fab.wallet.util.Const;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepo extends CrudRepository<TransactionTbl,Integer> {

    default PageableResponse getTransactions(EntityManager entityManager, PassBookFilter passBookFilter, String userId){
        List<TransactionResponseDTO> transactionResponseDTOS=new ArrayList<>();
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionResponseDTO> cq=cb.createQuery(TransactionResponseDTO.class);
        Root<TransactionTbl> root=cq.from(TransactionTbl.class);
        cq.multiselect(root.get("txnType"),root.get("paymentMode"),root.get("txnDateTime"),root.get("fromUser"),
                root.get("toUser"),root.get("txnAmount"));
        List<Predicate> predicates=new LinkedList<>();
        if(passBookFilter.getFromDate()!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("txnDateTime"), LocalDateTime.of(passBookFilter.getFromDate(), LocalTime.MIN)));
        }
        if(passBookFilter.getToDate()!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("txnDateTime"), LocalDateTime.of(passBookFilter.getFromDate(), LocalTime.MAX)));
        }
        Predicate debitPredicate=null;
        Predicate creditPredicate=null;
        if(passBookFilter.isDebitTransactions()){
            debitPredicate = cb.equal(root.get("fromUser"),userId);
        }
        if(passBookFilter.isAddMoneyTransactions() && passBookFilter.isWalletToWalletTransactions()){
            creditPredicate=cb.equal(root.get("toUser"),userId);
        }else if(passBookFilter.isAddMoneyTransactions()){
            creditPredicate=cb.and(cb.equal(root.get("toUser"),userId),cb.equal(root.get("txnType"), Const.TransactionType.ADD_MONEY.getFEConst()));
        }else if(passBookFilter.isWalletToWalletTransactions()){
            creditPredicate=cb.and(cb.equal(root.get("toUser"),userId),cb.equal(root.get("txnType"), Const.TransactionType.WALLET_TO_WALLET.getFEConst()));
        }else if(!passBookFilter.isDebitTransactions()){
            PageableResponse response =new PageableResponse();
            response.setResponse(new ArrayList<>());
            return response;
        }
        if(debitPredicate!=null && creditPredicate!=null){
            predicates.add(cb.or(debitPredicate,creditPredicate));
        }else if(debitPredicate!=null){
            predicates.add(debitPredicate);
        }else if(creditPredicate!=null){
            predicates.add(creditPredicate);
        }
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<TransactionResponseDTO> query=entityManager.createQuery(cq);
        query.setFirstResult((passBookFilter.getPageNo()-1)*passBookFilter.getRecsPerPage());
        query.setMaxResults(passBookFilter.getRecsPerPage());
        transactionResponseDTOS=query.getResultList();

        PageableResponse pageableResponse=new PageableResponse();
        pageableResponse.setResponse(transactionResponseDTOS);
        if(passBookFilter.getPageNo()==1){
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            countQuery.select(cb.count(countQuery.from(TransactionTbl.class)));
            countQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            pageableResponse.setTotalRecords(entityManager.createQuery(countQuery).getSingleResult());
        }

        return pageableResponse;
    }

}
