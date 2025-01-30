package org.unibl.etf.promotionapp.servlets;

import org.unibl.etf.promotionapp.db.models.Employee;
import org.unibl.etf.promotionapp.db.models.Post;
import org.unibl.etf.promotionapp.db.models.Promotion;
import org.unibl.etf.promotionapp.db.mysql.PostRepository;
import org.unibl.etf.promotionapp.db.mysql.PromotionRepository;
import org.unibl.etf.promotionapp.rss.RSSConsumer;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.unibl.etf.promotionapp.util.DateParser;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private PromotionRepository promotionRepository = new PromotionRepository();
    private PostRepository postRepository = new PostRepository();

    private boolean anyNull(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isBlank() || arg.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getSession().setAttribute("promotionsRSS", RSSConsumer.consumePromotionRSS());
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("promotionsRSS", new ArrayList<>());
        }

        try {
            request.getSession().setAttribute("postsRSS", RSSConsumer.consumePostRSS());
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("postsRSS", new ArrayList<>());
        }

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
//        request.getParameterMap().forEach((key, value) -> System.out.println(key + ": " + Arrays.toString(value)));

        if (action == null || action.isBlank() || action.isEmpty()) {
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
            return;
        } else if (action.equals("createPost")) {
            String title = request.getParameter("title");
            String content = request.getParameter("description");
            if (anyNull(title, content)) {
                request.getSession().setAttribute("message", "Title and content cannot be empty.");
                request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
                return;
            }

            // create post
            // boze dragi sto nisam builder implementiro krvi moja
            Post post = new Post();
            post.setCreatedAt(LocalDateTime.now());
            post.setEmployeeID(((Employee)request.getSession().getAttribute("user")).getId());
            post.setContent(content);
            post.setTitle(title);
            System.out.println(post);
            postRepository.create(post);

            // reload posts
            try {
                request.getSession().setAttribute("postsRSS", RSSConsumer.consumePostRSS());
                request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.equals("createPromotion")) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            if (anyNull(title, description)) {
                request.getSession().setAttribute("message", "Title and description cannot be empty.");
                request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
                return;
            }

            // create promotion
            Promotion promotion = new Promotion();
            promotion.setStartDate(java.util.Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(1)))); // hahahhahahahahhahdsakiudhaskjdakjhsh
            promotion.setEndDate(DateParser.parseDate(request.getParameter("endDate")));
            promotion.setTitle(title);
            promotion.setDescription(description);
            promotion.setEmployeeID(((Employee)request.getSession().getAttribute("user")).getId());
            System.out.println(promotion);
            promotionRepository.create(promotion);


            // reload promotions
            try {
                request.getSession().setAttribute("promotionsRSS", RSSConsumer.consumePromotionRSS());
                request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
