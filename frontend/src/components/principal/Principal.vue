<script lang="ts">
import { defineComponent } from 'vue'
import CrudCliente from '../clientes/CrudCliente.vue'
import CrudProcesso from '../processos/CrudProcesso.vue'
import { useClienteStore } from '../../stores/clientes/clienteStore'
import { useProcessoStore } from '../../stores/processos/processoStore'

export default defineComponent({
  name: 'Principal',
  components: {
    CrudCliente,
    CrudProcesso,
  },
  data() {
    return {
      clienteStore: useClienteStore(),
      processoStore: useProcessoStore(),
    }
  },
  async mounted() {
    await Promise.allSettled([
      this.clienteStore.listar(),
      this.processoStore.listar(),
    ])
  },
})
</script>

<template>
  <div class="min-h-screen surface-ground p-4 md:p-5 xl:p-6">
    <div class="flex flex-column gap-2 mb-4">
      <span class="text-primary font-semibold text-sm uppercase">Juri MS</span>
      <h1 class="m-0 text-3xl">Painel Principal</h1>
      <p class="m-0 text-color-secondary">
        Visualizacao consolidada de clientes e processos.
      </p>
    </div>

    <div class="grid">
      <div class="col-12 xl:col-6">
        <CrudCliente />
      </div>
      <div class="col-12 xl:col-6">
        <CrudProcesso />
      </div>
    </div>
  </div>
</template>