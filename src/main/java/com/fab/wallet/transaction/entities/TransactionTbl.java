package com.fab.wallet.transaction.entities;

import com.fab.wallet.user.entities.UserTbl;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_TBL")
public class TransactionTbl {
    @Id
    @Column(name = "TXN_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    int txnId;
    @Column(name = "TXN_TYPE",length = 1)
    short txnType;
    @Column(name = "FROM_USER",length = 60)
    String fromUser;
    @Column(name = "PAYMENT_MODE",length = 1)
    short paymentMode;
    @Column(name = "TO_USER",length = 60)
    String toUser;
    @Column(name = "TXN_DATE_TIME")
    LocalDateTime txnDateTime;
    @Column(name = "TRANSACTION_AMOUNT")
    int txnAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_USER",nullable = true,insertable = false,updatable = false)
    UserTbl fromUserTbl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_USER",insertable = false,updatable = false)
    UserTbl toUserTbl;

    public int getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(int txnAmount) {
        this.txnAmount = txnAmount;
    }

    public short getTxnType() {
        return txnType;
    }

    public void setTxnType(short txnType) {
        this.txnType = txnType;
    }

    public short getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(short paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDateTime getTxnDateTime() {
        return txnDateTime;
    }

    public void setTxnDateTime(LocalDateTime txnDateTime) {
        this.txnDateTime = txnDateTime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public UserTbl getFromUserTbl() {
        return fromUserTbl;
    }

    public void setFromUserTbl(UserTbl fromUserTbl) {
        this.fromUserTbl = fromUserTbl;
    }

    public UserTbl getToUserTbl() {
        return toUserTbl;
    }

    public void setToUserTbl(UserTbl toUserTbl) {
        this.toUserTbl = toUserTbl;
    }
}
