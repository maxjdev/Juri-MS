import { defineStore } from 'pinia'
import type { Cliente, ClientePayload } from '../../models/clientes/Cliente'
import { clienteService } from '../../services/clientes/clienteService'

interface ClienteState {
  items: Cliente[]
  loading: boolean
  error: string
}

export const useClienteStore = defineStore('cliente', {
  state: (): ClienteState => ({
    items: [],
    loading: false,
    error: '',
  }),
  actions: {
    async listar() {
      this.loading = true
      this.error = ''

      try {
        this.items = await clienteService.listar()
      } catch (error) {
        console.error(error)
        this.error = 'Nao foi possivel carregar os clientes.'
      } finally {
        this.loading = false
      }
    },
    async inserir(payload: ClientePayload) {
      const clienteCriado = await clienteService.inserir(payload)
      this.items = [clienteCriado, ...this.items]

      return clienteCriado
    },
    async atualizar(id: string, payload: ClientePayload) {
      const clienteAtualizado = await clienteService.atualizar(id, payload)
      this.items = this.items.map((cliente) => (cliente.id === id ? clienteAtualizado : cliente))

      return clienteAtualizado
    },
    async excluir(id: string) {
      await clienteService.excluir(id)
      this.items = this.items.filter((cliente) => cliente.id !== id)
    },
  },
})