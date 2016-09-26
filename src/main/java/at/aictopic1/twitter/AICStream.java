/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.twitter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 *
 */
public class AICStream {
    private static String filePath;
    private List<String> track;
    private List<String> lang;

    public AICStream(String jsonFile) {
        this.track = new ArrayList();
        this.lang = new ArrayList();

        File file = new File(jsonFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Logger.getLogger(AICStream.class.getName()).log(Level.INFO, "File {0} created.", file.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(AICStream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        AICStream.filePath = file.getAbsolutePath();
    }

    public void addTrack(String company) {
        String low = new StringBuilder()
                .append('#')
                .append(company.toLowerCase())
                .toString();
        this.track.add(low);

        String upp = new StringBuilder()
                .append('#')
                .append(company.toUpperCase())
                .toString();
        this.track.add(upp);

        String cam = new StringBuilder()
                .append('#')
                .append(company.substring(0, 1).toUpperCase())
                .append(company.substring(1).toLowerCase())
                .toString();
        this.track.add(cam);

        Logger.getLogger(AICStream.class.getName()).log(Level.INFO, "Keyword {0} added.", company);
    }

    public void remTrack(String company) {
        String low = new StringBuilder()
                .append('#')
                .append(company.toLowerCase())
                .toString();
        this.track.remove(low);

        String upp = new StringBuilder()
                .append('#')
                .append(company.toUpperCase())
                .toString();
        this.track.remove(upp);

        String cam = new StringBuilder()
                .append('#')
                .append(company.substring(0, 1).toUpperCase())
                .append(company.substring(1).toLowerCase())
                .toString();
        this.track.remove(cam);

        Logger.getLogger(AICStream.class.getName()).log(Level.INFO, "Keyword {0} removed.", company);
    }

    public void addLang(String lang) {
        this.lang.add(lang);
        Logger.getLogger(AICStream.class.getName()).log(Level.INFO, "Lanaguage {0} added.", lang);
    }

    public void startStream() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(getListener());

        twitterStream.filter(getQuery());

    }

    public void stopStream() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.shutdown();
    }

    private StatusListener getListener() {
        return new StatusListener() {
            public void onStatus(Status status) {
                String rawJSON = TwitterObjectFactory.getRawJSON(status);

                File file = new File(AICStream.filePath);

                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(file, true));
                    writer.append(rawJSON);
                    writer.newLine();
                    writer.flush();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AICStream.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AICStream.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException ex) {
                            Logger.getLogger(AICStream.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }
        };
    }

    private FilterQuery getQuery() {
        FilterQuery query = new FilterQuery();
        String[] track = this.track.toArray(new String[this.track.size()]);
        query.track(track);
        String[] lang = this.lang.toArray(new String[this.lang.size()]);
        query.language(lang);

        return query;
    }
}
