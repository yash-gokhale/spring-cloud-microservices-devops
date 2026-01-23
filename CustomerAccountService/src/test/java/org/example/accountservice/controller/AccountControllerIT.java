package org.example.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.accountservice.CustomerAccountApplication;
import org.example.accountservice.model.AccountDetails;
import org.example.accountservice.repository.AccountRepository;
import org.example.accountservice.service.AccountService;
import org.example.common.dto.AccountType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = CustomerAccountApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateAccount() throws Exception {
        String requestJson = """
        {
          "customerName": "John Doe",
          "customerEmail": "john@example.com",
          "totalPrice": 70000
        }
        """;

        mockMvc.perform(post("/accounts/create").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk());
        Optional<AccountDetails> details =  accountRepository.findByCustomerEmail("john@example.com");

        assertTrue (
            (details.isPresent())
        );

        assertEquals("John Doe", details.get().getCustomerName());
        assertEquals("john@example.com", details.get().getCustomerEmail());
        assertEquals(AccountType.PREMIUM, details.get().getAccountType());
        }

    @AfterEach
    void cleanup() {
        accountRepository.deleteAll();
    }
    }



