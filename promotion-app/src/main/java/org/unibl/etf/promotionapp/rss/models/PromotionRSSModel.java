package org.unibl.etf.promotionapp.rss.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.unibl.etf.promotionapp.db.models.Promotion;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PromotionRSSModel {
    private Promotion promotion;
    private String author;
}
