import { useEffect, useState } from "react"
import api from "../../../utils/api";
import '../../../styles/components.css';
import TransportationDeviceGenericTable from "../../../components/tables/TransportationDeviceTableGeneric";
import { handleCSVUpload } from "../../../utils/csv";

export default function TransportationDevicesPanel() {
  const [manufacturers, setManufacturers] = useState(null);
  const [deviceType, setDeviceType] = useState('vehicle');
  const [formData, setFormData] = useState({
    uuid: '',
    acquisitionDate: '',
    purchasePrice: '',
    manufacturerID: '',
    model: '',
    description: '',
    status: 'Available',
  });
  const [uploadResult, setUploadResult] = useState(null);
  const [message, setMessage] = useState(null);

  const onFileChange = async (event) => {
    const file = event.target.files[0];
    try {
      const result = await handleCSVUpload(file);
      setUploadResult(result);
      console.log('Upload completed:', result);
    } catch (error) {
      console.error('Error during upload:', error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.instance.get('/manufacturers');
        return response;
      } catch (error) {
        console.error('Error fetching manufacturers:', error);
      }
    };

    fetchData().then(response => {
      if (response) {
        setManufacturers(response.data.content);
      }
    });
  }, [])

  const handleChange = (event) => {
    const { id, value } = event.target;

    setFormData(prevData => ({
      ...prevData,
      [id]: value
    }));
  };

  const handleDeviceTypeChange = (event) => {
    setDeviceType(event.target.value);
    // console.log(event.target.value);
  };

  const handleManufacturerChange = (event) => {
    // nema logike da mi idalje prikazuje samo ime u displeju l o l
    // mrzim js
    // console.log(event.target.value)
    formData.manufacturerID = event.target.value.split('#')[0]
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    // console.log(formData);

    const requiredFields = ['uuid', 'acquisitionDate', 'purchasePrice', 'manufacturerID', 'model', 'description'];
    if (deviceType === 'electricScooter') requiredFields.push('maxSpeed');
    if (deviceType === 'electricBicycle') requiredFields.push('autonomy');

    const missingFields = requiredFields.filter(field => !formData[field]);

    if (missingFields.length > 0) {
      alert(`Please fill in all required fields: ${missingFields.join(', ')}`);
      return;
    }

    let response;

    if (deviceType === 'vehicle') {
      response = await api.instance.post('/vehicles', formData);
    } else if (deviceType === 'electricScooter') {
      response = await api.instance.post('/electric-scooters', formData);
    } else if (deviceType === 'electricBicycle') {
      response = await api.instance.post('/electric-bicycles', formData);
    }

    // console.log(response);

    if (response.status === 200) {
      setFormData({
        uuid: '',
        deviceType: '',
        acquisitionDate: '',
        purchasePrice: '',
        manufacturerID: '',
        model: '',
        description: '',
        status: 'Available',
      });

      setMessage("Success!");
    }
  };

  return (
    manufacturers &&
    <div className="flex flex-col gap-4" >
      <TransportationDeviceGenericTable />

      <hr className="my-2" />

      {
        <div className="flex flex-col justify-start gap-4 items-start">
          <p className="px-2 font-bold">Create a new vehicle</p>

          <select id="vehicleType" className="minimal-input p-4 w-full hover-highlight" onChange={handleDeviceTypeChange} required>
            <option value={'vehicle'} className="text-black">Car</option>
            <option value={'electricScooter'} className="text-black">e-Scooter</option>
            <option value={'electricBicycle'} className="text-black card">e-Bike</option>
          </select>

          <input id="uuid" type="text" placeholder="Unique Identifier" className="minimal-input w-full hover-highlight" onChange={handleChange} required />

          <input id="acquisitionDate" type="date" placeholder="Acquisition Date" onChange={handleChange} className="minimal-input w-full hover-highlight" required />

          <input id="purchasePrice" type="number" placeholder="Price" onChange={handleChange} className="minimal-input w-full hover-highlight" required />

          <select id="manufacturerID" className="minimal-input p-4 w-full hover-highlight"
            onChange={handleManufacturerChange} required>
            <option value="" disabled selected>Select a manufacturer</option>
            {
              manufacturers.map((manufacturer) => {
                return (
                  <option
                    key={manufacturer.manufacturerID}
                    value={manufacturer.manufacturerID + "#" + manufacturer.name}
                    className="text-gray-700">{manufacturer.name}</option>
                )
              })
            }
          </select>

          <input id="model" type="text" placeholder="Model" onChange={handleChange} className="minimal-input w-full hover-highlight" required />

          <textarea id="description" type="text" placeholder="Description" onChange={handleChange} className="minimal-input w-full h-80 hover-highlight" />
          {
            deviceType === "electricScooter" &&
            <input id="maxSpeed" type="number" placeholder="Maximum Speed" onChange={handleChange} className="minimal-input w-full hover-highlight" required />
          }

          {
            deviceType === "electricBicycle" &&
            <input id="autonomy" type="number" placeholder="Battery Capacity" onChange={handleChange} className="minimal-input w-full hover-highlight"
              required />
          }

          <div className="flex flex-row justify-start items-start gap-6">
            <button className="minimal-button hover-highlight hover-outline" onClick={handleSubmit}>Create</button>
            {
              message &&
              <p className="italic">{message}</p>
            }
          </div>

        </div>
      }

      <div className="flex flex-col items-start">
        <input className="minimal-button" type="file" accept=".csv" onChange={onFileChange} />
        {
          uploadResult && (
            <div className="flex flex-col justify-start items-start gap-4 card mt-4">
              <h3 className="font-bold">CSV Upload Results</h3>
              <p>Total Processed: <span className="font-bold">{uploadResult.totalProcessed}</span></p>
              <p>Successful: <span className="font-bold text-green-300">{uploadResult.successful}</span></p>
              <p>Failed: <span className="font-bold text-red-300">{uploadResult.failed}</span></p>
            </div>
          )
        }
      </div>
    </div >
  )
}
