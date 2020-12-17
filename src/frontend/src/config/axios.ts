import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `http://localhost:8080/api`
});

export default AXIOS 