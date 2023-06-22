package dev.konstantinou.urlshortener.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${server.api-prefix}/stats")
public class StatsController {

    // TODO get top 10 link
    // TODO get last  10 links
}
