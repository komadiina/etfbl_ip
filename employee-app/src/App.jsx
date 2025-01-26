import './App.css'
import { BrowserRouter, Routes, Route } from "react-router";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";
import EmployeeDashboard from './pages/EmployeeDashboard.jsx';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={"/"} element={<EmployeeDashboard />} />
        <Route path={"/dashboard"} element={<EmployeeDashboard />} />
        <Route path={"/login"} element={<Login />} />
        <Route path={"/register"} element={<Register />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
