package ru.diasoft.digitalq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.diasoft.digitalq.domain.SmsVerification;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {

    Optional<SmsVerification> findByProcessGuidAndSecretCodeAndStatus(String processGUID, String secretCode, String status);

    @Transactional
    @Modifying
    @Query("update SmsVerification v set status = ?1 where processGuid = ?2")
    int updateStatusByProcessGuid(String status, String processGuid);
}
