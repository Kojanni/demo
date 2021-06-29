package ru.diasoft.digitalq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.model.SmsDeliveredMessage;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.smsverificationdelivered.subscribe.SmsVerificationDeliveredSubscribeListenerService;

@Service
@Primary
@RequiredArgsConstructor
public class SmsVerificationDeliveredSubscribeListenerServiceImpl implements SmsVerificationDeliveredSubscribeListenerService {
    private final SmsVerificationRepository repository;

    private static final String STATUS_OK = "OK";
    private static final String STATUS_WAITING = "WAITING";

    @Override
    public void smsVerificationDelivered(SmsDeliveredMessage msg) {
        repository.updateStatusByProcessGuid(STATUS_OK, msg.getGuid());
    }
}
