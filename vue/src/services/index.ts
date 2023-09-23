import axios, { type AxiosInstance } from 'axios';
import { ArticleApi, AuthApi, Configuration, RegistrationApi, ViewApi } from '@/api/vulpress';
import AuthService from './auth.service';
import ViewService from './view.service';
import ArticleService from './article.service';
import RegistrationService from './registration.service';

const instance: AxiosInstance = axios.create();
instance.interceptors.request.use((config) => {
  const token: string | null = localStorage.getItem('jwtToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const prefix: string = '/api';
const configuration: Configuration = new Configuration();

const authApi: AuthApi = new AuthApi(configuration, prefix, instance);
const articleApi: ArticleApi = new ArticleApi(configuration, prefix, instance);
const viewApi: ViewApi = new ViewApi(configuration, prefix, instance);
const registrationApi: RegistrationApi = new RegistrationApi(configuration, prefix, instance);

export const authService: AuthService = new AuthService(authApi);
export const viewService: ViewService = new ViewService(viewApi);
export const articleService: ArticleService = new ArticleService(articleApi);
export const registrationService: RegistrationService = new RegistrationService(registrationApi);
