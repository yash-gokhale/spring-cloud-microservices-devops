package org.example.accountservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.accountservice.model.AccountDetails;
import org.example.accountservice.repository.AccountRepository;
import org.example.common.dto.AccountRequest;
import org.example.common.dto.AccountResponse;
import org.example.common.dto.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountServiceIT {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void shouldCreateNewAccountSuccessfully(){
        //given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCustomerName("John Doe");
        accountRequest.setCustomerEmail("JohnDoe@email.com");
        accountRequest.setTotalPrice(1000);

        AccountResponse accountResponse = new AccountResponse();

        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setCustomerName("John Doe");
        accountDetails.setAccountType(AccountType.PREMIUM);
        accountDetails.setAccountStatus("INACTIVE");
        accountDetails.setAccountCreationDate(LocalDateTime.now());
        accountDetails.setCustomerId("User89676");
        accountDetails.setWalletBalance(100000);

        //when
        when(accountRepository.findByCustomerEmail(accountRequest.getCustomerEmail())).thenReturn(Optional.of(accountDetails));

        when(accountRepository.save(any(AccountDetails.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        AccountResponse response = accountService.updateAccount(accountRequest, accountResponse);

        //then
        assertNotNull(response);
        assertNotNull(response.getCustomerId());
        assertTrue(response.isHasSufficientBalance());
        assertNull(response.getAccountStatus());
        assertEquals(1000, response.getFinalPrice());

        AccountDetails updatedDetails = accountRepository.findByCustomerEmail("JohnDoe@email.com").orElseThrow(() -> new EntityNotFoundException("Account not found"));
        assertEquals("ACTIVE",updatedDetails.getAccountStatus());
        assertEquals(99000, updatedDetails.getWalletBalance());
        assertNotNull(updatedDetails.getCustomerId());
        assertNotNull(updatedDetails.getCustomerName());
        assertEquals(AccountType.PREMIUM, updatedDetails.getAccountType());
        assertNotNull(updatedDetails.getAccountCreationDate());
        verify(accountRepository).save(accountDetails);

    }
}
