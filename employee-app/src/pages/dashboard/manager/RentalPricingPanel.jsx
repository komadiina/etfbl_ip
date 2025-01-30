import { useEffect, useState } from "react"
import api from "../../../utils/api";
import { useForm } from "react-hook-form";
import GenericTable from "../../../components/tables/GenericTable";

export default function RentalPricingPanel() {
  const [devices, setDevices] = useState([])
  const [manufacturers, setManufacturers] = useState([])
  const [devicePricings, setDevicePricings] = useState([]);
  const [selectedDeviceID, setSelectedDeviceID] = useState(null);

  const { register, handleSubmit, formState: { errors }, reset } = useForm();
  const [submitError, setSubmitError] = useState(null);
  const [submitSuccess, setSubmitSuccess] = useState(false);

  const [devicePricingsPage, setDevicePricingsPage] = useState(0);
  const [devicePricingsTotalPages, setDevicePricingsTotalPages] = useState(0);

  const rentalPricingColumns = [
    { 'key': 'deviceID', 'label': 'Device ID' },
    { 'key': 'pricePerMinute', 'label': 'PPM (US$)' },
    { 'key': 'isActive', 'label': 'Active' }
  ]

  const fetchDevices = async (page) => {
    try {
      const [devicesResponse, manufacturersResponse] = await Promise.all([
        api.instance.get(`/transportation-devices?page=0&limit=1000`),
        api.instance.get(`/manufacturers?page=0&limit=1000`)
      ]);

      setManufacturers(manufacturersResponse.data.content);
      setDevices(devicesResponse.data.content.map(d => ({
        ...d,
        manufacturerName: manufacturersResponse.data.content.find(m => m.manufacturerID === d.manufacturerID)?.name || 'N/A'
      })));

      setDevicePricingsPage(devicesResponse.data.pageable.pageNumber);
      setDevicePricingsTotalPages(devicesResponse.data.totalPages);
    } catch (error) {
      console.log(error);
    }
  }

  const fetchDevicePricings = async () => {
    try {
      const [devicePricingsResponse] = await Promise.all([
        api.instance.get(`/rental-prices/device/all/pageable/${selectedDeviceID}?page=${devicePricingsPage}&limit=10`)
      ]);

      if (devicePricingsResponse.data.content)
        setDevicePricings(devicePricingsResponse.data.content.map(d => ({
          ...d,
          isActive: d.active === true ? 'Active' : 'Inactive'
        })));

      // console.log(devicePricingsResponse.data.content)
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    fetchDevices();
  }, [])

  useEffect(() => {
    if (selectedDeviceID != null) {
      fetchDevicePricings();
    }
  }, [selectedDeviceID])

  const onSubmit = async (data) => {
    try {
      data = { ...data, deviceID: selectedDeviceID }
      console.log(data);
      const response = await api.instance.post('/rental-prices', data);

      if (response.status === 200) {
        setSubmitSuccess(true);
        setSubmitError(null);
        reset();
      } else {
        setSubmitSuccess(false);
        setSubmitError(`Failed to create rental price. Please try again. (${response.statusText})`);
      }
    } catch (error) {
      console.error('Error creating employee:', error);
      setSubmitError('Failed to create employee. Please try again.');
      setSubmitSuccess(false);
    }
  };

  return (
    devices && devicePricings && manufacturers &&
    <div className="w-full flex flex-col gap-4 items-start justify-start">
      {
        devicePricings.length > 0 &&
        <GenericTable
          columns={rentalPricingColumns}
          data={devicePricings}
          page={devicePricingsPage}
          totalPages={devicePricingsTotalPages}
          showPagination={false}
        />
      }

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 w-full">
        <p className="text-xl text-left px-2">Select a vehicle</p>
        <select {...register('deviceID')}
          className="w-full minimal-input hover-highlight"
          onChange={(e) => { setSelectedDeviceID(e.target.value) }}>
          {
            devices.map((device) => (
              <option
                key={device.deviceID}
                value={device.deviceID}
                className="text-black"
              >
                {device.model} - {device.manufacturerName}
              </option>
            ))
          }
        </select>

        <p className="text-xl text-left px-2">Price per minute</p>
        <input
          type="number"
          {...register('pricePerMinute', { required: true })}
          className="w-full minimal-input hover-highlight"
        />
        {errors.pricePerMinute && <span className="text-red-500">Price per minute is required</span>}

        {/* submit */}
        <button type="submit" className="minimal-button w-full">Submit</button>
      </form>
    </div>
  )
}
