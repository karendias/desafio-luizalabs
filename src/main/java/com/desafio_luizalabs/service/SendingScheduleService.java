package com.desafio_luizalabs.service;

import com.desafio_luizalabs.dto.SendingScheduleDto;
import com.desafio_luizalabs.dto.SendingScheduleStatusDto;
import org.apache.coyote.BadRequestException;

public interface SendingScheduleService {
    void sendingSchedule(SendingScheduleDto sendingScheduleDto);

    SendingScheduleStatusDto getSendingScheduleStatus(Integer id) throws BadRequestException;

    void cancelSendingSchedule(Integer id) throws BadRequestException;
}
