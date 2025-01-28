import { useState } from 'react';
import { useForm } from 'react-hook-form';
import api from '../utils/api';

const NewEmployeeForm = () => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm();
  const [submitError, setSubmitError] = useState(null);
  const [submitSuccess, setSubmitSuccess] = useState(false);

  const onSubmit = async (data) => {
    try {
      data = { ...data, userType: 'Employee' }
      const response = await api.instance.post('/employees', data);
      console.log('Employee created:', response.data);
      setSubmitSuccess(true);
      setSubmitError(null);
      reset();
    } catch (error) {
      console.error('Error creating employee:', error);
      setSubmitError('Failed to create employee. Please try again.');
      setSubmitSuccess(false);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 w-full">
      <div className='flex flex-col gap-2'>
        <label htmlFor="firstName" className="text-xl items-start justify-start font-bold text-left">First Name</label>
        <input
          type="text"
          id="firstName"
          {...register('firstName', { required: 'First name is required' })}
          className="minimal-input hover-highlight"
        />
        {errors.firstName && <p className="my-1 text-lg italic text-red-600">{errors.firstName.message}</p>}
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor="lastName" className="text-xl items-start justify-start font-bold text-left">Last Name</label>
        <input
          type="text"
          id="lastName"
          {...register('lastName', { required: 'Last name is required' })}
          className="minimal-input hover-highlight"
        />
        {errors.lastName && <p className="my-1 text-lg italic text-red-600">{errors.lastName.message}</p>}
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor="username" className="text-xl items-start justify-start font-bold text-left">Username</label>
        <input
          type="text"
          id="username"
          {...register('username', { required: 'Username is required' })}
          className="minimal-input hover-highlight"
        />
        {errors.username && <p className="my-1 text-lg italic text-red-600">{errors.username.message}</p>}
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor="email" className="text-xl items-start justify-start font-bold text-left">Email</label>
        <input
          type="email"
          id="email"
          {...register('email', {
            required: 'Email is required',
            pattern: {
              value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
              message: "Invalid email address"
            }
          })}
          className="minimal-input hover-highlight"
        />
        {errors.email && <p className="my-1 text-lg italic text-red-600">{errors.email.message}</p>}
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor="password" className="text-xl items-start justify-start font-bold text-left">Password</label>
        <input
          type="password"
          id="password"
          {...register('password', {
            required: 'Password is required',
            minLength: {
              value: 8,
              message: 'Password must be at least 8 characters long'
            }
          })}
          className="minimal-input hover-highlight"
        />
        {errors.password && <p className="my-1 text-lg italic text-red-600">{errors.password.message}</p>}
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor="phoneNumber" className="text-xl items-start justify-start font-bold text-left">Phone Number</label>
        <input
          type="tel"
          id="phoneNumber"
          {...register('phoneNumber', {
            required: 'Phone number is required',
            pattern: {
              value: /^[0-9]+$/,
              message: "Invalid phone number"
            }
          })}
          className="minimal-input hover-highlight"
        />
        {errors.phoneNumber && <p className="my-1 text-lg italic text-red-600">{errors.phoneNumber.message}</p>}
      </div>

      <div className='flex flex-col gap-2'>
        <label htmlFor="role" className="text-xl items-start justify-start font-bold text-left">Role</label>
        <select
          id="role"
          {...register('role', { required: 'Role is required' })}
          className="minimal-input p-4 w-full hover-highlig"
        >
          <option className="text-black" value="">Select a role</option>
          <option className="text-black" value="Administrator">Administrator</option>
          <option className="text-black" value="Manager">Manager</option>
          <option className="text-black" value="Operator">Operator</option>
        </select>
        {errors.role && <p className="my-1 text-lg italic text-red-600">{errors.role.message}</p>}
      </div>

      {submitError && <p className="text-sm text-red-600">{submitError}</p>}
      {submitSuccess && <p className="text-sm text-green-600">Employee created successfully!</p>}

      <button
        type="submit"
        className="minimal-input hover-highlight"
      >
        Create Employee
      </button>
    </form>
  );
};

export default NewEmployeeForm;