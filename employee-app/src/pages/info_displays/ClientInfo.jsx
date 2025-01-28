import { useEffect, useState } from "react";
import { useParams } from "react-router"
import api from "../../utils/api";

export default function ClientInfoDisplay() {
  let id = useParams().id;
  const [client, setClient] = useState(null);
  const [error, setError] = useState(null);

  const fetchClient = async () => {
    try {
      const [responseData] = await Promise.all([
        api.instance.get(`/clients/${id}`),
      ])

      setClient(_ => ({
        username: responseData.data.username,
        firstName: responseData.data.firstName,
        lastName: responseData.data.lastName,
        email: responseData.data.email,
        phoneNumber: responseData.data.phoneNumber,
        idCardNumber: responseData.data.idCardNumber,
        passportID: responseData.data.passportID
      }))
    } catch (error) {
      setError(error);
      console.log(error)
    }
  }

  useEffect(() => {
    fetchClient();
  }, [])

  if (error) {
    return (
      <div className="flex flex-col gap-2 card">
        <h1 className="font-bold">Oops!</h1>
        <p className="italic text-red-300">{error.message}</p>
      </div>
    )
  }

  return (client &&
    <div className="card w-full flex flex-col gap-4 justify-start items-start">
      <h1 className="font-bold text-2xl">{client.firstName} {client.lastName}</h1>
      <hr className="border-2 border-white border-opacity-15 w-full" />

      <div className="flex flex-col gap-2">
        <div className="flex flex-row gap-2">
          <span className="font-bold">Username:</span>
          <span>{client.username}</span>
        </div>
        <div className="flex flex-row gap-2">
          <span className="font-bold">Email:</span>
          <span>{client.email}</span>
        </div>
        <div className="flex flex-row gap-2">
          <span className="font-bold">Phone number:</span>
          <span>{client.phoneNumber}</span>
        </div>
        <div className="flex flex-row gap-2">
          <span className="font-bold">ID Card number:</span>
          <span>{client.idCardNumber}</span>
        </div>
        <div className="flex flex-row gap-2">
          <span className="font-bold">Passport ID:</span>
          <span>{client.passportID}</span>
        </div>
      </div>

    </div>
  )
}