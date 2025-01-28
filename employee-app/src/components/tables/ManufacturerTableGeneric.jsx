import { useState, useEffect } from 'react';
import api from '../../utils/api';
import GenericTable from './GenericTable';

const ManufacturerTableGeneric = () => {
  const [manufacturers, setManufacturers] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchManufacturers();
  }, [currentPage]);

  const fetchManufacturers = async () => {
    try {
      const response = await api.instance.get(`/manufacturers?page=${currentPage}&limit=10`);
      setManufacturers(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching manufacturers:', error);
    }
  };

  const handleSort = (column, direction) => {
    let sorted = manufacturers.sort((a, b) => {
      if (a[column] < b[column]) return direction === 'asc' ? -1 : 1;
      if (a[column] > b[column]) return direction === 'asc' ? 1 : -1;
      return 0;
    })
    setManufacturers(sorted);
  };

  const handleDelete = async (id) => {
    try {
      await api.instance.delete(`/manufacturers/${id}`);
      fetchManufacturers();
    } catch (error) {
      console.error('Error deleting manufacturer:', error);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'country', label: 'Country' },
    { key: 'address', label: 'Address' },
    { key: 'phone', label: 'Phone' },
    { key: 'fax', label: 'Fax' },
    { key: 'email', label: 'Email' },
  ];

  return (
    <GenericTable
      data={manufacturers}
      columns={columns}
      onSort={handleSort}
      onDelete={handleDelete}
      currentPage={currentPage}
      totalPages={totalPages}
      onNextPage={() => setCurrentPage(prev => prev + 1)}
      onPrevPage={() => setCurrentPage(prev => prev - 1)}
    />
  );
};

export default ManufacturerTableGeneric;