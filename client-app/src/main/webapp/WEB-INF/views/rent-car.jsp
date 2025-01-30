<%@ page import="org.unibl.etf.promotionapp.db.models.TransportationDevice" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ognjen
  Date: 29.1.2025.
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clients | Rentals | Car</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
    <script src="${pageContext.request.contextPath}/scripts/timer.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/jquery-3.7.1.js"></script>
</head>
<body onload="init()" class="bg-slate-800 text-white flex flex-col items-center justify-center h-screen gap-4 max-w-screen-sm mx-auto">
    <form id="rentalForm" class="flex flex-col w-full gap-2" method="POST" action="rent-car">
        <input type="hidden" id="rentalAction" name="rentalAction" value="start">
        <select name="deviceID" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full rounded-md">
            <%
                for (TransportationDevice td : (List<TransportationDevice>)(request.getSession().getAttribute("vehicles"))) {
                    String html = String.format(
                            "<option value=\"%s\" class=\"text-black\">%s</option>",
                            td.getDeviceID(),
                            td.getModel()
                    );
                    out.println(html);
                }
            %>
        </select>

        <input type="number" name="dropoffX" placeholder="Drop-off X" class="p-4 border-2 text-lg my-2 rounded-md"/>
        <input type="number" name="dropoffY" placeholder="Drop-off Y" class="p-4 border-2 text-lg my-2 rounded-md"/>
        <input type="text" name="paymentCard" placeholder="Payment card number" class="p-4 border-2 text-lg my-2 rounded-md"/>
        <input type="text" name="driversLicense" placeholder="Drivers license ID" class="p-4 border-2 text-lg my-2 rounded-md"/>
        <button id="startRentalBtn" type="submit" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full rounded-md">Rent</button>
    </form>

    <%= request.getSession().getAttribute("message") != null ? request.getSession().getAttribute("message") : "" %>

    <% if (request.getAttribute("rentalStarted") != null && (boolean)request.getAttribute("rentalStarted")) { %>
    <div id="stopRentalDiv" class="mt-4 flex flex-col justify-center items-center">
        <form method="post" action="rent-car?rentalAction=requestPDF">
            <button type="submit" class="p-4 border-2 text-xl font-bold hover:cursor-pointer w-full rounded-md">Download PDF</button>
        </form>
<%--        <p class="italic text-lg">Rental progress:</p>--%>
<%--        <p class="font-bold text-xl" id="timer">00:00:00</p>--%>
        <form method="post" action="rent-car?rentalAction=stop">
            <button id="stopRentalBtn" type="submit" class="p-2 border-2 text-lg font-bold hover:cursor-pointer mt-2">Stop Rental</button>
        </form>
    </div>
    <% } %>
</body>
</html>
