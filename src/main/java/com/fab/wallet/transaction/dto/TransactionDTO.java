package com.fab.wallet.transaction.dto;

import com.fab.wallet.util.Const;

import javax.validation.constraints.DecimalMin;

public class TransactionDTO {
    Const.PaymentMode paymentMode;
    String toUser;
    @DecimalMin("1")
    int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Const.PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(short paymentMode) {
        this.paymentMode = Const.PaymentMode.map.get(paymentMode);
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
