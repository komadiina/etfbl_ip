import "../styles/animations.css";
import { LoginForm } from "../components/LoginForm.jsx";
import { useNavigate } from "react-router"

export default function Login(props) {
  let navigate = useNavigate()

  return (
    <div className="flex flex-col gap-4 slide-in max-w-screen-xl mx-auto">
      <h1 className="card p-4 shadow-md font-bold text-xl">Employee Portal</h1>
      <LoginForm />
    </div>

  )
}
