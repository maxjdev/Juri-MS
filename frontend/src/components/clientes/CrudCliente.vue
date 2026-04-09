<script lang="ts">
import { defineComponent } from 'vue'
import axios from 'axios'
import Button from 'primevue/button'
import Card from 'primevue/card'
import Dialog from 'primevue/dialog'
import Column from 'primevue/column'
import DataTable from 'primevue/datatable'
import Message from 'primevue/message'
import ProgressSpinner from 'primevue/progressspinner'
import ClienteDetail from './ClienteDetail.vue'
import ClienteForm from './ClienteForm.vue'
import ClienteUpdate from './ClienteUpdate.vue'
import type { Cliente } from '../../models/clientes/Cliente'
import { useClienteStore } from '../../stores/clientes/clienteStore'
import { useProcessoStore } from '../../stores/processos/processoStore'

export default defineComponent({
  name: 'CrudCliente',
  components: {
    Button,
    Card,
    ClienteDetail,
    ClienteForm,
    ClienteUpdate,
    Column,
    DataTable,
    Dialog,
    Message,
    ProgressSpinner,
  },
  data() {
    return {
      clienteStore: useClienteStore(),
      processoStore: useProcessoStore(),
      deleteError: '',
      deleteVisible: false,
      deleting: false,
      detailVisible: false,
      formVisible: false,
      updateVisible: false,
      selectedCliente: null as Cliente | null,
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
    openDetailDialog(cliente: Cliente) {
      this.selectedCliente = cliente
      this.detailVisible = true
    },
    closeDetailDialog() {
      this.detailVisible = false
      this.selectedCliente = null
    },
    openUpdateDialog(cliente: Cliente) {
      this.selectedCliente = cliente
      this.updateVisible = true
    },
    closeUpdateDialog() {
      this.updateVisible = false
      this.selectedCliente = null
    },
    openDeleteDialog(cliente: Cliente) {
      this.selectedCliente = cliente
      this.deleteError = ''
      this.deleteVisible = true
    },
    closeDeleteDialog() {
      this.deleteVisible = false
      this.deleteError = ''
      this.deleting = false
      this.selectedCliente = null
    },
    async confirmDelete() {
      if (!this.selectedCliente) {
        return
      }

      this.deleting = true
      this.deleteError = ''

      try {
        await this.clienteStore.excluir(this.selectedCliente.id)
        this.processoStore.removerPorClienteId(this.selectedCliente.id)
        this.closeDeleteDialog()
      } catch (error) {
        if (axios.isAxiosError(error) && error.response?.data?.message) {
          this.deleteError = String(error.response.data.message)
        } else {
          this.deleteError = 'Nao foi possivel excluir o cliente.'
        }
        this.deleting = false
      }
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
          <Column header="Acoes">
            <template #body="slotProps">
              <div class="flex align-items-center gap-1">
                <Button
                  icon="pi pi-eye"
                  severity="info"
                  text
                  rounded
                  aria-label="Detalhar cliente"
                  @click="openDetailDialog(slotProps.data)"
                />
                <Button
                  icon="pi pi-pencil"
                  severity="contrast"
                  text
                  rounded
                  aria-label="Editar cliente"
                  @click="openUpdateDialog(slotProps.data)"
                />
                <Button
                  icon="pi pi-trash"
                  severity="danger"
                  text
                  rounded
                  aria-label="Excluir cliente"
                  @click="openDeleteDialog(slotProps.data)"
                />
              </div>
            </template>
          </Column>
        </DataTable>

        <ClienteDetail v-model:visible="detailVisible" :cliente="selectedCliente" />
        <ClienteForm v-model:visible="formVisible" @saved="closeForm" />
        <ClienteUpdate
          v-model:visible="updateVisible"
          :cliente="selectedCliente"
          @saved="closeUpdateDialog"
        />

        <Dialog
          :visible="deleteVisible"
          modal
          header="Confirmar Exclusao"
          :style="{ width: '28rem', maxWidth: '96vw' }"
          @update:visible="deleteVisible = $event"
        >
          <div class="flex flex-column gap-3">
            <Message v-if="deleteError" severity="error" :closable="false">{{ deleteError }}</Message>
            <p class="m-0 line-height-3">
              Deseja realmente excluir o cliente
              <strong>{{ selectedCliente?.nomeCompleto }}</strong>?
            </p>
            <p class="m-0 text-color-secondary line-height-3">
              Os processos vinculados a este cliente tambem serao removidos.
            </p>
          </div>

          <template #footer>
            <div class="flex justify-content-end gap-2">
              <Button label="Cancelar" severity="secondary" text @click="closeDeleteDialog" />
              <Button label="Excluir" severity="danger" :loading="deleting" @click="confirmDelete" />
            </div>
          </template>
        </Dialog>
      </div>
    </template>
  </Card>
</template>