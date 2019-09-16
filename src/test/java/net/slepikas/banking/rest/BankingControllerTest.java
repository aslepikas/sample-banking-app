package net.slepikas.banking.rest;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.slepikas.banking.db.config.RepoFactoryForTest;
import net.slepikas.banking.db.dao.AccountEventRepository;
import net.slepikas.banking.db.dao.AccountRepository;
import net.slepikas.banking.db.dao.Customer;
import net.slepikas.banking.db.dao.CustomerRepository;
import net.slepikas.banking.rest.request.Login;
import org.hsqldb.HsqlException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = "net.slepikas.banking.db.dao")
//@DatabaseSetup("classpath:schema.sql")
//@ContextConfiguration(classes = {AccountEventRepository.class, AccountRepository.class, CustomerRepository.class,
//        RepoFactoryForTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
@PropertySource("application-test.yml")
//@SpringBootTest(classes={BankingController.class})
//@ContextConfiguration(classes = {DbConfig.class, AccountRepository.class, AccountEventRepository.class, CustomerRepository.class})
@EnableAutoConfiguration
//@WebAppConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BankingControllerTest {

    @Autowired
    public AccountEventRepository eventRepository;
    @Autowired
    public AccountRepository accountRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    DataSource dataSource;
    @Autowired
    TestEntityManager testEntityManager;
    //    @Autowired
    BankingController bankingController;
    Customer originalCustomer;
    Login originalLogin;
    Customer sameAsOriginal;
    Login sameAsLogin;
    Customer differentCust;
    Login differentLogin;

    @Before
    public void before() {
        bankingController = new BankingController();
        bankingController.setCustomerRepository(customerRepository);
        bankingController.setAccountEventRepository(eventRepository);
        bankingController.setCustomerRepository(customerRepository);

        originalCustomer = new Customer();
        originalCustomer.setId(123L);
        originalCustomer.setEmail("test@mail.com");
        originalCustomer.setName("Name Surname");
        originalCustomer.setPass("somePAss");
        originalLogin = new Login();
        originalLogin.setEmail(originalCustomer.getEmail());
        originalLogin.setPass(originalCustomer.getPass());

        sameAsOriginal = new Customer();
        sameAsOriginal.setEmail("test@mail.com");
        sameAsOriginal.setName("asdad");
        sameAsOriginal.setPass("asdadsad");

        differentCust = new Customer();
        differentCust.setEmail("test2@mail.com");
        differentCust.setName("asdad asd");
        differentCust.setPass("asdadsad pass");
    }

    @Test
    public void registerWithNewAccountTest() throws Exception {
        bankingController.register(originalCustomer);
        assertEquals(1, bankingController.getAccounts(originalLogin).size());
    }

    @Test(expected = HsqlException.class)
    public void registerDuplicateTest() {
        bankingController.register(originalCustomer);
        bankingController.register(sameAsOriginal);
    }

    @Test
    public void getAccounts() throws Exception {
        bankingController.register(originalCustomer);
        bankingController.getAccounts(originalLogin);
    }

    @Test
    public void deposit() {
    }

    @Test
    public void withdraw() {
    }

    @Test
    public void getAccountHistory() {
    }
}