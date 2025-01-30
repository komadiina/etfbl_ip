package org.unibl.etf.rest_api.controller.rss;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import org.unibl.etf.rest_api.model.db.Post;
import org.unibl.etf.rest_api.model.db.Promotion;
import org.unibl.etf.rest_api.service.crud.PostService;
import org.unibl.etf.rest_api.service.crud.PromotionService;
import org.unibl.etf.rest_api.service.crud.UserService;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PostsRssFeedView extends AbstractRssFeedView {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle("Promotions RSS feed");
        feed.setDescription("IP Project - Promotions RSS feed");
        feed.setLink("http://localhost:8080/api/promotions");
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Post> posts = postService.retrieveAll();
        List<Item> items = new ArrayList<>();

        for (Post post : posts) {
            Item entry = new Item();
            entry.setAuthor(userService.retrieve(post.getEmployeeID()).getUsername());
            entry.setTitle(post.getTitle());
            entry.setPubDate(Date.from(post.getCreatedAt().toInstant(ZoneOffset.UTC)));

            Content content = new Content();
            content.setType("text");
            content.setValue(post.getContent());
            entry.setContent(content);

            items.add(entry);
        }

        return items;
    }
}
