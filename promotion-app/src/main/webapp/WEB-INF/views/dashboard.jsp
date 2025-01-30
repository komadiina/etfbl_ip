<%@ page import="org.unibl.etf.promotionapp.db.models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.promotionapp.rss.models.PostRSSModel" %>
<%@ page import="org.unibl.etf.promotionapp.rss.models.PromotionRSSModel" %><%--
  Created by IntelliJ IDEA.
  User: ognjen
  Date: 30.1.2025.
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees | Dashboard</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
    <script>
      function initListeners() {
        document.getElementById('promotionFilter').addEventListener('input', function () {
          const filter = this.value.toLowerCase();
          console.log(filter)
          const promotions = document.querySelectorAll('#promotionsList .promotion');
          promotions.forEach(promotion => {
            const content = promotion.textContent.toLowerCase();
            promotion.style.display = content.includes(filter) ? '' : 'none';
          });
        });

        document.getElementById('postFilter').addEventListener('input', function () {
          const filter = this.value.toLowerCase();
          console.log(filter)
          const posts = document.querySelectorAll('#postsList .post');
          posts.forEach(post => {
            const content = post.textContent.toLowerCase();
            post.style.display = content.includes(filter) ? '' : 'none';
          });
        });
      }
    </script>
</head>
<body onload="initListeners()" class="bg-slate-800 text-white">
    <div class="container mx-auto p-6">
        <h1 class="text-3xl font-bold mb-6">Dashboard</h1>

        <section class="mb-8">
            <h2 class="text-2xl font-semibold mb-4">Promotions</h2>
            <input type="text" id="promotionFilter" placeholder="Filter by description" class="p-2 border rounded mb-4">
            <div id="promotionsList">
                <% {
                    for (PromotionRSSModel promotion : (List<PromotionRSSModel>)request.getSession().getAttribute("promotionsRSS")) {
                        String html = String.format(
                                "<div class=\"promotion bg-slate-700 p-4 rounded shadow-xl mb-2 rounded-lg\">" +
                                        "<h3 class=\"font-bold\">%s</h3>" +
                                        "<p>%s</p>" +
                                        "<p class=\"text-sm text-gray-300 italic\">%s at %s</p>" +
                                        "</div>",
                                promotion.getPromotion().getTitle(),
                                promotion.getPromotion().getDescription(),
                                promotion.getAuthor(), promotion.getPromotion().getStartDate());

                        out.println(html);
                    }
                }%>
            </div>
        </section>

        <section class="mb-8">
            <h2 class="text-2xl font-semibold mb-4">Posts</h2>
            <input type="text" id="postFilter" placeholder="Filter by content" class="p-2 border rounded mb-4">
            <div id="postsList">
                <% {
                    for (PostRSSModel post : (List<PostRSSModel>)request.getSession().getAttribute("postsRSS")) {
                        String html = String.format(
                                "<div class=\"post bg-slate-700 p-4 rounded shadow-xl mb-2 rounded-lg\">" +
                                        "<h3 class=\"font-bold\">%s</h3>" +
                                        "<p>%s</p>" +
                                        "<p class=\"text-sm text-gray-500 italic\">%s at %s</p>" +
                                        "</div>",
                                post.getPost().getTitle(),
                                post.getPost().getContent(),
                                post.getAuthor(), post.getPost().getDisplayDate());

                        out.println(html);
                    }
                }%>
            </div>
        </section>

        <%= session.getAttribute("message") != null ? session.getAttribute("message") : "" %>

        <section>
            <h2 class="text-2xl font-semibold mb-4">New Promotion</h2>
            <form action="dashboard?action=createPromotion" method="POST" class="bg-slate-700 p-6 rounded shadow-xl">
                <input type="hidden" name="action" value="createPromotion">
                <div class="mb-4">
                    <label for="promotionTitle" class="block mb-2 font-bold px-2">Title</label>
                    <input type="text" id="promotionTitle" name="title" required class="w-full p-2 border rounded-lg">
                </div>
                <div class="mb-4">
                    <label for="promotionDescription" class="block mb-2 px-2">Description</label>
                    <textarea id="promotionDescription" name="description" required class="w-full p-2 border rounded-lg"></textarea>
                </div>
                <div class="mb-4">
                    <label for="promotionEndDate" class="block mb-2 px-2">Description</label>
                    <input id="promotionEndDate" name="endDate" required class="w-full p-2 border rounded-lg" type="date"/>
                </div>
                <button type="submit" class="bg-slate-400 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Create Promotion
                </button>
            </form>
        </section>

        <section>
            <h2 class="text-2xl font-semibold my-4">New Post</h2>
            <form action="dashboard?action=createPost" method="POST" class="bg-slate-700 p-6 rounded shadow-xl">
                <input type="hidden" name="action" value="createPromotion">
                <div class="mb-4">
                    <label for="postTitle" class="block mb-2 font-bold px-2">Title</label>
                    <input type="text" id="postTitle" name="title" required class="w-full p-2 border rounded-lg">
                </div>
                <div class="mb-4">
                    <label for="postDescription" class="block mb-2 px-2">Content</label>
                    <textarea id="postDescription" name="description" required class="w-full p-2 border rounded-lg"></textarea>
                </div>
                <button type="submit" class="bg-slate-400 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Create Promotion
                </button>
            </form>
        </section>
    </div>
</body>
</html>
