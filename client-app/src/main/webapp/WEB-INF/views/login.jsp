<%--
  Created by IntelliJ IDEA.
  User: ognjen
  Date: 29.1.2025.
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>

<%@page import="org.unibl.etf.promotionapp.beans.LoginBean" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="loginBean" class="org.unibl.etf.promotionapp.beans.LoginBean" scope="request"/>
<jsp:setProperty name="loginBean" property="username" param="username"/>
<jsp:setProperty name="loginBean" property="password" param="password"/>

<%@page import="org.unibl.etf.promotionapp.beans.MessageBean" %>
<jsp:useBean id="messageBean" class="org.unibl.etf.promotionapp.beans.MessageBean" scope="request"/>
<jsp:setProperty name="messageBean" property="message" param="message"/>

<html>
<head>
    <title>Clients | Login</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/stylesheet.css"/>
</head>
<body class="bg-slate-800 text-white flex flex-col items-center justify-center h-screen gap-4 max-w-screen-sm mx-auto">
    <div class="my-4 w-full">
        <h1 class="text-2xl font-bold text-left">Welcome!</h1>
        <p class="text-left text-xl">Please sign in.</p>
    </div>

    <form method="POST" action="login"
            class="border-2 border-white border-opacity-15 rounded-lg shadow-xl p-4 flex flex-col justify-center gap-4 w-full">
        <label class="flex flex-col text-lg px-4 w-full">
            <span class="text-xl font-bold mb-2">Username</span>
            <input type="text"
                   name="username"
                   placeholder="Username"
                   class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
        </label>

        <label class="flex flex-col text-lg px-4 w-full">
            <span class="text-xl font-bold mb-2">Password</span>
            <input type="password"
                   name="password"
                   placeholder="Password"
                   class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
        </label>

        <button type="submit"
                class="border-2 bg-opacity-15 text-2xl hover:bg-opacity-30 duration-200 transition-all relative rounded-lg p-4 m-4 z-10 hover:cursor-pointer hover:bg-white hover:text-black duration-300 transition-all"
        >
            Sign in
        </button>
    </form>

    <form method="GET" action="register" class="bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4">
        <button type="submit">No account? Register here!</button>
    </form>

    <p class="text-lg text-red-300 my-4 font-semibold"><%= session.getAttribute("message") != null ? session.getAttribute("message") : "" %></p>
</body>
</html>
