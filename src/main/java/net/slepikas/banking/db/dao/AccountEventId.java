package net.slepikas.banking.db.dao;

import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

public class AccountEventId implements Serializable {

    @Id
    private Long accountId;

    @Id
    private Timestamp eventTs;


}
