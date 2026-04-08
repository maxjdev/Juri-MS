<script lang="ts">
import { defineComponent } from 'vue'
import Button from 'primevue/button'
import Card from 'primevue/card'
import Column from 'primevue/column'
import DataTable from 'primevue/datatable'
import Message from 'primevue/message'
import ProgressSpinner from 'primevue/progressspinner'
import Tag from 'primevue/tag'
import ProcessoForm from './ProcessoForm.vue'
import type { ProcessoTabelaItem, StatusProcesso } from '../../models/processos/Processo'
import { statusProcessoLabels, statusProcessoSeverity } from '../../models/processos/Processo'
import { useProcessoStore } from '../../stores/processos/processoStore'

export default defineComponent({
  name: 'CrudProcesso',
  components: {
    Button,
    Card,
    Column,
    DataTable,
    Message,
    ProcessoForm,
    ProgressSpinner,
    Tag,
  },
  data() {
    return {
      processoStore: useProcessoStore(),
      formVisible: false,
    }
  },
  computed: {
    processos(): ProcessoTabelaItem[] {
      return this.processoStore.tabelaItems
    },
    loading(): boolean {
      return this.processoStore.loading
    },
    error(): string {
      return this.processoStore.error
    },
  },
  methods: {
    openForm() {
      this.formVisible = true
    },
    closeForm() {
      this.formVisible = false
    },
    statusLabel(status: StatusProcesso): string {
      return statusProcessoLabels[status]
    },
    statusSeverity(status: StatusProcesso): string {
      return statusProcessoSeverity[status]
    },
  },
})
</script>

<template>
  <Card>
    <template #title>
      <div class="flex align-items-center justify-content-between gap-3">
        <div class="flex align-items-center gap-3">
          <span>Processos</span>
          <span class="text-color-secondary text-sm">{{ processos.length }} registro(s)</span>
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
          :value="processos"
          dataKey="id"
          paginator
          :rows="5"
          size="small"
          stripedRows
          tableStyle="min-width: 100%"
        >
          <template #empty>
            Nenhum processo encontrado.
          </template>
          <Column field="numeroProcesso" header="Numero do Processo" />
          <Column field="clienteNome" header="Cliente" />
          <Column header="Status">
            <template #body="slotProps">
              <Tag
                :value="statusLabel(slotProps.data.status)"
                :severity="statusSeverity(slotProps.data.status)"
              />
            </template>
          </Column>
        </DataTable>

        <ProcessoForm v-model:visible="formVisible" @saved="closeForm" />
      </div>
    </template>
  </Card>
</template>