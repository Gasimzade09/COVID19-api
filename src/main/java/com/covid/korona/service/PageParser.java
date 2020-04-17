package com.covid.korona.service;


import com.covid.korona.model.CityStat;
import com.covid.korona.model.Model;
import com.covid.korona.model.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PageParser {
    public Response getDailyStat() throws IOException {
        String url = "https://koronavirusinfo.az/az";
        Document response = Jsoup.connect(url).get();
        Elements elements = response.getElementsByClass("counter");
        String row = elements.get(0).toString();
        int firstIndex = row.indexOf("<strong>")+8;
        int lastIndex = row.indexOf("</strong>");
        String infectedCount = row.substring(firstIndex, lastIndex);
        row = elements.get(1).toString();
        firstIndex = row.indexOf("<strong>")+8;
        lastIndex = row.indexOf("</strong>");
        String newInfectedCount = row.substring(firstIndex, lastIndex);
        row = elements.get(2).toString();
        firstIndex = row.indexOf("<strong>")+8;
        lastIndex = row.indexOf("</strong>");
        String recoveredCount = row.substring(firstIndex, lastIndex);

        row = elements.get(3).toString();
        firstIndex = row.indexOf("<strong>")+8;
        lastIndex = row.indexOf("</strong>");
        String deadCount = row.substring(firstIndex, lastIndex);
        row = elements.get(4).toString();
        firstIndex = row.indexOf("<strong>")+8;
        lastIndex = row.indexOf("</strong>");
        String activeInfectedCount = row.substring(firstIndex, lastIndex);
        Model model = new Model(infectedCount, newInfectedCount, recoveredCount, deadCount, activeInfectedCount);

        Response response1 = getStatForCity();
        response1.setModel(model);
        return response1;
    }

    public Response getStatForCity() throws IOException {
        String url = "https://koronavirusinfo.az/az/page/statistika/azerbaycanda-cari-veziyyet";
        Document response = Jsoup.connect(url).get();
        Elements elements = response.getElementsByTag("script");
        String result = elements.toString();
        Integer firstIndex = result.indexOf("countryStat") + 15;
        Integer lastIndex = result.indexOf("capitalStat") - 25;
        result = result.substring(firstIndex, lastIndex);
        List<CityStat> cityStats = new ArrayList<>();
        String string[] = result.split("},");
        for (int i = 0; i < string.length - 1; i++) {
            firstIndex = string[i].indexOf("{") + 2;
            string[i] = string[i].substring(firstIndex);
            String cities[] = string[i].split(",");
            String cityName = cities[0].substring(cities[0].indexOf(": \"") + 3, cities[0].length() - 1);
            Integer count = Integer.parseInt(cities[1].substring(cities[1].indexOf(": ") + 2));
            CityStat cityStat = new CityStat(cityName, count);
            cityStats.add(cityStat);
        }
        Response response1 = new Response();
        response1.setCityStats(cityStats);
        return response1;
    }

    @Scheduled(fixedDelay = 1800000L)
    public void dontSleep(){
        System.out.println("I am not sleeping! =)");
    }
}
