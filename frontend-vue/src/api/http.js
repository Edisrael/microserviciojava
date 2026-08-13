import axios from 'axios'

// Base URL de tu API Spring Boot
const http = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
  // Imprescindible para cookies entre Vue (:5173) y API (:8080)
  withCredentials: true,
})

export default http
