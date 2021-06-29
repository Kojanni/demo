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

    private final String PHONE_NUMBER = "89205555555";
    private final String SECRET_CODE = "7689";
    private final String STATUS_OK = "OK";
    private final String STATUS_WAITING = "WAITING";

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

    @Test
    void update() {
        String processGuid = UUID.randomUUID().toString();
        SmsVerification smsVerification = SmsVerification.builder()
                .processGuid(processGuid)
                .phoneNumber(PHONE_NUMBER)
                .secretCode(SECRET_CODE)
                .status(STATUS_WAITING)
                .build();
        SmsVerification createdEntity = repository.save(smsVerification);
        repository.updateStatusByProcessGuid(STATUS_OK, processGuid);

        assertThat(repository.findById(createdEntity.getVerificationId()).orElse(smsVerification.builder().build()).getStatus()).isEqualTo(STATUS_OK);
    }
}