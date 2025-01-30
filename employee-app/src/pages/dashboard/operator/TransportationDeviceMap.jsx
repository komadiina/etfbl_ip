import { useState, useEffect } from "react";
import api from "../../../utils/api";

export default function TransportationDeviceMap() {
  const [gridData, setGridData] = useState(Array(20).fill().map(() => Array(20).fill(0)));
  const [maxDevices, setMaxDevices] = useState(0);
  const [initialized, setInitialized] = useState(false);
  const [rentals, setRentals] = useState(null);
  const [message, setMessage] = useState("Loading...");

  const [isCellFocused, setIsCellFocused] = useState(false);
  const [cellX, setCellX] = useState(null);
  const [cellY, setCellY] = useState(null);
  const [targetDevices, setTargetDevices] = useState(null);

  const handleCellClick = (x, y) => {
    setIsCellFocused(true);
    setCellX(x);
    setCellY(y);

    const devices = rentals.filter(rental => {
      return (
        rental.posX === x &&
        rental.posY === y
      );
    })

    // console.log(devices)
    setTargetDevices(devices);
  };

  useEffect(() => {
    fetchRentalData();
  }, []);

  const fetchRentalData = async () => {
    try {
      const [
        // rentalsResponse,
        transportationDevicesResponse,
        rentalPricesResponse,
        clientsResponse] = await Promise.all([
          // api.instance.get('/rentals'),
          api.instance.get('/transportation-devices/available/all'),
          api.instance.get('/rental-prices'),
          api.instance.get('/clients')
        ]);


      let filteredRentals = transportationDevicesResponse.data.filter(device => ({
        ...device,
        // clientName: clientsResponse.data.find(c => c.id === device.clientID)?.firstName + ' ' + clientsResponse.data.find(c => c.id === device.clientID)?.lastName,
        deviceName: transportationDevicesResponse.data.find(d => d.deviceID === device.deviceID)?.model || 'N/A',
        rentalPrice: rentalPricesResponse.data.find(rp => rp.deviceID === device.deviceID)?.pricePerMinute || 'N/A'
      }));

      filteredRentals = filteredRentals.map(fr => ({
        ...fr,
        deviceName: transportationDevicesResponse.data.find(d => d.deviceID === fr.deviceID)?.model || 'N/A',
        rentalPrice: rentalPricesResponse.data.find(rp => rp.deviceID === fr.deviceID)?.pricePerMinute || 'N/A'
      }))

      setRentals(filteredRentals)

      const newGridData = Array(20).fill().map(() => Array(20).fill(0));
      filteredRentals.forEach(rental => {
        const x = rental.posX
        const y = rental.posY;
        if (x >= 0 && x < 20 && y >= 0 && y < 20) {
          newGridData[y][x]++;
        }
      });

      const max = Math.max(...newGridData.flatMap(row => row));

      setGridData(newGridData);
      setMaxDevices(max);
      setInitialized(true)
    } catch (error) {
      console.error('Error fetching rental data:', error);
    }
  };

  if (!initialized) return <p>{message}</p>

  return (
    initialized &&
    <div className="w-full flex flex-col gap-4 items-start justify-start max-w-screen-2xl" >
      <h2 className="text-xl font-bold mb-4">Device Locations</h2>

      <div className="grid grid-flow-row grid-rows-20 gap-0.5 w-full card">
        {gridData.map((row, y) => (
          <div key={y} className="grid grid-flow-col grid-cols-20 gap-0.5 w-full">
            {row.map((count, x) => (
              <div
                key={x}
                className="w-12 h-12 hover-highlight flex items-center justify-center p-4 border-2 hover:scale-125 rounded-lg hover:cursor-pointer"
                onClick={() => handleCellClick(x, y)}
              >
                <span className="text-xl font-bold text-white">{count > 0 ? count : ''}</span>
              </div>
            ))}
          </div>
        ))}
      </div>

      <div className="flex flex-row flex-wrap justify-between max-w-screen-2xl gap-10">
        {isCellFocused && (
          targetDevices.map(td => {
            const statusColorText = td.status === "Available" ? "text-green-600" : td.status === "Broken" ? "text-red-600" : "text-yellow-600";
            return (

              <div className="flex flex-col gap-1 card" key={td.rentalID}>
                <p className="text-2xl font-bold px-2 text-left">Device information</p>
                <p className="px-2 text-left">Location: ({cellX}, {cellY})</p>
                <p className={"px-2 text-left " + statusColorText}>Status: {td.status}</p>
                <p className="px-2 text-left">Vehicle: {td.deviceName}</p>
                <p className="px-2 text-left">PPM rate: {td.rentalPrice}</p>
              </div>

            )
          })
        )}
      </div>
    </div>

  );
}
