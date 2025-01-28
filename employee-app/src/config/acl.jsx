import TransportationDevicesPanel from "../pages/dashboard/administrator/TransportationDevicesPanel.jsx";
import UserPanel from "../pages/dashboard/administrator/UserPanel.jsx";
import ManufacturerCRUDPanel from "../pages/dashboard/administrator/ManufacturerCRUDPanel.jsx";
import RentalPanel from "../pages/dashboard/manager/RentalPanel.jsx";
import TransportationDeviceMap from "../pages/dashboard/operator/TransportationDeviceMap.jsx";
import BreakdownEntryPanel from "../pages/dashboard/operator/BreakdownEntryPanel.jsx";
import StatisticsPanel from "../pages/dashboard/manager/StatisticsPanel.jsx";
import RentalPricingPanel from "../pages/dashboard/manager/RentalPricingPanel.jsx";
import ClientOverview from "../pages/dashboard/operator/ClientOverview.jsx";

const adminSpecific = [
  {
    icon: "fa-solid fa-car",
    title: "Vehicles",
    component: <TransportationDevicesPanel />
  },
  {
    icon: "fa-solid fa-user",
    title: "Users",
    component: <UserPanel />
  },
  {
    icon: "fa-solid fa-industry",
    title: "Manufacturers",
    component: <ManufacturerCRUDPanel />
  }
];

const operatorSpecific = [
  {
    icon: "fa-solid fa-key",
    title: "Rentals",
    component: <RentalPanel />
  },
  {
    icon: "fa-solid fa-map",
    title: "Map",
    component: <TransportationDeviceMap />
  },
  {
    icon: "fa-solid fa-fire-extinguisher",
    title: "Breakdowns",
    component: <BreakdownEntryPanel />
  },
  {
    icon: 'fa-solid fa-user-pen',
    title: 'Clients',
    component: <ClientOverview />
  }
];

const managerSpecific = [
  {
    icon: "fa-solid fa-chart-simple",
    title: "Statistics",
    component: <StatisticsPanel />
  },
  {
    icon: "fa-solid fa-dollar-sign",
    title: "Pricings",
    component: <RentalPricingPanel />
  }
];

export const acl = {
  "Administrator": adminSpecific,
  "Operator": operatorSpecific,
  "Manager": [...adminSpecific, ...operatorSpecific, ...managerSpecific]
};

export default acl;