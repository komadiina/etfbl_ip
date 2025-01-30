<%@ page import="org.unibl.etf.promotionapp.beans.UserBean" %>
<%@ page import="org.unibl.etf.promotionapp.beans.TransportationDeviceBean" %>
<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.promotionapp.db.models.TransportationDevice" %>

<%--
  Created by IntelliJ IDEA.
  User: ognjen
  Date: 29.1.2025.
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clients | Dashboard</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
</head>
<body class="bg-slate-800 text-white flex flex-col items-center justify-center h-screen gap-4 max-w-screen-sm mx-auto">
    <div class="w-full">
        <div class="flex flex-col items-start justify-start top-5 absolute">
            <h1 class="text-2xl font-bold text-left">Dashboard</h1>
            <p class="text-xl text-left">Welcome, <%= ((UserBean)session.getAttribute("userBean")).getFirstName() %> <%= ((UserBean)session.getAttribute("userBean")).getLastName() %></p>
        </div>

        <div class="flex flex-col items-center justify-center gap-4 w-full">
            <form method="post" action="dashboard?pageRequest=rentCar" class="w-full">
                <button type="submit" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full">Rent a Car</button>
            </form>

            <form method="post" action="dashboard?pageRequest=rentBike" class="w-full">
                <button type="submit" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full">Rent an e-Bike</button>
            </form>

            <form method="post" action="dashboard?pageRequest=rentScooter" class="w-full">
                <button type="submit" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full">Rent an e-Scooter</button>
            </form>

            <form method="post" action="dashboard?pageRequest=editProfile" class="w-full">
                <button type="submit" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full">Profile</button>
            </form>
        </div>
    </div>
</body>
</html>
