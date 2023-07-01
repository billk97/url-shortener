package dev.konstantinou.urlshortener.controllers;

import dev.konstantinou.urlshortener.dtos.HealthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api-prefix}/health")
public class HealthApi {

    @GetMapping()
    public HealthDTO isUp() {
        return new HealthDTO(true);
    }

}
