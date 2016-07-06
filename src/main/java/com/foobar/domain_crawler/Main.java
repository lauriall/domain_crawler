package com.foobar.domain_crawler;


import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main  {

    private static final Logger log = Logger.getLogger( Crawler.class.getName() );

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("d", true, "Domain to be crawled");
        options.addOption("o", true, "Output file of results");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("d") && cmd.hasOption("o")) {
                String s = cmd.getOptionValue("d");
                File outPutFile = new File(cmd.getOptionValue("o"));
                Map<Integer, Map<Integer, List<URL>>> results = new Crawler(s).start().getSiteMap();
                CrawlerUtils.writeSiteMapToFil(results, outPutFile);
            }
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Failed parsing command line parameters ", e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "", e);
        }
    }

}