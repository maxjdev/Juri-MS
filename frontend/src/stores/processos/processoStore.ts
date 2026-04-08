import { defineStore } from 'pinia'
import type { Processo, ProcessoPayload, ProcessoTabelaItem } from '../../models/processos/Processo'
import { processoService } from '../../services/processos/processoService'
import { useClienteStore } from '../clientes/clienteStore'

interface ProcessoState {
  items: Processo[]
  loading: boolean
  error: string
}

export const useProcessoStore = defineStore('processo', {
  state: (): ProcessoState => ({
    items: [],
    loading: false,
    error: '',
  }),
  getters: {
    tabelaItems(state): ProcessoTabelaItem[] {
      const clienteStore = useClienteStore()

      return state.items.map((processo) => {
        const cliente = clienteStore.items.find((item) => item.id === processo.clienteId)

        return {
          ...processo,
          clienteNome: cliente?.nomeCompleto ?? processo.clienteId,
        }
      })
    },
  },
  actions: {
    async listar() {
      this.loading = true
      this.error = ''

      try {
        this.items = await processoService.listar()
      } catch (error) {
        console.error(error)
        this.error = 'Nao foi possivel carregar os processos.'
      } finally {
        this.loading = false
      }
    },
    async inserir(payload: ProcessoPayload) {
      const processoCriado = await processoService.inserir(payload)
      this.items = [processoCriado, ...this.items]

      return processoCriado
    },
  },
})