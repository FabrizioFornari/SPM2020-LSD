import axios from 'axios'
import { getToken } from '@/firebase'

const AXIOS = axios.create({
  baseURL: `/api`
});

AXIOS.interceptors.request.use(async request => {
  const token = await getToken();
  if (token) request.headers.common.Authorization = 'Bearer ' + token;
  return request;
});

export default AXIOS 