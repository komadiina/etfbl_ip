import { useEffect, useState } from "react";
import api from "../../../utils/api";
import { useNavigate } from "react-router";
import GenericTable from "../../../components/tables/GenericTable";
import NewEmployeeForm from "../../../components/NewEmployeeForm";
import ClientTableGeneric from "../../../components/tables/ClientTableGeneric";

export default function UserPanel() {
  const [employeePage, setEmployeePage] = useState(0);
  const [employeePageTotal, setEmployeePageTotal] = useState(0);

  const [employees, setEmployees] = useState([]);

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

  const employeeColumns = [
    ...commonColumns,
    { 'key': 'role', 'label': 'Role' }
  ];

  const fetchEmployees = async () => {
    try {
      const [employeesResponse] = await Promise.all([api.instance.get(`/employees?page=${employeePage}&limit=10`)]);
      setEmployees(employeesResponse.data.content.map(e => ({ ...e, active: e.active ? 'Active' : 'Inactive' })));

      setEmployeePageTotal(employeesResponse.data.totalPages);
      setEmployeePage(employeesResponse.data.pageable.pageNumber);
    } catch (error) {
      console.error('Error fetching employees:', error);
    }
  }

  useEffect(() => {
    fetchEmployees();
  }, [employeePage]);

  const onInfoEmployee = (item) => {
    navigate(`/employees/${item.id}`);
  };


  const onDeleteEmployee = async (item) => {
    if (window.confirm("Are you sure you want to delete this employee?")) {
      try {
        await api.instance.delete(`/employees/${item.id}`);
        const updatedEmployees = employees.filter(employee => employee.id !== item.id);
        setEmployees(updatedEmployees);
      } catch (error) {
        console.error('Error deleting employee:', error);
      }
    }
  };

  const onSortEmployee = (key) => {
    const sortedEmployees = [...employees];
    sortedEmployees.sort((a, b) => a[key] > b[key] ? 1 : -1);
    setEmployees(sortedEmployees);
  };

  return (
    employees &&
    <div className="w-full flex flex-col gap-4">
      <ClientTableGeneric />

      <div className="flex flex-col gap-4 card items-start justify-start">
        <h1 className="px-2 font-bold text-xl">Employees</h1>
        <GenericTable
          columns={employeeColumns}
          data={employees}
          onInfo={onInfoEmployee}
          onDelete={onDeleteEmployee}
          onSort={onSortEmployee}
          currentPage={employeePage}
          totalPages={employeePageTotal}
          onNextPage={() => setEmployeePage(prev => Math.min(prev + 1, employeePageTotal - 1))}
          onPrevPage={() => setEmployeePage(prev => Math.max(prev - 1, 0))}
        />
      </div>

      <div className="flex flex-col gap-4 card items-start justify-start">
        <h1 className="px-2 font-bold text-xl">New employee</h1>
        <NewEmployeeForm />
      </div>
    </div>
  )
}
