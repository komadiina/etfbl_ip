<%--
  Created by IntelliJ IDEA.
  User: ognjen
  Date: 29.1.2025.
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clients | Register</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
</head>
<body class="bg-slate-800 text-white flex flex-col items-center justify-center h-screen gap-4 max-w-screen-sm mx-auto">
  <form method="POST" action="register">
    <h1 class="text-2xl font-bold text-left">Register</h1>
    <p class="text-left text-xl">Please fill in the form below.</p>
    <div class="flex flex-col gap-2">
      <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Username</span>
        <input type="text"
               name="username"
               placeholder="Username (*)"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
      </label>

      <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Password</span>
        <input type="password"
               name="password"
               placeholder="Password (*)"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
      </label>

      <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Email</span>
        <input type="text"
               name="email"
               placeholder="Email (*)"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
      </label>

      <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">First name</span>
        <input type="text"
               name="firstName"
               placeholder="First name (*)"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
      </label>

      <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Last name</span>
        <input type="text"
               name="lastName"
               placeholder="Last name (*)"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
      </label>

      <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Phone number</span>
        <input type="text"
               name="phoneNumber"
               placeholder="Phone number (*)"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
      </label>

        <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Domestic citizen? Enter your ID card number</span>
        <input type="text"
               name="idCardNumber"
               placeholder="ID card number"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
        </label>
        <label class="flex flex-col text-lg px-4 w-full">
        <span class="text-xl font-bold mb-2">Foreign citizen? Enter you passport ID</span>
        <input type="text"
               name="passportID"
               placeholder="Passport ID"
               class="border-2 bg-opacity-0 bg-gray-500 hover:bg-opacity-10 duration-200 transition-all relative rounded-lg p-4"/>
        </label>
    </div>

    <button type="submit" class="my-4 w-full border-2 bg-opacity-15 text-2xl hover:bg-opacity-30 duration-200 transition-all relative rounded-lg hover:cursor-pointer hover:bg-white hover:text-black duration-300 transition-all">Submit</button>
  </form>

  <p class="my-4 text-xl text-red-300"><%= session.getAttribute("message") != null ? session.getAttribute("message") : "" %></p>
</body>
</html>
