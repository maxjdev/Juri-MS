import axios from 'axios'
import { apiConfig } from '../../config/api'
import type { Cliente, ClientePayload } from '../../models/clientes/Cliente'

const clienteApi = axios.create({
  baseURL: apiConfig.clienteBaseUrl,
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
  async atualizar(id: string, payload: ClientePayload): Promise<Cliente> {
    const { data } = await clienteApi.put<Cliente>(`/clientes/${id}`, payload)
    return data
  },
  async excluir(id: string): Promise<void> {
    await clienteApi.delete(`/clientes/${id}`)
  },
}