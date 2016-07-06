package com.foobar.domain_crawler;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class CrawlerUtils {

    private static final UrlValidator uv;

    private static final Logger log = Logger.getLogger( Crawler.class.getName() );

    static {
        uv = new UrlValidator();
    }

    private CrawlerUtils() {}

    static boolean urlIsValid(String s) {
        return uv.isValid(s);
    }

    static String getUrlWithoutQuery(URL url) {
        String urlString = url.toString();
        return (urlString.contains("?")) ? urlString.substring(0, url.toString().indexOf("?")) : urlString;
    }

    static List<URL> getAllUrlsFromUrl(URL url) {
        List<URL> results = new ArrayList<URL>();
        try {
            Document doc = Jsoup.connect(url.toString()).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String linkStr = link.attr("abs:href");
                if(CrawlerUtils.urlIsValid(linkStr))
                    results.add(new URL(linkStr));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed retrieving links from " + url.toString(), e);
        }

        return results;
    }

    static void writeSiteMapToFil(Map<Integer, Map<Integer, List<URL>>> results, File outPutFile) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(outPutFile.getAbsolutePath(), "UTF-8");
        writer.println("Id, Parent, URL");
        for (Integer id : results.keySet()) {
            for (Integer parent : results.get(id).keySet()) {
                for (URL url : results.get(id).get(parent)) {
                    writer.write(id + "," + parent + "," + url.toString() + "\n");
                }
            }
        }
        writer.close();
    }
}
