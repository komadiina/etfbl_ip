import React, { useState } from 'react';

const GenericTable = ({
  data,
  columns,
  onSort,
  onDelete,
  onInfo,
  onFilter,
  filterTarget,
  currentPage,
  totalPages,
  onNextPage,
  onPrevPage,
  onToggleActivity,
  clickableRow = true,
  showPagination = true
}) => {
  const [sortColumn, setSortColumn] = useState('');
  const [sortDirection, setSortDirection] = useState('asc');
  const handleSort = (column) => {
    const newDirection = column === sortColumn && sortDirection === 'asc' ? 'desc' : 'asc';
    setSortColumn(column);
    setSortDirection(newDirection);
    onSort(column, newDirection);
  };

  return (
    <div className="max-w-screen-2xl mx-auto w-full px-4 sm:px-6 lg:px-8">
      {onFilter && (
        <div className="mb-4">
          <input
            type="text"
            className='minimal-input hover-highlight w-full'
            placeholder={`${filterTarget ? `Filter by ${filterTarget}` : 'Filter'}`}
            onInput={onFilter}
          />
        </div>
      )}
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="table-header">
          <tr>
            {columns.map((column) => (
              <th
                key={column.key}
                onClick={() => handleSort(column.key)}
                className="table-header"
              >
                {column.label}
                {sortColumn === column.key && (
                  <span>{sortDirection === 'asc' ? ' ▲' : ' ▼'}</span>
                )}
              </th>
            ))}
            {onDelete && <th className="px-6 py-3">Delete</th>}
            {onInfo && <th className="px-6 py-3">Info</th>}
            {onToggleActivity && <th className="px-6 py-3">Activity</th>}
          </tr>
        </thead>

        <tbody>
          {data.map((item, index) => (
            <tr key={index}
              className='table-entry hover-highlight'
              onClick={() => clickableRow && onInfo(item)}
            >
              {columns.map((column) => (
                <td key={column.key} className="table-entry">
                  {item[column.key]}
                </td>
              ))}
              {onDelete && (
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button
                    onClick={() => onDelete(item)}  // Pass the entire item
                    className="text-red-600 hover:text-red-900 p-2"
                  >
                    <i className="fa-solid fa-trash"></i>
                  </button>
                </td>
              )}
              {onInfo && (
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button
                    onClick={() => onInfo(item)}  // Pass the entire item
                    className="text-blue-600 hover:text-blue-900 p-2"
                  >
                    <i className="fa-solid fa-info"></i>
                  </button>
                </td>
              )}

              {onToggleActivity && (
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button
                    onClick={() => onToggleActivity(item)}  // Pass the entire item
                    className="hover:text-blue-900 p-2"
                  >
                    <i className={`text-xl fa-solid ${item.active === "Inactive" ? "fa-toggle-off text-red-600" : "fa-toggle-on text-blue-600"}`}></i>
                  </button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>

      {
        showPagination &&
        <div className="mt-4 flex justify-center align-middle">
          <button
            onClick={onPrevPage}
            disabled={currentPage === 0}
            className="minimal-button"
          >
            <i className="fa-solid fa-chevron-left py-0"></i>
          </button>

          <span className='align-middle items-center mt-4 px-4'>Page {currentPage + 1} of {totalPages == 0 ? 1 : totalPages}</span>

          <button
            onClick={onNextPage}
            disabled={currentPage === totalPages - 1}
            className="minimal-button"
          >
            <i className='fa-solid fa-chevron-right py-0'></i>
          </button>
        </div>
      }
    </div>
  );
};

export default GenericTable;