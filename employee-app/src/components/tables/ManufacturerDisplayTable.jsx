import '../../styles/components.css';
import { useEffect, useState, useCallback } from 'react';
import api from '../../utils/api';
import { useNavigate } from 'react-router';

const filter = (data, nameFilter) =>
  data.filter(manufacturer =>
    manufacturer
      .name.toLowerCase()
      .includes(nameFilter.toLowerCase())
  );

export const ManufacturerDisplayTable = () => {
  let navigate = useNavigate();

  const [sortKey, setSortKey] = useState('name');
  const [sortDirection, setSortDirection] = useState('asc');

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [manufacturers, setManufacturers] = useState([]);
  const [sortedManufacturers, setSortedManufacturers] = useState(null);
  const [nameFilter, setNameFilter] = useState('')

  const loadData = async (pageNum = 0) => {
    const url = `/manufacturers?page=${pageNum}&limit=10`;
    const response = await api.instance.get(url);
    const { content, totalPages } = response.data;

    setManufacturers(content.map(manufacturer => ({
      ...manufacturer,
      address: manufacturer.address || 'N/A',
      phone: manufacturer.phone || 'N/A',
      fax: manufacturer.fax || 'N/A',
      email: manufacturer.email || 'N/A'
    })));

    setTotalPages(totalPages)
    setSortedManufacturers(sortData(filter(content, nameFilter), sortKey, sortDirection));
  }


  useEffect(() => {
    const fetchData = async () => {
      try {
        loadData(page);
      } catch (error) {
        console.error('Error fetching manufacturers:', error);
      }
    };

    fetchData();
  }, [page]);

  const handleSort = (key) => {
    setSortDirection(sortKey === key && sortDirection === 'asc' ? 'desc' : 'asc');
    setSortKey(key);
    setSortedManufacturers(sortData(manufacturers, key, sortDirection));
  };

  const sortData = (data, key, direction = 'asc') => {
    return [...data].sort((a, b) => {
      if (a[key] < b[key]) return direction === 'asc' ? -1 : 1;
      if (a[key] > b[key]) return direction === 'asc' ? 1 : -1;
      return 0;
    });
  };

  const handlePrevious = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  }

  const handleNext = () => {
    if (page < totalPages - 1) {
      setPage(page => page + 1)
    }
  }

  const handleDelete = async (id) => {
    try {
      await api.instance.delete(`/manufacturers/${id}`);
      loadData();
    } catch (error) {
      console.error('Error deleting manufacturer:', error);
    }
  };

  return (
    <div className="flex flex-col justify-start w-full">
      <input id='nameFilter'
        className="minimal-input w-full mb-4 hover-highlight" type="text" placeholder="Search by name"
        onChange={(e) => {
          setNameFilter(_ => e.target.value)
          setSortedManufacturers(sortData(filter(manufacturers, e.target.value), sortKey, sortDirection));
        }} />

      <table>
        <thead>
          <tr>
            <th className="table-header" onClick={() => handleSort('name')}>Name</th>
            <th className="table-header" onClick={() => handleSort('country')}>Country</th>
            <th className="table-header" onClick={() => handleSort('address')}>Address</th>
            <th className="table-header" onClick={() => handleSort('phone')}>Phone</th>
            <th className="table-header" onClick={() => handleSort('fax')}>Fax</th>
            <th className="table-header" onClick={() => handleSort('email')}>Email</th>
            <th scope="col" className='table-header'>Delete</th>
            <th scope="col" className='table-header'>Info</th>
          </tr>
        </thead>

        <tbody>
          {
            sortedManufacturers &&
            sortedManufacturers.map((manufacturer, index) => (
              <tr key={index} className='table-row'>
                <td className='table-entry'>{manufacturer.name}</td>
                <td className='table-entry'>{manufacturer.country}</td>
                <td className='table-entry'>{manufacturer.address}</td>
                <td className='table-entry'>{manufacturer.phone}</td>
                <td className='table-entry'>{manufacturer.fax}</td>
                <td className='table-entry'>{manufacturer.email}</td>
                <td className="table-entry">
                  <button
                    onClick={() => handleDelete(manufacturer.manufacturerID)}
                    className="text-red-300 hover:text-red-600 px-4 py-2"
                  >
                    <i className="fa-solid fa-trash" />
                  </button>
                </td>
                <td className="table-entry">
                  <button
                    onClick={() => { navigate(`/manufacturers/${manufacturer.manufacturerID}`) }}
                    className="text-blue-300 hover:text-blue-600 px-4 py-2"
                  >
                    <i className="fa-solid fa-info" />
                  </button>
                </td>
              </tr>
            ))
          }
        </tbody>
      </table>

      <div className='my-2'>
        <button
          className="mx-4 p-2 rounded-md minimal-button hover-highlight shadow-xl"
          onClick={() => { handlePrevious() }}
        >
          <i className="fa-solid fa-chevron-left px-1" />
        </button>
        <span className="self-center">Page {page + 1} of {totalPages}</span>
        <button
          className="mx-4 p-2 rounded-md minimal-button hover-highlight"
          onClick={() => { handleNext() }}
        >
          <i className="fa-solid fa-chevron-right px-1" />
        </button>
      </div>
    </div>
  );
}