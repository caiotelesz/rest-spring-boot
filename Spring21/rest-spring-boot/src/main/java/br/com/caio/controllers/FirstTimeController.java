package br.com.caio.controllers;

import br.com.caio.model.FirstTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class FirstTimeController {

    private static final String template = "Hello, %s!";
    private static final AtomicLong counter = new AtomicLong();

    @RequestMapping("/firstTime")
    public FirstTime firstTime(@RequestParam(value = "name", defaultValue = "World")
                               String name) {
        return new FirstTime(counter.incrementAndGet(), String.format(template, name));
    }
}
