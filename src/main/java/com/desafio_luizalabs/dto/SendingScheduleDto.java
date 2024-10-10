package com.desafio_luizalabs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendingScheduleDto {
    private String status;

    private String recipient;

    private String recipientType;

    private String message;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateAndTimeOfSubmission;

    @Override
    public String toString() {
        return "SendingScheduleDto{" +
                "message='" + message + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientType='" + recipientType + '\'' +
                ", dateAndTimeOfSubmission=" + dateAndTimeOfSubmission +
                '}';
    }
}
