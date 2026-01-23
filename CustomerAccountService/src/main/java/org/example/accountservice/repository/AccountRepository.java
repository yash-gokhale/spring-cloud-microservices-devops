package org.example.accountservice.repository;


import org.example.accountservice.model.AccountDetails;
import org.reactivestreams.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails, String> {
    Optional<AccountDetails> findByCustomerEmail(String customerEmail);
}
