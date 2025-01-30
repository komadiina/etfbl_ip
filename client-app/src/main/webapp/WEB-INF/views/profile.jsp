<%@ page import="org.unibl.etf.promotionapp.db.models.Rental" %>
<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.promotionapp.beans.RentalInfo" %><%--
  Created by IntelliJ IDEA.
  User: ognjen
  Date: 30.1.2025.
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clients | Profile</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
</head>
<body class="bg-slate-800 text-white flex flex-col items-center justify-center h-screen gap-4 max-w-screen-sm mx-auto">
    <h1 class="text-4xl font-bold">Profile</h1>

    <%-- edit username --%>
    <form method="post" action="?action=editUsername" class="flex flex-col items-start justify-start gap-4 shadow-xl">
        <input type="text" name="username" placeholder="Change username" class="rounded-md border-2 p-2" />
        <button type="submit" class="bg-opacity-15 bg-opacity-15 w-full rounded-md border-2 px-4 py-2 shadow-2xl">Submit</button>
        <hr class="w-full rounded-xl border-2 border-white" />
    </form>

    <form method="post" action="?action=editPassword" class="flex flex-col items-start justify-start gap-4 shadow-xl">
        <input type="password" name="password" placeholder="Change password" class="rounded-md border-2 p-2" />
        <button type="submit" class="bg-opacity-15 bg-opacity-15 w-full rounded-md border-2 px-4 py-2 shadow-2xl">Submit</button>
        <hr class="w-full rounded-xl border-2 border-white" />
    </form>

    <p class="text-red-600">
        <%= request.getSession().getAttribute("message") != null ? request.getSession().getAttribute("message") : "" %>
    </p>

    <%-- user's rental history --%>
    <%{
       for (RentalInfo rental : (List<RentalInfo>)request.getSession().getAttribute("rentalInfoHistory")) {
           String html = String.format(
                   "<div class=\"flex flex-col gap-2 border-2 border-slate-600 p-4 shadow-xl\"> " +
                       "<p class=\"text-lg \">Date: %s, %s</p>" +
                       "<p class=\"text-lg \">Device: %s</p>" +
                       "<p class=\"text-lg \">Duration (s): %s</p>" +
                       "<p class=\"text-lg \">Pickup X: %d</p>" +
                       "<p class=\"text-lg \">Pickup Y: %d</p>" +
                       "<p class=\"text-lg \">DropoffX: %d</p>" +
                       "<p class=\"text-lg \">DropoffY: %d</p>" +
                       "<p class=\"text-lg \">PPM: $%.2f</p>" +
                       "<p class=\"text-lg \">Total cost: $%.2f</p>" +
                   "</div>",
                   rental.getStartDateTime().toString().split("T")[0],
                   rental.getStartDateTime().toString().split("T")[1],
                   rental.getDevice().getModel(),
                   rental.getDuration(),
                   rental.getPickupX(),
                   rental.getPickupY(),
                   rental.getDropoffX(),
                   rental.getDropoffY(),
                   rental.getPrice().getPricePerMinute(),
                   rental.getPrice().getPricePerMinute() * rental.getDuration().doubleValue() / 60.0
           );

           out.println(html);
       }
    }%>
</body>
</html>
