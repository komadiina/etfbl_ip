import { useState } from "react";
import api from "../utils/api";

export default function ManufacturerEntryForm() {
  const [manufacturer, setManufacturer] = useState({});
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleChange = (event) => {
    manufacturer[event.target.id] = event.target.value
    setManufacturer(manufacturer)
  }

  const handleSubmit = async (ev) => {
    const dto = {
      name: manufacturer.name,
      country: manufacturer.country,
      address: manufacturer.address,
      phone: manufacturer.phoneNumber,
      fax: manufacturer.fax,
      email: manufacturer.email
    }

    const response = await api.instance.post('/manufacturers', dto).catch(error => setError(error));
  }

  return (
    <div className="flex flex-col justify-start gap-4 items-start">
      <h1 className="px-2 font-bold">Add Manufacturer</h1>

      <input id='name' className="minimal-input w-full" type="text" placeholder="Name" onChange={handleChange} />
      <input id='country' className="minimal-input w-full" type="text" placeholder="Country" onChange={handleChange} />
      <input id='address' className="minimal-input w-full" type="address" placeholder="Address" onChange={handleChange} />
      <input id='phoneNumber' className="minimal-input w-full" type="tel" placeholder="Phone" onChange={handleChange} />
      <input id='fax' className="minimal-input w-full" type="text" placeholder="Fax" onChange={handleChange} />
      <input id='email' className="minimal-input w-full" type="fax" placeholder="Email" onChange={handleChange} />

      <div className="flex flex-row items-start justify-start">
        <button className="minimal-button" onClick={handleSubmit}>Submit</button>
        {
          error &&
          <p className="text-red-500">Ooops!</p>
        }
        {
          !error && success &&
          <p className="text-green-500">Success!</p>
        }
      </div>
    </div>
  )
}