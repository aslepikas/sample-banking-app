package net.slepikas.banking.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByEmailAndPass(String email, String pass);
}
