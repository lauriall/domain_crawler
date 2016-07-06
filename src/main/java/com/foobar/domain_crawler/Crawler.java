package com.foobar.domain_crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Crawler {

    private final URL startDomain;
    private int id = 0;
    private final List<String> results;
    private final Map<Integer, Map<Integer, List<URL>>> siteMap;

    Crawler(String startDomain) throws MalformedURLException {
        results = new ArrayList<String>();
        this.siteMap = new HashMap<Integer, Map<Integer, List<URL>>>();
        this.startDomain = new URL(startDomain);
    }

    Crawler start() throws IOException {
        this.siteMap.put(0, new HashMap<Integer, List<URL>>());
        this.siteMap.get(0).put(0, new ArrayList<URL>());
        this.siteMap.get(0).get(0).add(this.startDomain);
        List<URL> urlsFound = CrawlerUtils.getAllUrlsFromUrl(startDomain);
        crawl(urlsFound, 0);
        return this;
    }

    private void crawl(List<URL> urlsToCrawl, int parent) throws IOException {
        for (URL url : urlsToCrawl) {
            if (!url.getHost().equals(startDomain.getHost()))
                continue;
            if (!CrawlerUtils.urlIsValid(CrawlerUtils.getUrlWithoutQuery(url)))
                continue;
            if (!results.contains(CrawlerUtils.getUrlWithoutQuery(url))) {
                id++;
                if(siteMap.get(id) == null)
                    siteMap.put(id, new HashMap<Integer, List<URL>>());
                if(siteMap.get(id).get(parent) == null)
                    siteMap.get(id).put(parent, new ArrayList<URL>());
                siteMap.get(id).get(parent).add(url);
                results.add(CrawlerUtils.getUrlWithoutQuery(url));
                List<URL> urlsFound = CrawlerUtils.getAllUrlsFromUrl(url);
                crawl(urlsFound, parent++);
            }
        }
    }

    Map<Integer, Map<Integer, List<URL>>> getSiteMap() {
        return siteMap;
    }

}
