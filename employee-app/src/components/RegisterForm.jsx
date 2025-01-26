import '../styles/components.css'
import '../styles/animations.css'
import '../index.css'
import { useState } from "react";
import api from "../utils/api.js";

export const RegisterForm = () => {
  const [username, setUsername] = useState(null);
  const [password, setPassword] = useState(null);
  const [confirmPassword, setConfirmPassword] = useState(null);
  const [email, setEmail] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);

  const [isForeignCitizen, setIsForeignCitizen] = useState(false);
  const [idCardNumber, setIdCardNumber] = useState(null);
  const [passportNumber, setPassportNumber] = useState(null);

  const [errorMessage, setErrorMessage] = useState(null);

  const highlightRed = (element) => element.classList.add("highlight-red-outline");
  const clearHighlight = (element) => {
    setErrorMessage(null);
    element.classList.remove("highlight-red-outline");
  }

  const validateInputs = () => {
    let status = true;

    // no fields can be empty
    if (!username || username.trim() === "") {
      highlightRed(document.getElementById("username"));
      setErrorMessage("Username cannot be empty.");
      status = false;
    }

    if (!password || password.trim() === "") {
      highlightRed(document.getElementById("password"));
      setErrorMessage("Password cannot be empty.");
      status = false;
    }

    if (!email || email.trim() === "") {
      highlightRed(document.getElementById("email"));
      setErrorMessage("Email cannot be empty.");
      status = false;
    }

    if (!firstName || firstName.trim() === "") {
      highlightRed(document.getElementById("first-name"));
      setErrorMessage("First name cannot be empty.");
      status = false;
    }

    if (!lastName || lastName.trim() === "") {
      highlightRed(document.getElementById("last-name"));
      setErrorMessage("Last name cannot be empty.");
      status = false;
    }

    if (!phoneNumber || phoneNumber.trim() === "") {
      highlightRed(document.getElementById("phone-number"));
      setErrorMessage("Phone number cannot be empty.");
      status = false;
    }

    if (password !== confirmPassword) {
      highlightRed(document.getElementById("password"));
      highlightRed(document.getElementById("confirmPassword"));
      setErrorMessage("Passwords do not match.");
      status = false;
    }

    if (!isForeignCitizen && (!idCardNumber || idCardNumber.trim() === "")) {
      highlightRed(document.getElementById("id-card-number"));
      setErrorMessage("ID card number cannot be empty.");
      status = false;
    }

    if (isForeignCitizen && (!passportNumber || passportNumber.trim() === "")) {
      highlightRed(document.getElementById("passport-number"));
      setErrorMessage("Passport number cannot be empty.");
      status = false;
    }

    return status;
  }

  const tryRegister = async () => {
    if (validateInputs() == false) return;

    const body = {
      username: username,
      password: password,
      email: email,
      firstName: firstName,
      lastName: lastName,
      phoneNumber: phoneNumber,
      isForeignCitizen: isForeignCitizen,
      userType: "Client",
      idCardNumber: idCardNumber,
      passportNumber: passportNumber
    };

    const response = await api.register(body);
    let success = response.status === 200, message = "Registration unsuccessful";

    if (success) {
      localStorage.setItem("username", username);
    } else {
      message = response.data.error;
    }

    return { success, message };
  }


  return (
    <div className={"card w-full flex flex-col justify-start items-start gap-4"}>
      <h1 className={"text-2xl"}>Welcome!</h1>
      <p>Fill out the form below to register.</p>

      <input onChange={(e) => { clearHighlight(document.getElementById("username")); setUsername(e.target.value) }}
        id='username'
        className={"minimal-input-compact w-full hover-outline"}
        type="text" placeholder="Username" />

      <input onChange={(e) => { clearHighlight(document.getElementById("email")); setEmail(e.target.value) }}
        id='email'
        className={"minimal-input-compact w-full hover-outline"}
        type="email"
        placeholder="Email" />

      <input onChange={(e) => { clearHighlight(document.getElementById("password")); setPassword(e.target.value) }}
        id='password'
        className={"minimal-input-compact w-full hover-outline"}
        type="password"
        placeholder="Password" />

      <input onChange={(e) => { clearHighlight(document.getElementById("confirm-password")); setConfirmPassword(e.target.value) }}
        id='confirm-password'
        className={"minimal-input-compact w-full hover-outline"}
        type="password"
        placeholder="Confirm Password" />

      <input onChange={(e) => { clearHighlight(document.getElementById("first-name")); setFirstName(e.target.value) }}
        id='first-name'
        className={"minimal-input-compact w-full hover-outline"}
        type="text"
        placeholder="First Name" />

      <input onChange={(e) => { clearHighlight(document.getElementById("last-name")); setLastName(e.target.value) }}
        id='last-name'
        className={"minimal-input-compact w-full hover-outline"}
        type="text"
        placeholder="Last Name" />

      <input onChange={(e) => { clearHighlight(document.getElementById("phone-number")); setPhoneNumber(e.target.value) }}
        id='phone-number'
        className={"minimal-input-compact w-full hover-outline"}
        type="tel"
        placeholder="Phone Number" />

      <div className="flex items-center justify-between w-full">
        <span className='text-sm pl-1'>A foreign citizen?</span>
        <label className="switch">
          <input
            type="checkbox"
            checked={isForeignCitizen}
            onChange={() => { setIsForeignCitizen(!isForeignCitizen) }}
          />
          <span className="slider round"></span>
        </label>
      </div>

      {
        isForeignCitizen ? (
          <input onChange={(e) => { clearHighlight(document.getElementById("passport-number")); setPassportNumber(e.target.value) }}
            id="passport-number"
            className={"minimal-input-compact w-full hover-outline"}
            type="text"
            placeholder="Passport Number" />
        ) : (
          <input onChange={(e) => { clearHighlight(document.getElementById("id-card-number")); setIdCardNumber(e.target.value) }}
            id="id-card-number"
            className={"minimal-input-compact w-full hover-outline"}
            type="text"
            placeholder="ID Card Number" />
        )
      }


      <button className={"minimal-button w-full h-auto font-semibold hover-highlight"}
        onClick={async () => await tryRegister()}>
        Register
      </button>

      {
        errorMessage && (
          <p className="text-red-500 card p-4">{errorMessage}</p>
        )
      }
    </div>
  )
}