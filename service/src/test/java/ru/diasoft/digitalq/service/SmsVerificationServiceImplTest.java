package ru.diasoft.digitalq.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.domain.*;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SmsVerificationServiceImplTest {

    @Mock
    SmsVerificationRepository repository;

    SmsVerificationService smsVerificationService;

    private final String PHONE_NUMBER = "8-921-782-33-57";
    private final String VALID_CODE = "1111";
    private final String NOT_VALID_CODE = "0000";
    private final String GUID = UUID.randomUUID().toString();
    private final String STATUS_OK = "OK";


    @Before
    public void init1() {
        smsVerificationService = new SmsVerificationServiceImpl(repository);
        SmsVerification mock = SmsVerification.builder()
                .processGuid(GUID)
                .phoneNumber(PHONE_NUMBER)
                .secretCode(VALID_CODE)
                .status(STATUS_OK)
                .build();
        Mockito.when(repository.findByProcessGuidAndSecretCodeAndStatus(GUID, VALID_CODE, STATUS_OK)).thenReturn(Optional.of(mock));
        Mockito.when(repository.findByProcessGuidAndSecretCodeAndStatus(GUID, NOT_VALID_CODE, STATUS_OK)).thenReturn(Optional.empty());
        Mockito.when(repository.save(any(SmsVerification.class))).thenReturn(mock);
       }

    @Test
    public void dsSmsVerificationCheck() {
        SmsVerificationCheckRequest smsVerificationCheckRequest = new SmsVerificationCheckRequest();
        smsVerificationCheckRequest.setProcessGUID(GUID);
        smsVerificationCheckRequest.setCode(VALID_CODE);

        SmsVerificationCheckResponse response = smsVerificationService.dsSmsVerificationCheck(smsVerificationCheckRequest);

        assertThat(response).isNotNull();
        assertThat(response.getCheckResult()).isTrue();
    }

    @Test
    public void dsSmsVerificationCheck2() {
        SmsVerificationCheckRequest smsVerificationCheckRequest = new SmsVerificationCheckRequest();
        smsVerificationCheckRequest.setProcessGUID(GUID);
        smsVerificationCheckRequest.setCode(NOT_VALID_CODE);
        SmsVerificationCheckResponse response = smsVerificationService.dsSmsVerificationCheck(smsVerificationCheckRequest);

        assertThat(response).isNotNull();
        assertThat(response.getCheckResult()).isFalse();
    }

    @Test
    public void dsSmsVerificationCreate() {
        SmsVerificationPostRequest smsVerificationPostRequest = new SmsVerificationPostRequest();
        smsVerificationPostRequest.setPhoneNumber(PHONE_NUMBER);

        SmsVerificationPostResponse response = smsVerificationService.dsSmsVerificationCreate(smsVerificationPostRequest);

        assertThat(response).isNotNull();
        assertThat(response.getProcessGUID()).isNotEmpty();
    }
}