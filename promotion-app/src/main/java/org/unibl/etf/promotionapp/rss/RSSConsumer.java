package org.unibl.etf.promotionapp.rss;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.unibl.etf.promotionapp.db.models.Post;
import org.unibl.etf.promotionapp.db.models.Promotion;
import org.unibl.etf.promotionapp.rss.models.PostRSSModel;
import org.unibl.etf.promotionapp.rss.models.PromotionRSSModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RSSConsumer {
    private static String BASE_URL = "http://localhost:8080/rss";

    public static List<PromotionRSSModel> consumePromotionRSS() throws IOException, FeedException {
        URL endpoint = new URL(BASE_URL + "/promotions");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(endpoint));

        List<PromotionRSSModel> result = new ArrayList<>();
        feed.getEntries().forEach(entry -> {
            Promotion promotion = new Promotion();

            if (entry.getTitle() != null) {
                promotion.setTitle(entry.getTitle());
            } else {
                promotion.setTitle("No title.");
            }

            // content:encoded pa zato nmg direktno content parsovati lolllllllll dobro
            if (!entry.getContents().isEmpty()) {
                promotion.setDescription(entry.getContents().getFirst().getValue());
            } else if (entry.getDescription() != null) {
                promotion.setDescription(entry.getDescription().getValue());
            } else {
                promotion.setDescription("No description.");
            }

            // Parse the date correctly
            if (entry.getPublishedDate() != null) {
                promotion.setStartDate(entry.getPublishedDate());
                promotion.setEndDate(entry.getPublishedDate()); // todo: thingamajig
            }


            result.add(new PromotionRSSModel(promotion, entry.getAuthor()));
        });

//        System.out.println(result);
        return result;
    }

    public static List<PostRSSModel> consumePostRSS() throws IOException, FeedException {
        URL endpoint = new URL(BASE_URL + "/posts");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(endpoint));

        List<PostRSSModel> result = new ArrayList<>();
        feed.getEntries().forEach(entry -> {
            Post post = new Post();

            if (entry.getTitle() != null) {
                post.setTitle(entry.getTitle());
            } else {
                post.setTitle("No title.");
            }

            // content:encoded pa zato nmg direktno content parsovati lolllllllll dobro
            if (!entry.getContents().isEmpty()) {
                post.setContent(entry.getContents().getFirst().getValue());
            } else if (entry.getDescription() != null) {
                post.setContent(entry.getDescription().getValue());
            } else {
                post.setContent("No description.");
            }

            // Parse the date correctly
            if (entry.getPublishedDate() != null) {
                post.setDate(entry.getPublishedDate().toString());
            }
            result.add(new PostRSSModel(post, entry.getAuthor()));
        });

        return result;
    }
}
