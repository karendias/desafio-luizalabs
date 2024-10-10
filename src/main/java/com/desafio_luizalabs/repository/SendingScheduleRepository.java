package com.desafio_luizalabs.repository;

import com.desafio_luizalabs.entity.SendingScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendingScheduleRepository extends JpaRepository<SendingScheduleEntity, Integer> {
}
