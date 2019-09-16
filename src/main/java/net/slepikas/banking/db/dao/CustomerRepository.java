package net.slepikas.banking.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByEmailAndPass(String email, String pass);
}
