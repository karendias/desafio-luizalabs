package com.desafio_luizalabs.web.api;

import com.desafio_luizalabs.dto.SendingScheduleDto;
import com.desafio_luizalabs.dto.SendingScheduleStatusDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("sending-schedule")
public interface SendingScheduleApi {

    @PostMapping("/sending")
    ResponseEntity<Void> sendingSchedule(@RequestBody SendingScheduleDto sendingScheduleDto);

    @GetMapping("/{id}")
    ResponseEntity<SendingScheduleStatusDto> getSendingSchedule(@PathVariable("id") Integer id) throws BadRequestException;

    @PatchMapping("/{id}")
    ResponseEntity<Void> cancelSendingSchedule(@PathVariable("id") Integer id) throws BadRequestException;
}
