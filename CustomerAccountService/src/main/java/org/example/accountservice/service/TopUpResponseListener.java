package org.example.accountservice.service;

import org.example.accountservice.model.AccountDetails;
import org.example.accountservice.repository.AccountRepository;
import org.example.common.dto.TopUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class TopUpResponseListener {

    Logger log = Logger.getLogger(TopUpResponseListener.class.getName());

    @Autowired
    private AccountRepository accountRepository;

    @JmsListener(destination = "topup.response.queue")
    public void receiveTopUpResponse(TopUpResponse topUpResponse) {
        log.info("Received Top Up Response: " + topUpResponse);
        AccountDetails accountDetails = accountRepository.findById(topUpResponse.getCustomerId()).get();
        if(topUpResponse.getTopUpStatus().equals("TOP-UP_SUCCESS")){
            accountDetails.setWalletBalance(accountDetails.getWalletBalance() + topUpResponse.getFinalPrice());
            log.info("Top-up successful for customer ID: " + topUpResponse.getCustomerId() +
                    ". New wallet balance: " + accountDetails.getWalletBalance());
        }
        accountRepository.save(accountDetails);
    }
}
