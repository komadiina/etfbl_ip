import '../styles/components.css'
import '../index.css'
import { useEffect, useState } from "react";
import api from "../utils/api.js";

function checkSlide() {
  const slideElements = document.querySelectorAll('.slide-in');

  slideElements.forEach(element => {
    // Half way through the element
    const slideInAt = (window.scrollY + window.innerHeight) - element.clientHeight / 2;
    const elementBottom = element.offsetTop + element.clientHeight;
    const isHalfShown = slideInAt > element.offsetTop;
    const isNotScrolledPast = window.scrollY < elementBottom;

    if (isHalfShown && isNotScrolledPast) {
      element.classList.add('active');
    }
  });
}

export const LoginForm = () => {
  const [username, setUsername] = useState(null);
  const [password, setPassword] = useState(null);
  const [message, setMessage] = useState(null);

  useEffect(() => {
    checkSlide();
    window.addEventListener('scroll', checkSlide);
    return () => window.removeEventListener('scroll', checkSlide);
  }, [])

  const tryLogin = async () => {
    if (!username || username.trim() === "") return { success: false, message: "Username cannot be empty." };
    if (!password || password.trim() === "") return { success: false, message: "Password cannot be empty." };

    const response = await api.login({ username, password });
    let success = response.status === 200;
    if (success) {
      localStorage.setItem("username", username);
      localStorage.setItem("role", response.data.user.role);
      localStorage.setItem("x-api-key", response.data.xapiKey.key)
      setMessage(null);
      window.location.href = '/dashboard'
    } else {
      setMessage(response.data.error);
    }
  }

  return (
    <div className={"card flex flex-col justify-start items-start gap-4 py-6"}>
      <h1 className={"text-2xl"}>Welcome!</h1>
      <p className='p-0'>Please sign in.</p>

      <input onChange={(e) => setUsername(e.target.value)} className={"minimal-input w-full hover-outline"}
        type="text" placeholder="Username" />

      <input onChange={(e) => setPassword(e.target.value)} id={"passwordInput"}
        className={"minimal-input w-full hover-outline"} type="password" placeholder="Password" />

      <button className={"minimal-button w-full h-auto font-semibold hover-highlight"}
        onClick={async () => await tryLogin()}>Sign in
      </button>

      {
        message && <p className='p-0'>{message}</p>
      }
    </div>
  )
}

export default LoginForm;
