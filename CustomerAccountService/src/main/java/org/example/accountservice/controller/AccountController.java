package org.example.accountservice.controller;

import org.example.accountservice.model.AccountDetails;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.service.AccountService;
import org.example.common.dto.AccountRequest;
import org.example.common.dto.AccountResponse;
import org.example.common.dto.AccountType;
import org.example.common.dto.TopUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        accountService.createAccount(accountRequest, new AccountResponse());
    }

    @PostMapping("/update")
    public AccountResponse updateAccount(@RequestBody AccountRequest accountRequest) {
        if(checkAccountType(accountRequest.getCustomerEmail()).equals(AccountType.LITE))
        return accountService.liteBenefit(accountRequest, new AccountResponse());
        else
            return accountService.premiumBenefit(accountRequest, new AccountResponse());
    }

    @GetMapping("/exists/{email}")
    public boolean checkAccountIfExists(@PathVariable("email") String email){
        return accountRepository.findByCustomerEmail(email).isPresent();
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable ("id") String customerId){
        accountRepository.deleteById(customerId);
        return "Account deleted with id " + customerId;
    }

    @DeleteMapping
    public String deleteAll(){
        accountRepository.deleteAll();
        return "All records are deleted from account table";
    }

    @PutMapping("/topUp")
    public  String topUpRequest(@RequestBody TopUpRequest topUpRequest){
        return accountService.topUpWalletRequest(topUpRequest);
    }

    private AccountType checkAccountType(String email){
        return accountRepository.findByCustomerEmail(email).get().getAccountType();
    }
}
