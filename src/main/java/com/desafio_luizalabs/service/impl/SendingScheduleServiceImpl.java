package com.desafio_luizalabs.service.impl;

import com.desafio_luizalabs.dto.SendingScheduleDto;
import com.desafio_luizalabs.dto.SendingScheduleStatusDto;
import com.desafio_luizalabs.entity.SendingScheduleEntity;
import com.desafio_luizalabs.entity.Status;
import com.desafio_luizalabs.repository.SendingScheduleRepository;
import com.desafio_luizalabs.service.RabbitMQProducer;
import com.desafio_luizalabs.service.SendingScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SendingScheduleServiceImpl implements SendingScheduleService {

    @Autowired
    private SendingScheduleRepository sendingScheduleRepository;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    private final String ERROR_REGISTER_NOT_FOUND = "Registro não encontrado";

    @Override
    public void sendingSchedule(SendingScheduleDto sendingScheduleDto) throws BadRequestException {
        ArrayList<String> possibleRecipientTypes = new ArrayList<>(Arrays.asList("email", "phone"));
        boolean isValidRecipientType = possibleRecipientTypes.contains(sendingScheduleDto.getRecipientType());


        if (isValidRecipientType) {
            SendingScheduleEntity sendingSchedule = SendingScheduleEntity.builder()
                    .message(sendingScheduleDto.getMessage())
                    .recipient(sendingScheduleDto.getRecipient())
                    .recipientType(sendingScheduleDto.getRecipientType())
                    .status(String.valueOf(Status.PENDING))
                    .dateTimeSubmission(sendingScheduleDto.getDateAndTimeOfSubmission())
                    .build();

            sendingScheduleRepository.save(sendingSchedule);

            rabbitMQProducer.sendMessage(sendingSchedule);

        } else {
            String NOT_POSSIBLE_SAVE_REGISTER = "O tipo de envio não é válido";
            throw new BadRequestException(NOT_POSSIBLE_SAVE_REGISTER);
        }
    }

    @Override
    public SendingScheduleStatusDto getSendingScheduleStatus(Integer id) throws BadRequestException {
        SendingScheduleEntity sendingSchedule = sendingScheduleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ERROR_REGISTER_NOT_FOUND));

        SendingScheduleStatusDto sendingScheduleDto = new SendingScheduleStatusDto();
        sendingScheduleDto.setStatus(sendingSchedule.getStatus());

        return sendingScheduleDto;
    }

    @Override
    public void cancelSendingSchedule(Integer id) throws BadRequestException {
        SendingScheduleEntity sendingSchedule = sendingScheduleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ERROR_REGISTER_NOT_FOUND));

        if (sendingSchedule.getStatus().equals(String.valueOf(Status.PENDING))) {
            sendingSchedule.setStatus(String.valueOf(Status.CANCELLED));
            sendingScheduleRepository.save(sendingSchedule);
        } else {
            String NOT_POSSIBLE_ALTER_REGISTER = "Só é possível alterar um registro com status PENDENTE";
            throw new BadRequestException(NOT_POSSIBLE_ALTER_REGISTER);
        }
    }
}
