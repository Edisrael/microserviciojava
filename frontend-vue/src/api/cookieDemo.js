import http from './http'

/** POST /api/demo/cookie  → Set-Cookie */
export function crearCookie(nombre) {
  return http.post('/api/demo/cookie', { nombre })
}

/** GET /api/demo/cookie → el browser manda la cookie sola */
export function leerCookie() {
  return http.get('/api/demo/cookie')
}

/** DELETE /api/demo/cookie → la borra */
export function borrarCookie() {
  return http.delete('/api/demo/cookie')
}
