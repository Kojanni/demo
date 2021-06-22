package ru.diasoft.digitalq.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.domain.SmsVerification;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsVerificationRepositoryTest {
    @Autowired
    private SmsVerificationRepository repository;

    @Test
    public void smsVerificationCreatedTest() {
        SmsVerification smsVerification = SmsVerification.builder()
                .processGuid(UUID.randomUUID().toString())
                .phoneNumber("1234567")
                .secretCode("1234")
                .status("TEST")
                .build();

        SmsVerification smsVerificationCreated = repository.save(smsVerification);

        assertThat(smsVerification).isEqualToComparingOnlyGivenFields(smsVerificationCreated, "verificationId");
        assertThat(smsVerificationCreated.getVerificationId()).isNotNull();
    }
}