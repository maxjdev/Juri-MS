<script lang="ts">
import { defineComponent } from 'vue'
import Button from 'primevue/button'
import Card from 'primevue/card'
import Column from 'primevue/column'
import DataTable from 'primevue/datatable'
import Message from 'primevue/message'
import ProgressSpinner from 'primevue/progressspinner'
import ClienteForm from './ClienteForm.vue'
import type { Cliente } from '../../models/clientes/Cliente'
import { useClienteStore } from '../../stores/clientes/clienteStore'

export default defineComponent({
  name: 'CrudCliente',
  components: {
    Button,
    Card,
    ClienteForm,
    Column,
    DataTable,
    Message,
    ProgressSpinner,
  },
  data() {
    return {
      clienteStore: useClienteStore(),
      formVisible: false,
    }
  },
  computed: {
    clientes(): Cliente[] {
      return this.clienteStore.items
    },
    loading(): boolean {
      return this.clienteStore.loading
    },
    error(): string {
      return this.clienteStore.error
    },
  },
  methods: {
    openForm() {
      this.formVisible = true
    },
    closeForm() {
      this.formVisible = false
    },
  },
})
</script>

<template>
  <Card>
    <template #title>
      <div class="flex align-items-center justify-content-between gap-3">
        <div class="flex align-items-center gap-3">
          <span>Clientes</span>
          <span class="text-color-secondary text-sm">{{ clientes.length }} registro(s)</span>
        </div>
        <Button label="Inserir" icon="pi pi-plus" size="small" @click="openForm" />
      </div>
    </template>
    <template #content>
      <div class="flex flex-column gap-3">
        <Message v-if="error" severity="error" :closable="false">{{ error }}</Message>

        <div v-if="loading" class="flex justify-content-center py-6">
          <ProgressSpinner strokeWidth="4" style="width: 3rem; height: 3rem" />
        </div>

        <DataTable
          v-else
          :value="clientes"
          dataKey="id"
          paginator
          :rows="5"
          size="small"
          stripedRows
          tableStyle="min-width: 100%"
        >
          <template #empty>
            Nenhum cliente encontrado.
          </template>
          <Column field="nomeCompleto" header="Nome Completo" />
          <Column field="cpf" header="CPF" />
          <Column field="dataNascimento" header="Data de Nascimento" />
        </DataTable>

        <ClienteForm v-model:visible="formVisible" @saved="closeForm" />
      </div>
    </template>
  </Card>
</template>