package io.borys.webshop.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("")
    List<Account> findAll() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    Account findById(@PathVariable long id) {
        return accountRepository.findById(id).orElseThrow();
    }
}