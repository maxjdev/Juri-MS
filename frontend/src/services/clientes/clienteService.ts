import axios from 'axios'
import type { Cliente, ClientePayload } from '../../models/clientes/Cliente'

const clienteApi = axios.create({
  baseURL: import.meta.env.VITE_CLIENTE_API,
})

export const clienteService = {
  async listar(): Promise<Cliente[]> {
    const { data } = await clienteApi.get<Cliente[]>('/clientes')
    return data
  },
  async inserir(payload: ClientePayload): Promise<Cliente> {
    const { data } = await clienteApi.post<Cliente>('/clientes', payload)
    return data
  },
}