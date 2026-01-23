package org.example.accountservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.accountservice.model.AccountDetails;
import org.example.accountservice.repository.AccountRepository;
import org.example.common.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

   @Autowired
   private TopUpRequestProducer topUpRequestProducer;

   private final JmsTemplate jmsTemplate;

    private static final Logger log =
            LoggerFactory.getLogger(AccountService.class);


    public void createAccount(AccountRequest accountRequest, AccountResponse accountResponse) {

        String newCustomerId =
                accountRequest.getCustomerName().substring(0, 3).toUpperCase()
                        + new Random().nextInt(9000) + 1000;

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setCustomerId(newCustomerId);
        accountDetails.setCustomerName(accountRequest.getCustomerName());
        accountDetails.setCustomerEmail(accountRequest.getCustomerEmail());
        //jmsTemplate.convertAndSend("topup.request.queue", new TopUpRequest(accountDetails.getCustomerId(), 100000.0));
        accountDetails.setWalletBalance(100000.0);
        accountDetails.setAccountCreationDate(LocalDateTime.now());
        accountDetails.setAccountStatus("INACTIVE");
        accountDetails.setAccountType(AccountType.PREMIUM);

        accountRepository.save(accountDetails);

        accountResponse.setCustomerId(newCustomerId);

    }


    public AccountResponse updateAccount(AccountRequest accountRequest, AccountResponse accountResponse) {

        AccountDetails existing = accountRepository
                .findByCustomerEmail(accountRequest.getCustomerEmail())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        accountResponse.setCustomerId(existing.getCustomerId());
        accountResponse.setFinalPrice(accountRequest.getTotalPrice());

        if (existing.getWalletBalance() < accountRequest.getTotalPrice()) {
            accountResponse.setHasSufficientBalance(false);
            return accountResponse;
        }

        existing.setWalletBalance(existing.getWalletBalance() - accountRequest.getTotalPrice());
        existing.setAccountStatus("ACTIVE");

        accountRepository.save(existing);

        accountResponse.setHasSufficientBalance(true);

        return accountResponse;
    }

    public AccountResponse liteBenefit(AccountRequest accountRequest, AccountResponse accountResponse){
        double discountedTotalPrice = accountRequest.getTotalPrice() * 98.0 / 100.0;
        accountRequest.setTotalPrice(discountedTotalPrice);
        return updateAccount(accountRequest, accountResponse);
    }

    public AccountResponse premiumBenefit(AccountRequest accountRequest, AccountResponse accountResponse){
        double discountedTotalPrice = accountRequest.getTotalPrice() * 95.0 / 100.0;
        accountRequest.setTotalPrice(discountedTotalPrice);
        return updateAccount(accountRequest, accountResponse);
    }

    public String topUpWalletRequest(TopUpRequest topUpRequest){
        boolean isAccountAvailable = accountRepository.findById(topUpRequest.getCustomerId()).isPresent();

        if(!isAccountAvailable)
            return "Top up failed!! Account do not exists.";

        topUpRequestProducer.sendRequest(topUpRequest);

        return "request sent to process wallet top up.";
    }

}
