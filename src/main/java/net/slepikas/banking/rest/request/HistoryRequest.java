package net.slepikas.banking.rest.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistoryRequest {

    private Login login;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long accountId;

}
