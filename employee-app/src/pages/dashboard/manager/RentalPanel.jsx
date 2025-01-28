import { useEffect, useState } from "react";
import GenericTable from "../../../components/tables/GenericTable";
import api from "../../../utils/api";

export default function RentalPanel() {
  const [rentals, setRentals] = useState([]);
  const [clients, setClients] = useState([]);
  const [devices, setDevices] = useState([]);
  const [rentalPrices, setRentalPrices] = useState([]);

  const [rentalsPage, setRentalsPage] = useState(0);
  const [rentalsTotalPages, setRentalsTotalPages] = useState(0);

  const rentalColumns = [
    { 'key': 'rentalID', 'label': 'Rental ID' },
    { 'key': 'clientName', 'label': 'Client' },
    { 'key': 'deviceName', 'label': 'Device' },
    { 'key': 'rentalPrice', 'label': 'Price (US$)' },
    { 'key': 'startDateTime', 'label': 'Start' },
    { 'key': 'endDateTime', 'label': 'End' },
    // { 'key': 'active', 'label': 'Active' },
    { 'key': 'pickupX', 'label': 'Pickup X' },
    { 'key': 'pickupY', 'label': 'Pickup Y' },
    { 'key': 'dropoffX', 'label': 'Dropoff X' },
    { 'key': 'dropoffY', 'label': 'Dropoff Y' },
  ]

  const fetchClients = async () => {
    try {
      const [clientsResponse, transportationDevicesResponse, rentalPricesResponse, rentalsResponse] = await Promise.all([
        api.instance.get(`/clients`),
        api.instance.get('/transportation-devices'),
        api.instance.get('/rental-prices'),
        api.instance.get(`/rentals?page=${rentalsPage}&limit=10`),

      ]);

      // setClients(clientsResponse.data.content.map(c => ({ ...c, active: c.active ? 'Active' : 'Inactive' })));
      setRentals(rentalsResponse.data.content.map(r => ({
        ...r,
        startDateTime: new Date(r.startDateTime).toLocaleString(),
        endDateTime: r.endDateTime ? new Date(r.endDateTime).toLocaleString() : 'N/A',
        active: r.active == true ? 'Active' : 'Inactive',
        clientName: clientsResponse.data.find(c =>
          c.id === r.clientID)?.firstName + ' ' + clientsResponse.data.find(c => c.id === r.clientID)?.lastName,
        deviceName: transportationDevicesResponse.data.find(d => d.deviceID === r.deviceID)?.model || 'N/A',
        rentalPrice: rentalPricesResponse.data.find(rp => rp.id === r.rentalPriceID)?.pricePerMinute || 'N/A'
      })));

      console.log(transportationDevicesResponse);

      setRentalsPage(rentalsResponse.data.pageable.pageNumber);
      setRentalsTotalPages(rentalsResponse.data.totalPages);
    } catch (error) {
      console.error('Error fetching clients:', error);
    }
  }

  useEffect(() => {
    fetchClients();
  }, [rentalsPage]);

  return (
    rentals &&
    <div className="w-full flex flex-col gap-2">
      <GenericTable
        data={rentals}
        columns={rentalColumns}
        currentPage={rentalsPage}
        totalPages={rentalsTotalPages}
        onPrevPage={() => setRentalsPage(p => Math.max(p - 1, 0))}
        onNextPage={() => setRentalsPage(p => Math.min(p + 1, rentalsTotalPages))}
      />
    </div>
  )
}
