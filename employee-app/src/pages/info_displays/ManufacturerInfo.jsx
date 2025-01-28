import { useParams } from "react-router";
import { useEffect, useState } from "react";
import api from "../../utils/api";
import '../../styles/components.css';

const fetchData = async (id) => {
  const response = await api.instance.get(`/manufacturers/${id}`);
  return response.data;
}

export default function ManufacturerInfo() {
  const [data, setData] = useState(null);
  const [changes, setChanges] = useState(false);
  const [message, setMessage] = useState(null);
  const id = useParams().id;

  useEffect(() => {
    fetchData(id).then(data => setData(data));
  }, [id]);

  const handleChange = (event) => {
    const { id, value } = event.target;
    setChanges(true);

    setData(prevData => ({
      ...prevData,
      [id]: value
    }))
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response = await api.instance.put(`/manufacturers/${id}`, data);
    setChanges(false);
    if (response.status != 200) setMessage(response.statusText)
    else setMessage(null);
  }

  return (
    <div className="w-full">
      <div className="minimal-button w-full my-4 p-2 hover-highlight hover:cursor-pointer flex flex-row items-start justify-start" onClick={() => window.history.back()}>
        <i className="fa-solid fa-arrow-left px-2 text-xl" />
        <span className="text-xl ml-2">
          Back
        </span>
      </div>

      {
        data ? (
          <div className="flex flex-col gap-2 card items-start w-full">
            <p className="font-bold pl-1">Manufacturer</p>
            <input id="name" className="info-pane-detail minimal-input h-18 w-full" onChange={handleChange} value={data.name} />

            <p className="font-bold pl-1">Country</p>
            <input id="country" className="info-pane-detail minimal-input h-18 w-full" value={data.country} onChange={handleChange} />

            <p className="font-bold pl-1">Address</p>
            <input id="address" className="info-pane-detail minimal-input h-18 w-full" value={data.address} onChange={handleChange} />

            <p className="font-bold pl-1">Phone</p>
            <input id="phone" className="info-pane-detail minimal-input h-18 w-full" value={data.phone} onChange={handleChange} />

            <p className="font-bold pl-1">Fax</p>
            <input id="fax" className="info-pane-detail minimal-input h-18 w-full" value={data.fax} onChange={handleChange} />

            <p className="font-bold pl-1">Email</p>
            <input id="email" className="info-pane-detail minimal-input h-18 w-full" value={data.email} onChange={handleChange} />

            {
              changes ? (
                <button className="minimal-button" onClick={handleSubmit}>Save</button>
              ) : null
            }
          </div>
        ) : (
          <div>Loading...</div>
        )
      }
      {
        message && (
          <div>{message}</div>
        )
      }
    </div>
  )
}