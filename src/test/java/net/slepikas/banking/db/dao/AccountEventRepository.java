package net.slepikas.banking.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Transactional
public interface AccountEventRepository extends JpaRepository<AccountEvent, Long> {

    List<AccountEvent> findAllByEventTsBetween(Date startDate, Date endDate);

}
