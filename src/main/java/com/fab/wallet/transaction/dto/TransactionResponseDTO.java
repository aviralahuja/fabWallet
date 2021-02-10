package com.fab.wallet.transaction.dto;

import com.fab.wallet.user.entities.UserTbl;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class TransactionResponseDTO {
    short txnType;
    short paymentMode;
    LocalDateTime txnDateTime;
    int txnAmount;
    String fromUser;
    String toUser;

    public TransactionResponseDTO(short txnType, short paymentMode, LocalDateTime txnDateTime, String fromUser, String toUser, int txnAmount) {
        this.txnType = txnType;
        this.paymentMode = paymentMode;
        this.txnDateTime = txnDateTime;
        this.txnAmount = txnAmount;
        this.fromUser = fromUser;
        this.toUser = toUser;
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

    public int getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(int txnAmount) {
        this.txnAmount = txnAmount;
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
}
