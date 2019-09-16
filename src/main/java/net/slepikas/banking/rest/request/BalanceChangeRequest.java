package net.slepikas.banking.rest.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceChangeRequest {

    private Login login;

    private BigDecimal amount;

    private Long accountId;

}
