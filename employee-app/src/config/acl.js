import TransportationDevicesPanel from "../pages/dashboard/TransportationDevicesPanel.jsx";
import UserPanel from "../pages/dashboard/UserPanel.jsx";
import ManufacturerCRUDPanel from "../pages/dashboard/ManufacturerCRUDPanel.jsx";
import RentalPanel from "../pages/dashboard/RentalPanel.jsx";
import TransportationDeviceMap from "../pages/dashboard/TransportationDeviceMap.jsx";
import BreakdownEntryPanel from "../pages/dashboard/BreakdownEntryPanel.jsx";
import StatisticsPanel from "../pages/dashboard/StatisticsPanel.jsx";
import RentalPricingPanel from "../pages/dashboard/RentalPricingPanel.jsx";

const adminSpecific = [
  {
    icon: "fa-solid fa-car",
    title: "Vehicles",
    component: TransportationDevicesPanel()
  },
  {
    icon: "fa-solid fa-user",
    title: "Users",
    component: UserPanel()
  },
  {
    icon: "fa-solid fa-industry",
    title: "Manufacturers",
    component: ManufacturerCRUDPanel()
  }
]

const operatorSpecific = [
  {
    icon: "fa-solid fa-key",
    title: "Rentals",
    component: RentalPanel()
  },
  {
    icon: "fa-solid fa-map",
    title: "Map",
    component: TransportationDeviceMap()
  },
  {
    icon: "fa-solid fa-fire-extinguisher",
    title: "Breakdowns",
    component: BreakdownEntryPanel()
  }
]

const managerSpecific = [
  {
    icon: "fa-solid fa-chart-simple",
    title: "Statistics",
    component: StatisticsPanel()
  },
  {
    icon: "fa-solid fa-dollar-sign",
    title: "Pricings",
    component: RentalPricingPanel()
  }
]

export const acl = {
  "Administrator": adminSpecific,
  "Operator": [
    ...adminSpecific,
    ...operatorSpecific
  ],
  "Manager": [
    ...adminSpecific,
    ...operatorSpecific,
    ...managerSpecific
  ]
}