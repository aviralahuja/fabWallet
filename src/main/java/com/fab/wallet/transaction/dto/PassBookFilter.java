package com.fab.wallet.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PassBookFilter {
    int pageNo;
    int recsPerPage;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate fromDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate toDate;
    @NotNull
    boolean debitTransactions;
    @NotNull
    boolean walletToWalletTransactions;
    @NotNull
    boolean addMoneyTransactions;

    public boolean isDebitTransactions() {
        return debitTransactions;
    }

    public void setDebitTransactions(boolean debitTransactions) {
        this.debitTransactions = debitTransactions;
    }

    public boolean isWalletToWalletTransactions() {
        return walletToWalletTransactions;
    }

    public void setWalletToWalletTransactions(boolean walletToWalletTransactions) {
        this.walletToWalletTransactions = walletToWalletTransactions;
    }

    public boolean isAddMoneyTransactions() {
        return addMoneyTransactions;
    }

    public void setAddMoneyTransactions(boolean addMoneyTransactions) {
        this.addMoneyTransactions = addMoneyTransactions;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getRecsPerPage() {
        return recsPerPage;
    }

    public void setRecsPerPage(int recsPerPage) {
        this.recsPerPage = recsPerPage;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
