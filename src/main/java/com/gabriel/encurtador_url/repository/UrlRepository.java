package com.gabriel.encurtador_url.repository;

import com.gabriel.encurtador_url.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, String> {
    // Só de estender JpaRepository, você já ganha:
    // .save(), .findById(), .findAll(), .delete()
    // Sem escrever NENHUMA linha de SQL!
}