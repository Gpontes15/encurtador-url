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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {

    @Id
    private String id;
    
    private String originalUrl;
    
    private LocalDateTime createdAt = LocalDateTime.now();

    public UrlEntity(String id, String originalUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.createdAt = LocalDateTime.now();
    }
}