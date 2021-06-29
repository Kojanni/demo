package ru.diasoft.digitalq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.domain.*;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class SmsVerificationServiceImpl implements SmsVerificationService{
    private final SmsVerificationRepository smsVerificationRepository;

    private static final String STATUS_OK = "OK";
    private static final String STATUS_WAITING = "WAITING";

    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(SmsVerificationCheckRequest smsVerificationCheckRequest) {
        Optional<SmsVerification> result = smsVerificationRepository.findByProcessGuidAndSecretCodeAndStatus(smsVerificationCheckRequest.getProcessGUID(), smsVerificationCheckRequest.getCode(), STATUS_OK);

        SmsVerificationCheckResponse response = new SmsVerificationCheckResponse();
        response.setCheckResult(result.isPresent());

        return response;
    }

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(SmsVerificationPostRequest smsVerificationPostRequest) {
        SmsVerification entity = SmsVerification.builder()
                .phoneNumber(smsVerificationPostRequest.getPhoneNumber())
                .processGuid(UUID.randomUUID().toString())
                .secretCode(String.valueOf(new Random().nextInt(10000)))
                .status(STATUS_WAITING)
                .build();

        SmsVerification createdEntity = smsVerificationRepository.save(entity);

        SmsVerificationPostResponse response = new SmsVerificationPostResponse();
        response.setProcessGUID(createdEntity.getProcessGuid());

        return response;
    }
}
