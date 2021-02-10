package com.fab.wallet.transaction.dto;

public class PageableResponse {
    Object response;
    Long totalRecords;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
