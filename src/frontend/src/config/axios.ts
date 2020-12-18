import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`
});

export default AXIOS 