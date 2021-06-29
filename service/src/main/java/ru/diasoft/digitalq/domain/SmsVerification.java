package ru.diasoft.digitalq.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sms_verification")
public class SmsVerification {

    @Column(name = "verificationid")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_verification_verificationid_seq")
    @SequenceGenerator(name = "sms_verification_verificationid_seq", sequenceName = "sms_verification_verificationid_seq", allocationSize = 1)
    private Long verificationId;

    @Column(name = "processguid")
    private String processGuid;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "secretcode")
    private String secretCode;

    @Column(name = "status")
    private String status;
}
