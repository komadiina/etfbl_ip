import { useEffect, useState } from "react"
import api from "../../../utils/api";
import { useForm } from "react-hook-form";

export default function BreakdownEntryPanel() {
  const [devices, setDevices] = useState([])
  const [manufacturers, setManufacturers] = useState([]);
  const { register, handleSubmit, formState: { errors }, reset } = useForm();
  const [submitError, setSubmitError] = useState(null);
  const [submitSuccess, setSubmitSuccess] = useState(false);

  const fetchDevices = async () => {
    try {
      const [devicesResponse, manufacturersResponse] = await Promise.all([
        api.instance.get('/transportation-devices?page=0&limit=1000'),
        api.instance.get('/manufacturers?page=0&limit=1000'),
      ]);

      setManufacturers(manufacturersResponse.data.content);
      setDevices(devicesResponse.data.content);

      // update devices with manufacturer names
      const updatedDevices = devicesResponse.data.content.map((device) => {
        const manufacturer = manufacturersResponse.data.content.find((m) => m.id === device.manufacturerId);
        return { ...device, manufacturerName: manufacturer ? manufacturer.name : 'N/A' };
      })
      setDevices(updatedDevices);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    fetchDevices();
  }, []);


  const onSubmit = async (data) => {
    try {
      // console.log(data);

      const response = await api.instance.post('/breakdowns', data);
      // console.log('Breakdown created:', response.data);

      if (response.status == 200) {
        setSubmitSuccess(true);
        setSubmitError(null);
        reset();
      } else {
        setSubmitError(`Failed to create breakdown. Please try again. (${response.statusText})`);
        setSubmitSuccess(false);
      }
    } catch (error) {
      console.error('Error creating breakdown:', error);
      setSubmitError('Failed to create breakdown. Please try again.');
      setSubmitSuccess(false);
    }
  }

  return (
    devices &&
    <div className="w-full">
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 w-full">
        <div className="flex flex-col items-start justify-start gap-2">
          <label htmlFor="deviceID" className="text-left text-xl px-2">Select a device</label>
          <select
            id="deviceID"
            {...register('deviceID', { required: 'device is required' })}
            className="minimal-input hover-highlight w-full"
          >
            <option className="text-gray-300" value="" disabled>Select a device</option>
            {devices.map((device) => (
              <option className="text-black" key={device.deviceID} value={device.deviceID}>
                {device.model} - {device.uuid} ({device.manufacturerName})
              </option>
            ))}
          </select>
          {errors.deviceID && <p className="mt-1 text-sm text-red-600">{errors.deviceID.message}</p>}
        </div>

        <div className="flex flex-col items-start justify-start gap-2">
          <label htmlFor="description" className="text-left text-xl px-2">Description</label>
          <textarea
            id="description"
            {...register('description', {
              required: 'Description is required',
              minLength: {
                value: 10,
                message: 'Description must be at least 10 characters long'
              }
            })}
            rows="4"
            className="minimal-input hover-highlight w-full"
          ></textarea>
          {errors.description && <p className="mt-1 text-sm text-red-600">{errors.description.message}</p>}
        </div>

        <div className="flex flex-col items-start justify-start gap-2">
          <label htmlFor="dateTimeRecorded" className="text-left text-xl px-2">Date and Time</label>
          <input
            type="datetime-local"
            id="dateTimeRecorded"
            {...register('dateTimeRecorded', { required: 'Date and time is required' })}
            className="minimal-input hover-highlight w-full"
          />
          {errors.dateTimeRecorded && <p className="mt-1 text-sm text-red-600">{errors.dateTimeRecorded.message}</p>}
        </div>

        {submitError && <p className="text-sm text-red-600">{submitError}</p>}
        {submitSuccess && <p className="text-sm text-green-600">Breakdown entry created successfully!</p>}

        <button
          type="submit"
          className="minimal-button w-full hover-highlight"
        >
          Submit
        </button>
      </form>
    </div>
  )
}
