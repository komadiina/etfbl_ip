import { useNavigate, useParams } from "react-router"
import api from "../../utils/api";
import { useEffect, useState } from "react";
import GenericTable from "../../components/tables/GenericTable";

export default function TransportationDeviceInfo(props) {
  let navigate = useNavigate();
  const id = useParams().id;

  const [device, setDevice] = useState(null);
  const [manufacturer, setManufacturer] = useState(null);
  const [breakdowns, setBreakdowns] = useState([]);
  const [rentals, setRentals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [statusColor, setStatusColor] = useState('');

  const [currentPageRental, setCurrentPageRental] = useState(0);
  const [totalPagesRental, setTotalPagesRental] = useState(0);

  const [currentPageBreakdown, setCurrentPageBreakdown] = useState(0);
  const [totalPagesBreakdown, setTotalPagesBreakdown] = useState(0);

  const [description, setDescription] = useState('');
  const [message, setMessage] = useState(null);

  useEffect(() => {
    const fetchDeviceDetails = async () => {
      try {
        setLoading(true);
        const [deviceResponse, breakdownsResponse, rentalsResponse] = await Promise.all([
          api.instance.get(`/transportation-devices/${id}`),
          api.instance.get(`/breakdowns/all/${id}`),
          api.instance.get(`/rentals/all/${id}`),
        ]);

        setDevice(deviceResponse.data);
        setBreakdowns(breakdownsResponse.data);
        setRentals(rentalsResponse.data);

        const [manufacturerResponse] = await Promise.all([
          api.instance.get(`/manufacturers/${deviceResponse.data.manufacturerID}`),
        ])

        setManufacturer(manufacturerResponse.data);
        setDevice(dev => {
          return ({
            ...dev,
            manufacturerName: manufacturerResponse.data.name,
            manufacturerCountry: manufacturerResponse.data.country
          })
        })

        setBreakdowns(breakdownsResponse.data.map(b => ({ ...b, active: b.active ? 'Active' : 'Inactive', dateTimeRecorded: new Date(b.dateTimeRecorded).toLocaleString('en-US') })));

        if (deviceResponse.data.status === 'Broken') {
          setStatusColor('text-red-600')
        } else if (deviceResponse.data.status === 'Rented') {
          setStatusColor('text-yellow-600')
        } else setStatusColor('text-green-600')

        setLoading(false);
      } catch (err) {
        console.error('Error fetching device details:', err);
        setError('Failed to load device details. Please try again.');
        setLoading(false);
      }
    };

    fetchDeviceDetails();
  }, [id]);

  const handleSortRentals = (column, direction) => {
    const sortedRentals = [...rentals].sort((a, b) => {
      if (a[column] < b[column]) return direction === 'asc' ? -1 : 1;
      if (a[column] > b[column]) return direction === 'asc' ? 1 : -1;
      return 0;
    });
    setRentals(sortedRentals);
  };

  const handleDeleteRental = async (rentalId) => {
    if (window.confirm("Are you sure you want to delete this rental?")) {
      try {
        await api.instance.delete(`/rentals/${rentalId}`);
        const updatedRentals = rentals.filter(rental => rental.rentalID !== rentalId);
        setRentals(updatedRentals);
      } catch (error) {
        console.error('Error deleting rental:', error);
      }
    }
  };

  const handleInfoRental = (rental) => {
    navigate(`/rentals/${rental.rentalID}`);
  };

  const handleFilterRental = (event) => {
    const filterValue = event.target.value.toLowerCase();
    const filteredRentals = rentals.filter(rental =>
      rental.clientId.toString().toLowerCase().includes(filterValue) ||
      rental.startDateTime.toLowerCase().includes(filterValue)
    );
    setRentals(filteredRentals);
  };

  const handleSortBreakdowns = (column, direction) => {
    const sortedBreakdowns = [...breakdowns].sort((a, b) => {
      if (a[column] < b[column]) return direction === 'asc' ? -1 : 1;
      if (a[column] > b[column]) return direction === 'asc' ? 1 : -1;
      return 0;
    });
    setBreakdowns(sortedBreakdowns);
  };

  const handleDeleteBreakdown = async (item) => {
    if (window.confirm("Are you sure you want to delete this breakdown?")) {
      try {
        const breakdownId = item.breakdownID;
        await api.instance.delete(`/breakdowns/${breakdownId}`);
        const updatedBreakdowns = breakdowns.filter(breakdown => breakdown.breakdownID !== breakdownId);
        setBreakdowns(updatedBreakdowns);
      } catch (error) {
        console.error('Error deleting breakdown:', error);
      }
    }
  };

  const handleInfoBreakdown = (breakdown) => {
    navigate(`/breakdowns/${breakdown.breakdownID}`);
  };

  const handleFilterBreakdown = (event) => {
    const filterValue = event.target.value.toLowerCase();
    const filteredBreakdowns = breakdowns.filter(breakdown =>
      breakdown.description.toLowerCase().includes(filterValue) ||
      breakdown.dateTimeRecorded.toLowerCase().includes(filterValue)
    );
    setBreakdowns(filteredBreakdowns);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const handleCreateBreakdown = async () => {
    if (!description) {
      setMessage('Please enter a description for the breakdown.');
      return;
    }

    try {
      const response = await api.instance.post('/breakdowns', {
        description: description,
        dateTimeRecorded: new Date().toISOString(),
        deviceID: device.deviceID
      });
      if (response.status === 200) {
        const newBreakdown = response.data;
        setBreakdowns(prevBreakdowns => [...prevBreakdowns, newBreakdown]);
        setMessage(null);
        setDescription(''); // Clear the description field
      }
    } catch (error) {
      console.error('Error creating breakdown:', error);
    }
  };

  const handleDisableBreakdown = async (device) => {
    try {
      const response = await api.instance.put(`/breakdowns/disable/${device.deviceID}`);
      if (response.status === 200) {
        const updatedDevice = response.data;
        setDevice(updatedDevice);
      }
    } catch (error) {
      console.error('Error disabling breakdown:', error);
    }
  }


  if (loading) return <div>Loading...</div>;
  if (error) return <div>{error}</div>;
  if (!device) return <div>No device found</div>;

  return (
    <div className="flex flex-col gap-4">
      <div className="flex flex-col justify-start items-start card gap-2">
        <h2 className={"font-bold text-xl"}>{device.deviceType}</h2>
        <hr className="w-full" />
        <p className="font-bold mt-2 text-lg">Device ID</p>
        <p className=" w-full text-left text-xl">
          {device.deviceID}
        </p>

        <p className="font-bold mt-2 text-lg">Model</p>
        <p className=" w-full text-left text-xl">
          {device.model}
        </p>

        <p className="font-bold mt-2 text-lg">Manufacturer</p>
        <p className=" w-full text-left text-xl">
          {device.manufacturerName}
        </p>

        <p className="font-bold mt-2 text-lg">Country</p>
        <p className=" w-full text-left text-xl">
          {device.manufacturerCountry}
        </p>

        <p className="font-bold mt-2 text-lg">Description</p>
        <p className="w-full text-left text-xl">
          {device.description}
        </p>

        <p className="font-bold mt-2 text-lg">Status</p>
        <p className={"w-full text-left text-xl " + statusColor}>
          {device.status}
        </p>

        {
          device.deviceType === 'ElectricScooter' &&
          <>
            <p className="font-bold mt-2 text-lg">Max speed</p>
            <p className=" w-full text-left text-xl">
              {device.maxSpeed}
            </p>
          </>
        }

        {
          device.deviceType === 'ElectricBicycle' &&
          <>
            <p className="font-bold mt-2 text-lg">Autonomy</p>
            <p className=" w-full text-left text-xl">
              {device.autonomy}
            </p>
          </>
        }
      </div>

      {
        device.status === 'Available' &&
        <div className="flex flex-col justify-start items-start card gap-2">
          <h2 className={"font-bold text-xl"}>Add breakdown</h2>
          <input required className="minimal-input w-full" type="text" placeholder="Description" onChange={handleDescriptionChange} />
          <button className="minimal-button w-full" onClick={handleCreateBreakdown}>Submit</button>
          {
            message &&
            <p className="text-red-500">{message}</p>
          }
        </div>
      }

      {
        device.status === 'Broken' &&
        <button className="minimal-button w-full" onClick={() => handleDisableBreakdown(device)}>Remove breakdown</button>
      }

      <div className="flex flex-col justify-start items-start card gap-2">
        <h2 className={"font-bold text-xl"}>Breakdowns</h2>
        {
          breakdowns &&
          <GenericTable
            data={breakdowns}
            columns={[
              { key: 'breakdownID', label: 'Breakdown ID' },
              { key: 'dateTimeRecorded', label: 'Date' },
              { key: 'description', label: 'Description' },
              { key: 'active', label: 'Active' },
            ]}
            onSort={handleSortBreakdowns}
            onDelete={handleDeleteBreakdown}
            currentPage={currentPageBreakdown}
            totalPages={totalPagesBreakdown}
            onInfo={handleInfoBreakdown}
            onFilter={handleFilterBreakdown}
            onNextPage={() => setCurrentPageBreakdown(prev => Math.min(prev + 1, totalPagesBreakdown - 1))}
            onPrevPage={() => setCurrentPageBreakdown(prev => Math.max(prev - 1, 0))}
          />
        }

      </div>

      <div className="flex flex-col justify-start items-start card gap-2">
        <h2 className={"font-bold text-xl "}>Previous rentals</h2>
        {
          rentals &&
          <GenericTable
            data={rentals}
            columns={[
              { key: 'rentalID', label: 'Rental ID' },
              { key: 'startDateTime', label: 'Start Date' },
              { key: 'endDateTime', label: 'End Date' },
              { key: 'clientId', label: 'Client' },
              { key: 'pickupX', label: 'Pickup X' },
              { key: 'pickupY', label: 'Pickup Y' },
              { key: 'dropoffX', label: 'Dropoff X' },
              { key: 'dropoffY', label: 'Dropoff Y' },
              { key: 'duration', label: 'Duration' },
            ]}
            onSort={handleSortRentals}
            onDelete={handleDeleteRental}
            currentPage={currentPageRental}
            totalPages={totalPagesRental}
            onInfo={handleInfoRental}
            onFilter={handleFilterRental}
            onEntryClick={() => { navigate(`/transportation-devices/${rentals[0].id}`) }}
            nNextPage={() => setCurrentPageRental(prev => Math.min(prev + 1, totalPagesRental - 1))}
            onPrevPage={() => setCurrentPageRental(prev => Math.max(prev - 1, 0))}
          />
        }
      </div>
    </div>
  )
}