package com.desafio_luizalabs.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sending_schedule")
public class SendingScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generate_id")
    @SequenceGenerator(name = "generate_id", sequenceName = "generate_id", allocationSize = 1)
    @Column(name = "id_sending_schedule", nullable = false)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "recipient", length = Integer.MAX_VALUE)
    private String recipient;

    @Column(name = "message", length = Integer.MAX_VALUE)
    private String message;

    @Column(name = "date_time_submission")
    private LocalDateTime dateTimeSubmission;

    @Column(name = "recipient_type")
    private String recipientType;

}

