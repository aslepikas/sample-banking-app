package net.slepikas.banking.db.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(AccountEventId.class)
@Table(name = "account_events", schema = "sample")
public class AccountEvent {

    @Id
    private Long accountId;

    @Id
    private Timestamp eventTs;

    private BigDecimal balanceBefore;

    private BigDecimal balanceChange;
}
