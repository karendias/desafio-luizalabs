package com.desafio_luizalabs.service;

import com.desafio_luizalabs.configuration.RabbitMQConfig;
import com.desafio_luizalabs.entity.SendingScheduleEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(SendingScheduleEntity sendingSchedule) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                sendingSchedule
        );
        System.out.println("Enviando para a fila de comunicação: " + sendingSchedule);
    }
}

