package com.km086.admin.model.account;

public enum BillStatus {

    UNPAID("失败"), PAID("成功");

    private final String description;

    private BillStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}

