package net.slepikas.banking.rest;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.slepikas.banking.db.dao.*;
import net.slepikas.banking.rest.request.BalanceChangeRequest;
import net.slepikas.banking.rest.request.HistoryRequest;
import net.slepikas.banking.rest.request.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
public class BankingController {

    @Autowired
    @Setter
    private CustomerRepository customerRepository;

    @Autowired
    @Setter
    private AccountRepository accountRepository;

    @Autowired
    @Setter
    private AccountEventRepository accountEventRepository;

    @PostMapping("/register")
    public void register(@Valid @RequestBody Customer customer) {
        Customer cust = customerRepository.save(customer);
        createNewAccount(cust);
    }

    @PostMapping("/getAccounts")
    public List<Account> getAccounts(@RequestBody Login login) throws Exception {
        Customer customer = login(login);
        return accountRepository.findAllByCustId(customer.getId());
    }

    private Customer login(@RequestBody Login login) throws Exception {
        Customer customer = customerRepository.findOneByEmailAndPass(login.getEmail(), login.getPass());
        if (customer == null) {
            throw new Exception("Customer not found with provided login info");
        }
        return customer;
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestBody BalanceChangeRequest balanceChangeRequest) throws Exception {
        Customer customer = login(balanceChangeRequest.getLogin());
        Account account = accountRepository.getOne(balanceChangeRequest.getAccountId());
        if (balanceChangeRequest.getAmount().compareTo(new BigDecimal(0)) <= 0) {
            throw new ValidationException("Amount must be positive");
        }
        if (customer.getId() != account.getCustId()) {
            throw new Exception("Account not found");
        }
        log.info("here");
        return saveBalanceChange(balanceChangeRequest.getAmount(), account);
    }

    @PostMapping("/withdraw")
    public Account withdraw(@RequestBody BalanceChangeRequest balanceChangeRequest) throws Exception {
        Customer customer = login(balanceChangeRequest.getLogin());
        Account account = accountRepository.getOne(balanceChangeRequest.getAccountId());
        if (balanceChangeRequest.getAmount().compareTo(new BigDecimal(0)) <= 0) {
            throw new ValidationException("Amount must be positive");
        }
        if (customer.getId() != account.getCustId()) {
            throw new Exception("Account not found");
        }

        if (account.getBalance().compareTo(balanceChangeRequest.getAmount()) < 0) {
            throw new ValidationException("Cannot withdraw more funds than there are in account");
        }

        return saveBalanceChange(balanceChangeRequest.getAmount().negate(), account);
    }

    @PostMapping("/getHistory")
    public List<AccountEvent> getAccountHistory(@RequestBody HistoryRequest historyRequest) throws Exception {
        Customer customer = login(historyRequest.getLogin());
        Account account = accountRepository.getOne(historyRequest.getAccountId());
        if (customer.getId() != account.getCustId()) {
            throw new Exception("Account not found");
        }
        return accountEventRepository.findAllByEventTsBetween(Date.valueOf(historyRequest.getStartDate()),
                Date.valueOf(historyRequest.getEndDate()));
    }

    private Account saveBalanceChange(BigDecimal amount, Account account) {
        account.setBalance(account.getBalance().add(amount));
        AccountEvent event =
                new AccountEvent(account.getAccId(), new Timestamp(System.currentTimeMillis()), account.getBalance(),
                        amount);
        Account acc = accountRepository.save(account);
        accountEventRepository.save(event);
        return acc;
    }

    private void createNewAccount(Customer cust) {
        Account account = new Account();
        account.setBalance(new BigDecimal(0));
        account.setCustId(cust.getId());
        accountRepository.save(account);
    }

}
