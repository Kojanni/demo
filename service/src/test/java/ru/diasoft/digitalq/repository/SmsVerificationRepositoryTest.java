package ru.diasoft.digitalq.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.digitalq.domain.SmsVerification;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    void findByProcessGuidAndSecretCodeAndStatus() {
        String guid = UUID.randomUUID().toString();
        String secretCode = "123";
        String status = "CREATED";

        SmsVerification mock = SmsVerification.builder()
                .processGuid(guid)
                .phoneNumber("8-495-777-77-77")
                .secretCode(secretCode)
                .status(status)
                .build();

        SmsVerification createdEntity = repository.save(mock);

        assertThat(repository.findByProcessGuidAndSecretCodeAndStatus(guid, secretCode, status).orElse(SmsVerification.builder().build()))
                .isEqualTo(createdEntity);
        assertThat(repository.findByProcessGuidAndSecretCodeAndStatus("222", secretCode, status)).isEmpty();
    }
}