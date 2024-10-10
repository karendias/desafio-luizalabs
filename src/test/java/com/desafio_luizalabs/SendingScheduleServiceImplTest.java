package com.desafio_luizalabs;

import com.desafio_luizalabs.dto.SendingScheduleDto;
import com.desafio_luizalabs.dto.SendingScheduleStatusDto;
import com.desafio_luizalabs.entity.SendingScheduleEntity;
import com.desafio_luizalabs.entity.Status;
import com.desafio_luizalabs.repository.SendingScheduleRepository;
import com.desafio_luizalabs.service.RabbitMQProducer;
import com.desafio_luizalabs.service.impl.SendingScheduleServiceImpl;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SendingScheduleServiceImplTest {

    @InjectMocks
    private SendingScheduleServiceImpl sendingScheduleService;

    @Mock
    private SendingScheduleRepository sendingScheduleRepository;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendingSchedule_ValidRecipient() throws BadRequestException {
        SendingScheduleDto dto = new SendingScheduleDto();
        dto.setMessage("Test Message");
        dto.setRecipient("test@example.com");
        dto.setRecipientType("email");
        dto.setDateAndTimeOfSubmission(LocalDateTime.now());

        sendingScheduleService.sendingSchedule(dto);

        verify(sendingScheduleRepository).save(any(SendingScheduleEntity.class));
        verify(rabbitMQProducer).sendMessage(any(SendingScheduleEntity.class));
    }

    @Test
    void testSendingSchedule_InvalidRecipientType() {
        SendingScheduleDto dto = new SendingScheduleDto();
        dto.setMessage("Test Message");
        dto.setRecipient("test@example.com");
        dto.setRecipientType("invalidType"); // Tipo inválido

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            sendingScheduleService.sendingSchedule(dto);
        });

        assertEquals("O tipo de envio não é válido", thrown.getMessage());
    }

    @Test
    void testGetSendingScheduleStatus_ValidId() throws BadRequestException {
        SendingScheduleEntity entity = new SendingScheduleEntity();
        entity.setStatus(String.valueOf(Status.PENDING));

        when(sendingScheduleRepository.findById(1)).thenReturn(Optional.of(entity));

        SendingScheduleStatusDto statusDto = sendingScheduleService.getSendingScheduleStatus(1);

        assertEquals(String.valueOf(Status.PENDING), statusDto.getStatus());
    }

    @Test
    void testGetSendingScheduleStatus_InvalidId() {
        when(sendingScheduleRepository.findById(1)).thenReturn(Optional.empty());

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            sendingScheduleService.getSendingScheduleStatus(1);
        });

        assertEquals("Registro não encontrado", thrown.getMessage());
    }

    @Test
    void testCancelSendingSchedule_ValidId() throws BadRequestException {
        SendingScheduleEntity entity = new SendingScheduleEntity();
        entity.setStatus(String.valueOf(Status.PENDING));

        when(sendingScheduleRepository.findById(1)).thenReturn(Optional.of(entity));

        sendingScheduleService.cancelSendingSchedule(1);

        verify(sendingScheduleRepository).save(entity);
        assertEquals(String.valueOf(Status.CANCELLED), entity.getStatus());
    }

    @Test
    void testCancelSendingSchedule_InvalidId() {
        when(sendingScheduleRepository.findById(1)).thenReturn(Optional.empty());

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            sendingScheduleService.cancelSendingSchedule(1);
        });

        assertEquals("Registro não encontrado", thrown.getMessage());
    }

    @Test
    void testCancelSendingSchedule_StatusNotPending() {
        SendingScheduleEntity entity = new SendingScheduleEntity();
        entity.setStatus(String.valueOf(Status.CANCELLED));

        when(sendingScheduleRepository.findById(1)).thenReturn(Optional.of(entity));

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            sendingScheduleService.cancelSendingSchedule(1);
        });

        assertEquals("Só é possível alterar um registro com status PENDENTE", thrown.getMessage());
    }
}

