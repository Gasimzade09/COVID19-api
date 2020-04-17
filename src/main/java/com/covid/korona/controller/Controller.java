package com.covid.korona.controller;

import com.covid.korona.model.Response;
import com.covid.korona.service.PageParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {
    private final PageParser pageParser;

    public Controller(PageParser pageParser) {
        this.pageParser = pageParser;
    }

    @GetMapping(path = "/")
    public Response sendMessage(){
        try {
            return pageParser.getDailyStat();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
