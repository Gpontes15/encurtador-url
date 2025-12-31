package com.gabriel.encurtador_url.service;

import com.gabriel.encurtador_url.model.UrlEntity;
import com.gabriel.encurtador_url.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository; // Conexão com o Banco (Postgres)

    @Autowired
    private StringRedisTemplate redisTemplate; // Conexão com o Cache (Redis)

    // --- LÓGICA 1: ENCURTAR URL ---
    public String shortenUrl(String originalUrl) {
        // 1. Gerar um hash curto (código de 6 a 8 caracteres)
        // Usamos a lib Guava para criar um código único baseado na URL
        String shortCode = Hashing.murmur3_32_fixed().hashString(originalUrl, StandardCharsets.UTF_8).toString();

        // 2. Salvar no Banco de Dados (Garantia de persistência)
        UrlEntity url = new UrlEntity(shortCode, originalUrl);
        urlRepository.save(url);

        // 3. Salvar no Redis (Para ficar rápido na próxima leitura)
        // A chave é o shortCode, o valor é a URL original.
        // Expira em 10 minutos (TTL) para não lotar a memória RAM à toa.
        redisTemplate.opsForValue().set(shortCode, originalUrl, 10, TimeUnit.MINUTES);

        return shortCode;
    }

    // --- LÓGICA 2: RECUPERAR URL (A MÁGICA DO CACHE) ---
    public String getOriginalUrl(String shortCode) {
        // 1. Tenta pegar do Redis (Memória RAM - Muito rápido) ⚡
        String cachedUrl = redisTemplate.opsForValue().get(shortCode);
        
        if (cachedUrl != null) {
            System.out.println("🔥 Cache HIT! (Encontrado no Redis: " + shortCode + ")");
            return cachedUrl;
        }

        // 2. Se não achou no Redis, busca no Banco (Disco - Mais lento) 🐢
        System.out.println("🐢 Cache MISS! (Buscando no Banco de Dados: " + shortCode + ")");
        
        UrlEntity urlEntity = urlRepository.findById(shortCode)
                .orElseThrow(() -> new RuntimeException("URL não encontrada"));

        // 3. Achou no banco? Salva no Redis agora! 
        // Assim, o próximo usuário que pedir esse link vai cair no "Cache HIT"
        redisTemplate.opsForValue().set(shortCode, urlEntity.getOriginalUrl(), 10, TimeUnit.MINUTES);

        return urlEntity.getOriginalUrl();
    }
}