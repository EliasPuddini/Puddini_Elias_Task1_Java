package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static javax.persistence.SharedCacheMode.NONE;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.function.Predicate.not;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.data.repository.util.ClassUtils.hasProperty;

@DataJpaTest
@AutoConfigureTestDatabase()
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;

}
