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
import Tag from 'primevue/tag'
import ProcessoDetail from './ProcessoDetail.vue'
import ProcessoForm from './ProcessoForm.vue'
import ProcessoUpdate from './ProcessoUpdate.vue'
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
    Dialog,
    Message,
    ProcessoDetail,
    ProcessoForm,
    ProcessoUpdate,
    ProgressSpinner,
    Tag,
  },
  data() {
    return {
      deleteError: '',
      deleteVisible: false,
      deleting: false,
      detailVisible: false,
      processoStore: useProcessoStore(),
      formVisible: false,
      updateVisible: false,
      selectedProcesso: null as ProcessoTabelaItem | null,
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
    openDetailDialog(processo: ProcessoTabelaItem) {
      this.selectedProcesso = processo
      this.detailVisible = true
    },
    closeDetailDialog() {
      this.detailVisible = false
      this.selectedProcesso = null
    },
    openUpdateDialog(processo: ProcessoTabelaItem) {
      this.selectedProcesso = processo
      this.updateVisible = true
    },
    closeUpdateDialog() {
      this.updateVisible = false
      this.selectedProcesso = null
    },
    openDeleteDialog(processo: ProcessoTabelaItem) {
      this.selectedProcesso = processo
      this.deleteError = ''
      this.deleteVisible = true
    },
    closeDeleteDialog() {
      this.deleteVisible = false
      this.deleteError = ''
      this.deleting = false
      this.selectedProcesso = null
    },
    async confirmDelete() {
      if (!this.selectedProcesso) {
        return
      }

      this.deleting = true
      this.deleteError = ''

      try {
        await this.processoStore.excluir(this.selectedProcesso.id)
        this.closeDeleteDialog()
      } catch (error) {
        if (axios.isAxiosError(error) && error.response?.data?.message) {
          this.deleteError = String(error.response.data.message)
        } else {
          this.deleteError = 'Nao foi possivel excluir o processo.'
        }
        this.deleting = false
      }
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
          <Column header="Acoes">
            <template #body="slotProps">
              <div class="flex align-items-center gap-1">
                <Button
                  icon="pi pi-eye"
                  severity="info"
                  text
                  rounded
                  aria-label="Detalhar processo"
                  @click="openDetailDialog(slotProps.data)"
                />
                <Button
                  icon="pi pi-pencil"
                  severity="contrast"
                  text
                  rounded
                  aria-label="Editar processo"
                  @click="openUpdateDialog(slotProps.data)"
                />
                <Button
                  icon="pi pi-trash"
                  severity="danger"
                  text
                  rounded
                  aria-label="Excluir processo"
                  @click="openDeleteDialog(slotProps.data)"
                />
              </div>
            </template>
          </Column>
        </DataTable>

        <ProcessoDetail v-model:visible="detailVisible" :processo="selectedProcesso" />
        <ProcessoForm v-model:visible="formVisible" @saved="closeForm" />
        <ProcessoUpdate
          v-model:visible="updateVisible"
          :processo="selectedProcesso"
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
              Deseja realmente excluir o processo
              <strong>{{ selectedProcesso?.numeroProcesso }}</strong>?
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