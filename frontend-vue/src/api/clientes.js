import http from './http'

/** GET /api/clientes */
export function listarClientes() {
  return http.get('/api/clientes')
}

/** GET /api/clientes/activos */
export function listarClientesActivos() {
  return http.get('/api/clientes/activos')
}

/** GET /api/clientes/buscar?q=... */
export function buscarClientes(q) {
  return http.get('/api/clientes/buscar', { params: { q } })
}
