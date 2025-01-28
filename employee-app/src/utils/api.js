import axios from "axios";

export const api = {
  instance: axios.create({ baseURL: "http://localhost:8080/api" }),
  getUserType: async () => {
    const response = await api.instance.get(`/users/${localStorage.getItem("username")}`);
    return response.data.userType;
  },
  isSignedIn: () => localStorage.getItem("username") !== null,
  register: async (registerDto) => {
    return await api.instance.post("/public/auth/register", registerDto);
  },
  login: async (loginDto) => {
    return await api.instance.post("/public/auth/login", loginDto);
  }
}

// request-response interceptors for api calls
api.instance.interceptors.request.use(config => {
  config.headers['x-username'] = localStorage.getItem("username");
  config.headers['Content-Type'] = "application/json";
  return config;
})

// api.instance.interceptors.response.use(
//   response => response,
//   error => {
//     if (error.status === 401) {
//       localStorage.removeItem("username");
//       window.location.href = '/login';
//     }

//     return Promise.reject(error);
//   }
// )

export default api;
