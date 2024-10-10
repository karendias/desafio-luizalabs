package com.desafio_luizalabs.service;
import com.desafio_luizalabs.configuration.RabbitMQConfig;
import com.desafio_luizalabs.entity.SendingScheduleEntity;
import com.desafio_luizalabs.entity.Status;
import com.desafio_luizalabs.repository.SendingScheduleRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    @Autowired
    private SendingScheduleRepository sendingScheduleRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(SendingScheduleEntity sendingSchedule) {
        System.out.println("Processando a comunicação: " + sendingSchedule);

        sendingSchedule.setStatus(String.valueOf(Status.SENT));
        sendingScheduleRepository.save(sendingSchedule);
    }
}


