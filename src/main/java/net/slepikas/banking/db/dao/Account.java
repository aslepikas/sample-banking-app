package net.slepikas.banking.db.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "accounts", schema = "sample")
public class Account {

    @Id
    @GeneratedValue
    private Long accId;

    private long custId;

    private BigDecimal balance;

}
