import { useEffect, useState } from "react";
import api from "../../../utils/api";

import { ResponsiveContainer, Bar, BarChart, AreaChart, CartesianGrid, XAxis, YAxis, Tooltip, Area, LabelList } from "recharts"

export default function StatisticsPanel() {
  const [breakdowns, setBreakdowns] = useState(null);
  const [monthlyIncomes, setMonthlyIncomes] = useState(null);
  const [vehicleTypeIncomes, setVehicleTypeIncomes] = useState(null);

  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [year, setYear] = useState(new Date().getFullYear());

  const fetchBreakdowns = async () => {
    try {
      const [response] = await Promise.all([
        api.instance.get(`/statistics/breakdowns`)
      ])

      console.log('Breakdowns', response.data);
      setBreakdowns(response.data.breakdowns);

      console.log(Object.entries(response.data.breakdowns).map(([name, value]) => ({ name, value })))
    } catch (error) {
      console.error(error);
    }
  };

  const fetchIncomesByVehicleType = async () => {
    try {
      const [response] = await Promise.all([
        api.instance.get(`/statistics/incomes/overall`)
      ])

      console.log('Incomes by vehicle type', response.data);
      setVehicleTypeIncomes(response.data);
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    fetchIncomesByVehicleType();
    fetchBreakdowns();
  }, []);


  const fetchMonthlyIncomes = async () => {
    try {
      const [response] = await Promise.all([
        api.instance.get(`/statistics/incomes?month=${month}&year=${year}`)
      ])

      console.log('Monthly incomes', response.data);
      setMonthlyIncomes(response.data);
      // console.log(response.data.incomes.map((mi, index) => ({
      //   day: `${index}`,
      //   value: mi
      // })))
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    fetchMonthlyIncomes();
  }, [month, year]);

  return (
    // monthlyIncomes && vehicleTypeIncomes && breakdowns &&
    <div className="card w-full">
      <div>
        <h1 className="font-bold text-lg">Monthly statistics</h1>

        <div className="flex flex-col gap-4 items-start justify-start">
          <label>Choose month and year</label>
          <input type="month" className="minimal-input w-full" onChange={e => {
            setYear(e.target.value.split('-')[0])
            setMonth(e.target.value.split('-')[1])
          }} />
        </div>


        {
          monthlyIncomes &&
          <div className="w-full flex flex-col items-center justify-center">
            <ResponsiveContainer aspect={5} className={"w-full pr-8 pt-10"}>
              <AreaChart
                data={monthlyIncomes.incomes.map((mi, index) => ({
                  day: `${index}`,
                  value: mi
                }))}
                width={900}
                height={500}

              >
                <CartesianGrid strokeDasharray={"3 3"} />
                <XAxis dataKey="day" />
                <YAxis />
                <Tooltip />
                <Area type="monotone" dataKey="value" stroke="#8884d8" fill="#8884d8" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        }

        {
          vehicleTypeIncomes &&
          <div className="w-full flex flex-col items-center justify-center">
            <hr className="w-3/4" />
            <p className="font-bold text-xl text-left mt-4">Incomes by vehicle type</p>
            <ResponsiveContainer aspect={5} className={"w-full pr-8 pt-10"}>
              <BarChart
                width={900}
                height={500}
                data={Object.entries(vehicleTypeIncomes.incomes).map(([name, value]) => ({ name, value }))}
              >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="vehicleType" label={{ value: "Vehicle type", angle: 0, position: "insideLeft" }} />
                <YAxis />
                <Tooltip />
                <Bar dataKey="value" fill="#8884d8">
                  <LabelList
                    dataKey="name"
                    position="bottom"
                    fill="#a2a2a2"
                    fontSize={14}
                    formatter={(value) => `${value}`}
                  />
                </Bar>

              </BarChart>
            </ResponsiveContainer>
          </div>
        }

        {
          breakdowns &&
          <div className="w-full flex flex-col items-center justify-center">
            <hr className="w-3/4" />
            <p className="font-bold text-xl text-left mt-4">Breakdowns by vehicle type</p>
            <ResponsiveContainer aspect={5} className={"w-full pr-8 pt-10"}>
              <BarChart
                width={900}
                height={500}
                data={Object.entries(breakdowns).map(([name, count]) => ({ name, count }))}
              >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" label={{ value: "Vehicle type", angle: 0, position: "insideLeft" }} />
                <YAxis />
                <Tooltip />
                <Bar dataKey="count" fill="#8884d8">
                  <LabelList
                    dataKey="count"
                    position="bottom"
                    fill="#a2a2a2"
                    fontSize={14}
                    formatter={(count) => `${count}`}
                  />
                </Bar>
              </BarChart>
            </ResponsiveContainer>
          </div>
        }
      </div>
    </div>
  )
}
