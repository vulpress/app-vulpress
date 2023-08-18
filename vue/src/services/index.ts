import axios, { type AxiosInstance } from "axios";
import { AuthApi, Configuration } from "@/api/giannitsa";
import AuthService from "./auth.service";

const instance: AxiosInstance = axios.create();
instance.interceptors.request.use((config) => {
  const token: string | null = localStorage.getItem("jwtToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const prefix: string = "/api";
const configuration: Configuration = new Configuration();

const authApi: AuthApi = new AuthApi(configuration, prefix, instance);

export const authService: AuthService = new AuthService(authApi);
