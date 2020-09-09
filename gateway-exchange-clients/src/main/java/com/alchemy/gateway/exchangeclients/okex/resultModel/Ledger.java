package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class Ledger {

    private String ledger_id;

    private String currency;

    private String balance;

    private String amount;

    private String fee;

    private String typeName;

    private String timestamp;
}