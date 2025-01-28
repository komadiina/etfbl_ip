import { useState } from "react";
import acl from "../config/acl.jsx";

export const EmployeeDashboard = (props) => {
  const role = localStorage.getItem("role")
  const [currentComponent, setCurrentComponent] = useState(acl[role][0]);

  return (
    <div className={"flex flex-col items-center gap-8"}>

      {/* nekakav navbar */}
      <div className={"flex flex-row gap-4"}>
        {
          acl[role].map((component, index) => (
            <button
              key={index}
              className={"card mx-2 align-middle shadow-md flex flex-row items-center gap-2"}
              onClick={() => { setCurrentComponent(component) }}
            >
              <i className={component.icon + " relative top-0.5"}></i>
              {component.title}
            </button>
          ))
        }
      </div>

      <div className="dashboard-component-page items-startP">
        <div className="font-bold place-self-start text-2xl">
          {currentComponent.title}
        </div>

        <hr className="my-4" />

        {currentComponent.component}
      </div>
    </div>
  )
}

export default EmployeeDashboard;
