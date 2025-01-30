package org.unibl.etf.promotionapp.rss.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.unibl.etf.promotionapp.db.models.Post;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostRSSModel {
    private Post post;
    private String author;
}
