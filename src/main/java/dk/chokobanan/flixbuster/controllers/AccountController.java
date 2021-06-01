package dk.chokobanan.flixbuster.controllers;

import dk.chokobanan.flixbuster.DTO.AccountDTO;
import dk.chokobanan.flixbuster.DTO.UserDTO;
import dk.chokobanan.flixbuster.model.postgresql.Account;
import dk.chokobanan.flixbuster.model.postgresql.User;
import dk.chokobanan.flixbuster.model.redis.Login;
import dk.chokobanan.flixbuster.repository.postgresql.AccountRepository;
import dk.chokobanan.flixbuster.repository.postgresql.UserRepository;
import dk.chokobanan.flixbuster.repository.redis.SessionManagement;
import dk.chokobanan.flixbuster.repository.redis.SessionManagementImpl;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/flixbuster/accounts")
public class AccountController {

    private static AccountRepository accountRepository;
    private static UserRepository userRepository;
    private static SessionManagement management;

    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.management = new SessionManagementImpl();
    }

    @GetMapping
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @PostMapping("/create")
    public Account createAccount(@RequestBody Account account) {
        account.hash();
        User user = new User(account.getFirstname(), false);
        account.addUser(user);
        account.getPayment().setAccount(account);
        return accountRepository.save(account);
    }

    @PostMapping("/users/create/{userId}")
    public UserDTO createUser(@RequestBody UserDTO userDTO, @PathVariable UUID userId) {

        if(management.hasSession(userId, userDTO.getAccountId(), userDTO.getToken())) {

            Account account = accountRepository.findOneByUuid(userDTO.getAccountId());

            if(account.getUsers().size() < 4) {
                User user = new User(userDTO.getUsername(), userDTO.isChild());
                account.addUser(user);
                user = userRepository.save(user);
                return new UserDTO(user);
            }

        }

        return null;

    }


    @PostMapping("/login")
    public AccountDTO login(@RequestBody Login login){

        Account account = accountRepository.findOneByEmail(login.getEmail());

        if(account != null) {

            if(account.getPasswd().equals(login.hash()) && management.loginAllowed(account.getUuid())) {
                String token = management.addToken(account.getUuid());
                AccountDTO accountDTO = new AccountDTO(account, token);
                return accountDTO;
            }

            return null;
        }

        return null;
    }

    // Make exception handling for expired tokens
    @PostMapping("/login/{accountId}/{userId}")
    public UserDTO selectUser(@PathVariable UUID accountId, @RequestBody String token, @PathVariable UUID userId) {

        Account account = accountRepository.findOneByUuid(accountId);
        User user = account.getUser(userId);

        if(account != null && user != null)
        {
            management.setSession(userId, accountId, token);
            return new UserDTO(user);
        }

        return null;
    }







}
