package com.desafio_luizalabs.web.api.impl;

import com.desafio_luizalabs.dto.SendingScheduleDto;
import com.desafio_luizalabs.dto.SendingScheduleStatusDto;
import com.desafio_luizalabs.service.SendingScheduleService;
import com.desafio_luizalabs.web.api.SendingScheduleApi;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendingScheduleApiImpl extends AbstractApi implements SendingScheduleApi {

    @Autowired
    private SendingScheduleService sendingScheduleService;

    @Override
    public ResponseEntity<Void> sendingSchedule(SendingScheduleDto sendingScheduleDto) throws BadRequestException {
       sendingScheduleService.sendingSchedule(sendingScheduleDto);

       return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SendingScheduleStatusDto> getSendingSchedule(Integer id) throws BadRequestException {
        SendingScheduleStatusDto sendingScheduleStatusDto = sendingScheduleService.getSendingScheduleStatus(id);
        return sendingScheduleStatusDto != null ? ResponseEntity.ok(sendingScheduleStatusDto) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> cancelSendingSchedule(Integer id) throws BadRequestException {
        sendingScheduleService.cancelSendingSchedule(id);

        return ResponseEntity.noContent().build();
    }
}
