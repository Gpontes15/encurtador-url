package com.gabriel.encurtador_url.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Data // O Lombok cria os Getters, Setters e toString() sozinho
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {

    @Id
    private String id; // Este será o nosso "Código Curto" (ex: a7X9b2)
    
    private String originalUrl; // A URL gigante original
    
    private LocalDateTime createdAt = LocalDateTime.now(); // Para saber quando foi criado

    // Construtor personalizado para facilitar
    public UrlEntity(String id, String originalUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.createdAt = LocalDateTime.now();
    }
}