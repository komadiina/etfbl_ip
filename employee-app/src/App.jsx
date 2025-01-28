import './App.css'
import { BrowserRouter, Routes, Route } from "react-router";
import Login from "./pages/Login.jsx";
import EmployeeDashboard from './pages/EmployeeDashboard.jsx';
import ManufacturerInfo from './pages/info_displays/ManufacturerInfo.jsx';
import TransportationDeviceInfo from './pages/info_displays/TransportationDeviceInfo.jsx';
import ClientInfoDisplay from './pages/info_displays/ClientInfo.jsx';
import EmployeeInfoDisplay from './pages/info_displays/EmployeeInfo.jsx';

function App() {
  return (
    <BrowserRouter>

      <Routes>
        <Route path={"/"} element={<EmployeeDashboard />} />
        <Route path={"/dashboard"} element={<EmployeeDashboard />} />
        <Route path={"/login"} element={<Login />} />

        <Route path={"/manufacturers/:id"} element={<ManufacturerInfo />} />
        <Route path={"/transportation-devices/:id"} element={<TransportationDeviceInfo />} />

        <Route path={"/clients/:id"} element={<ClientInfoDisplay />} />
        <Route path={"/employees/:id"} element={<EmployeeInfoDisplay />} />

        <Route path={"*"} element={<h1>404</h1>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
