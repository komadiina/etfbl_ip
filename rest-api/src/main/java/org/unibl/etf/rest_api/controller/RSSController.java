package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.rest_api.controller.rss.PostsRssFeedView;
import org.unibl.etf.rest_api.controller.rss.PromotionsRssFeedView;

@RestController
@RequestMapping("/rss")
public class RSSController {
    @Autowired
    private PromotionsRssFeedView promotionsRssFeedView;

    @Autowired
    private PostsRssFeedView postsRssFeedView;

    @GetMapping("/promotions")
    public PromotionsRssFeedView getPromotionsRssFeed() {
        return promotionsRssFeedView;
    }

    @GetMapping("/posts")
    public PostsRssFeedView getPostsRssFeed() {
        return postsRssFeedView;
    }
}
