package org.saas.project.service.Impl;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.saas.project.service.UrlTitleService;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import org.jsoup.nodes.Document;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class UrlTitleServiceImpl implements UrlTitleService {
    @SneakyThrows
    @Override
    public String getTitleFromUrl(String url) {
        URL targetUrl = new URL(url);

        HttpURLConnection connection = (HttpsURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = Jsoup.connect(url).get();
            return document.title();
        }
        return "Error while fetching title.";
    }
}
