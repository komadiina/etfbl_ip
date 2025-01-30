import { useEffect, useState } from "react";
import api from "../../utils/api";
import { useNavigate } from "react-router";
import GenericTable from "./GenericTable";

export default function ClientTableGeneric() {
  const [clients, setClients] = useState([]);
  const [clientPage, setClientPage] = useState(0);
  const [clientPageTotal, setClientPageTotal] = useState(0);

  let navigate = useNavigate();

  const commonColumns = [
    { 'key': 'firstName', 'label': 'First Name' },
    { 'key': 'lastName', 'label': 'Last Name' },
    { 'key': 'username', 'label': 'Username' },
    { 'key': 'email', 'label': 'Email' },
    { 'key': 'active', 'label': 'Active' },
    { 'key': 'phoneNumber', 'label': 'Phone' },
    { 'key': 'userType', 'label': 'Type' },
  ]

  const clientColumns = [
    ...commonColumns,
    { 'key': 'idCardNumber', 'label': 'ID Card' },
    { 'key': 'passportID', 'label': 'Passport' },
    { 'key': 'balance', 'label': 'Balance' }
  ];


  const fetchClients = async () => {
    try {
      const [clientsResponse] = await Promise.all([api.instance.get(`/clients?page=${clientPage}&limit=10`)]);
      setClients(clientsResponse.data.content.map(c => ({ ...c, active: c.active ? 'Active' : 'Inactive' })));

      setClientPageTotal(clientsResponse.data.totalPages);
      setClientPage(clientsResponse.data.pageable.pageNumber);
    } catch (error) {
      console.error('Error fetching clients:', error);
    }
  }

  useEffect(() => {
    fetchClients();
  }, [clientPage])

  const onSortClient = (key) => {
    const sortedClients = [...clients];
    sortedClients.sort((a, b) => a[key] > b[key] ? 1 : -1);
    setClients(sortedClients);
  };

  const onToggleClientActivity = async (item) => {
    if (window.confirm("Are you sure?")) {
      try {

        await api.instance.post(`/clients/toggle-active/${item.id}`);
        await fetchClients();
      } catch (error) {
        console.error('Error updating client:', error);
      }
    }
  };

  const onInfoClient = (item) => {
    // console.log(item);
    navigate(`/clients/${item.id}`);
  };

  const handleFilter = (ev) => {
    const filteredClients = clients.filter(client => client.username.toLowerCase().includes(ev.target.value.toLowerCase()));
    setClients(filteredClients);
  }

  return (
    clients &&
    <div className="flex flex-col gap-4 card items-start justify-start">
      <h1 className="px-2 font-bold text-xl">Clients</h1>
      <GenericTable
        columns={clientColumns}
        data={clients}
        onInfo={onInfoClient}
        onToggleActivity={onToggleClientActivity}
        onSort={onSortClient}
        onFilter={handleFilter}
        filterTarget={'username'}
        currentPage={clientPage}
        totalPages={clientPageTotal}
        onNextPage={() => setClientPage(prev => Math.min(prev + 1, clientPageTotal - 1))}
        onPrevPage={() => setClientPage(prev => Math.max(prev - 1, 0))}
        clickableRow={false}
      />
    </div>
  )
}