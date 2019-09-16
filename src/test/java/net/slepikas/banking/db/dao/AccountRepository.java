package net.slepikas.banking.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByCustId(Long custId);

}
