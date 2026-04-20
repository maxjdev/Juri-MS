import axios from 'axios'
import { apiConfig } from '../../config/api'
import type { Processo, ProcessoPayload } from '../../models/processos/Processo'

const processoApi = axios.create({
  baseURL: apiConfig.processoBaseUrl,
})

export const processoService = {
  async listar(): Promise<Processo[]> {
    const { data } = await processoApi.get<Processo[]>('/processos')
    return data
  },
  async inserir(payload: ProcessoPayload): Promise<Processo> {
    const requestPayload = payload.status
      ? payload
      : {
          numeroProcesso: payload.numeroProcesso,
          clienteId: payload.clienteId,
        }

    const { data } = await processoApi.post<Processo>('/processos', requestPayload)
    return data
  },
  async atualizar(id: string, payload: ProcessoPayload): Promise<Processo> {
    const requestPayload = payload.status
      ? payload
      : {
          numeroProcesso: payload.numeroProcesso,
          clienteId: payload.clienteId,
        }

    const { data } = await processoApi.put<Processo>(`/processos/${id}`, requestPayload)
    return data
  },
  async excluir(id: string): Promise<void> {
    await processoApi.delete(`/processos/${id}`)
  },
}