import { useState, useEffect } from "react";
import api from "../../utils/api";
import GenericTable from "./GenericTable";
import { useNavigate } from "react-router";

export default function TransportationDeviceGenericTable() {
  const [transportationDevices, setTransportationDevices] = useState([]);
  const [manufacturers, setManufacturers] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  let navigate = useNavigate();

  useEffect(() => {
    loadData();
  }, [currentPage]);

  const loadData = async () => {
    try {
      const [devicesResponse, manufacturersResponse] = await Promise.all([
        api.instance.get(`/transportation-devices?page=${currentPage}&limit=10`),
        api.instance.get('/manufacturers')
      ]);

      const devices = devicesResponse.data.content;
      const manufacturers = manufacturersResponse.data.content;

      setTransportationDevices(devices.map(td => ({
        ...td,
        acquisitionDate: td.acquisitionDate.split('T')[0],
        manufacturerName: manufacturers.find(m => m.manufacturerID === td.manufacturerID)?.name || 'N/A'
      })));
      setManufacturers(manufacturers);
      setTotalPages(devicesResponse.data.totalPages);
    } catch (error) {
      console.error('Error loading data:', error);
    }
  };

  useEffect(() => {
    loadData()
  }, []);

  const handleSort = (column, direction) => {
    let sorted = transportationDevices.sort((a, b) => {
      if (a[column] < b[column]) return direction === 'asc' ? -1 : 1;
      if (a[column] > b[column]) return direction === 'asc' ? 1 : -1;
      return 0;
    })
    setTransportationDevices(sorted);
  };

  const handleDelete = async (device) => {
    if (!device || !device.deviceID) {
      console.error('Invalid device or deviceID');
      return;
    }

    const confirmDelete = window.confirm(`Are you sure you want to delete this ${device.deviceType}?`);
    if (!confirmDelete) return;

    try {
      let endpoint;
      switch (device.deviceType.toLowerCase()) {
        case 'vehicle':
          endpoint = '/vehicles';
          break;
        case 'electricscooter':
          endpoint = '/electric-scooters';
          break;
        case 'electricbicycle':
          endpoint = '/electric-bicycles';
          break;
        default:
          throw new Error(`Unknown device type: ${device.deviceType}`);
      }

      await api.instance.delete(`${endpoint}/${device.deviceID}`);
      loadData();
    } catch (error) {
      console.error('Error deleting device:', error);
    }
  };

  const columns = [
    { key: 'id', label: 'ID' },
    { key: 'uuid', label: 'UUID' },
    { key: 'model', label: 'Model' },
    { key: 'deviceType', label: 'Type' },
    { key: 'acquisitionDate', label: 'Date' },
    { key: 'purchasePrice', label: 'Price' },
    { key: 'manufacturerName', label: 'Manufacturer' },
    { key: 'description', label: 'Description' }
  ];

  const handleFilter = (event) => {
    const filterValue = event.target.value;
    const filteredVehicles = transportationDevices.filter(vehicle => vehicle.name.toLowerCase().includes(filterValue.toLowerCase()));
    setTransportationDevices(filteredVehicles);
  };

  const handleInfo = (device) => {
    console.log(device)
    if (!device || !device.deviceID) {
      console.error('Invalid device or deviceID');
      return;
    }

    let route;
    navigate(`/transportation-devices/${device.deviceID}`);
  };

  return (
    manufacturers && transportationDevices &&
    <GenericTable
      data={transportationDevices}
      columns={columns}
      onSort={handleSort}
      onDelete={handleDelete}
      currentPage={currentPage}
      totalPages={totalPages}
      onInfo={handleInfo}
      onFilter={handleFilter}
      onEntryClick={() => { navigate(`/transportation-devices/${transportationDevices[0].id}`) }}
      onNextPage={() => setCurrentPage(prev => prev + 1)}
      onPrevPage={() => setCurrentPage(prev => prev - 1)}
    />
  );
};