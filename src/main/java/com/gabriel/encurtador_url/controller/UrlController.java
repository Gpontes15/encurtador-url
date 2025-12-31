package com.gabriel.encurtador_url.controller;

import com.gabriel.encurtador_url.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping
public class UrlController {

    @Autowired
    private UrlService urlService;

    // Endpoint 1: Encurtar URL
    // Exemplo de uso no Postman: POST http://localhost:8080/shorten
    // Body (JSON): { "url": "https://google.com" }
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortCode = urlService.shortenUrl(originalUrl);
        
        // Retorna a URL completa encurtada (para facilitar o teste)
        return ResponseEntity.ok("http://localhost:8080/" + shortCode);
    }

    // Endpoint 2: Redirecionar
    // Exemplo de uso no Navegador: http://localhost:8080/a7X9b2
    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        
        // Isso faz o navegador abrir o site original automaticamente
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL não encontrada");
        }
    }
}