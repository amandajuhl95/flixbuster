package dk.chokobanan.flixbuster.controllers;

import dk.chokobanan.flixbuster.model.Account;
import dk.chokobanan.flixbuster.model.User;
import dk.chokobanan.flixbuster.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flixbuster/accounts")
public class AccountController {

    private static AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @PostMapping("/create")
    public Account createAccount(@RequestBody Account account) {
        User user = new User(account.getFirstname(), false);
        account.addUser(user);
        account.getPayment().setAccount(account);
        return accountRepository.save(account);
    }







}
