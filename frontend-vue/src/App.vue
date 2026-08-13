<script setup>
import { computed, onMounted, ref } from 'vue'
import { listarClientes, listarClientesActivos } from './api/clientes'
import { borrarCookie, crearCookie, leerCookie } from './api/cookieDemo'

const clientes = ref([])
const clienteIdSeleccionado = ref('')
const cargando = ref(false)
const error = ref('')
const soloActivos = ref(false)

const nombreCookie = ref('Eduardo')
const cookieMsg = ref('')
const cookieBusy = ref(false)

const clienteSeleccionado = computed(() =>
  clientes.value.find((c) => String(c.id) === String(clienteIdSeleccionado.value)) || null
)

async function cargarClientes() {
  cargando.value = true
  error.value = ''
  try {
    const { data } = soloActivos.value
      ? await listarClientesActivos()
      : await listarClientes()
    clientes.value = data
    if (
      clienteIdSeleccionado.value &&
      !clientes.value.some((c) => String(c.id) === String(clienteIdSeleccionado.value))
    ) {
      clienteIdSeleccionado.value = ''
    }
  } catch (e) {
    error.value =
      'No se pudo cargar clientes. ¿La API está en :8080 y CORS habilitado?'
    console.error(e)
  } finally {
    cargando.value = false
  }
}

async function onCrearCookie() {
  cookieBusy.value = true
  cookieMsg.value = ''
  try {
    const { data } = await crearCookie(nombreCookie.value)
    cookieMsg.value = data.mensaje + ' — ' + (data.hint || '')
  } catch (e) {
    cookieMsg.value = 'Error al crear cookie'
    console.error(e)
  } finally {
    cookieBusy.value = false
  }
}

async function onLeerCookie() {
  cookieBusy.value = true
  cookieMsg.value = ''
  try {
    const { data } = await leerCookie()
    cookieMsg.value = data.mensaje
  } catch (e) {
    cookieMsg.value = 'Error al leer cookie'
    console.error(e)
  } finally {
    cookieBusy.value = false
  }
}

async function onBorrarCookie() {
  cookieBusy.value = true
  cookieMsg.value = ''
  try {
    const { data } = await borrarCookie()
    cookieMsg.value = data.mensaje
  } catch (e) {
    cookieMsg.value = 'Error al borrar cookie'
    console.error(e)
  } finally {
    cookieBusy.value = false
  }
}

onMounted(cargarClientes)
</script>

<template>
  <main class="page">
    <header class="header">
      <p class="eyebrow">Lección frontend ↔ API</p>
      <h1>Clientes desde Spring Boot</h1>
      <p class="sub">
        Vue llama a <code>GET /api/clientes</code> y arma select + tabla.
      </p>
    </header>

    <section class="panel">
      <h2>Ejercicio: Cookie</h2>
      <p class="sub" style="margin-bottom: 0.85rem">
        1) Crear → el server manda <code>Set-Cookie</code> ·
        2) Leer → el browser la reenvía solo ·
        3) Borrar
      </p>

      <div class="row">
        <label class="field">
          <span>Nombre para la cookie</span>
          <input v-model="nombreCookie" type="text" placeholder="Tu nombre" />
        </label>
      </div>

      <div class="row">
        <button type="button" class="btn" :disabled="cookieBusy" @click="onCrearCookie">
          1. Crear cookie
        </button>
        <button type="button" class="btn" :disabled="cookieBusy" @click="onLeerCookie">
          2. Leer cookie
        </button>
        <button type="button" class="btn" :disabled="cookieBusy" @click="onBorrarCookie">
          3. Borrar cookie
        </button>
      </div>

      <div v-if="cookieMsg" class="detalle">{{ cookieMsg }}</div>
      <p class="sub" style="margin-top: 0.75rem">
        Tip: en DevTools → Application → Cookies → <code>localhost</code> busca
        <code>demo_usuario</code>. Es <strong>HttpOnly</strong>, así que
        <code>document.cookie</code> no la muestra.
      </p>
    </section>

    <section class="panel">
      <div class="row">
        <label class="field">
          <span>Filtro</span>
          <label class="check">
            <input v-model="soloActivos" type="checkbox" @change="cargarClientes" />
            Solo activos (<code>/api/clientes/activos</code>)
          </label>
        </label>

        <button type="button" class="btn" :disabled="cargando" @click="cargarClientes">
          {{ cargando ? 'Cargando…' : 'Recargar' }}
        </button>
      </div>

      <p v-if="error" class="error">{{ error }}</p>

      <label class="field">
        <span>Select dinámico</span>
        <select v-model="clienteIdSeleccionado" :disabled="cargando || !clientes.length">
          <option value="">— elige un cliente —</option>
          <option v-for="c in clientes" :key="c.id" :value="c.id">
            {{ c.razonSocial }} ({{ c.rfc }})
          </option>
        </select>
      </label>

      <div v-if="clienteSeleccionado" class="detalle">
        <strong>Seleccionado:</strong>
        {{ clienteSeleccionado.razonSocial }} · {{ clienteSeleccionado.correoElectronico || 'sin correo' }}
      </div>
    </section>

    <section class="panel">
      <h2>Tabla dinámica ({{ clientes.length }})</h2>
      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>RFC</th>
              <th>Razón social</th>
              <th>Correo</th>
              <th>Teléfono</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            <tr v-if="!clientes.length && !cargando">
              <td colspan="6">Sin datos</td>
            </tr>
            <tr
              v-for="c in clientes"
              :key="c.id"
              :class="{ selected: String(c.id) === String(clienteIdSeleccionado) }"
              @click="clienteIdSeleccionado = c.id"
            >
              <td>{{ c.id }}</td>
              <td>{{ c.rfc }}</td>
              <td>{{ c.razonSocial }}</td>
              <td>{{ c.correoElectronico }}</td>
              <td>{{ c.telefono }}</td>
              <td>{{ c.estado }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </main>
</template>
