<script lang="ts">
import { defineComponent } from 'vue'
import type { PropType } from 'vue'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import Tag from 'primevue/tag'
import type { ProcessoTabelaItem, StatusProcesso } from '../../models/processos/Processo'
import { statusProcessoLabels, statusProcessoSeverity } from '../../models/processos/Processo'

export default defineComponent({
  name: 'ProcessoDetail',
  components: {
    Button,
    Dialog,
    Tag,
  },
  props: {
    processo: {
      type: Object as PropType<ProcessoTabelaItem | null>,
      default: null,
    },
    visible: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:visible'],
  methods: {
    closeDialog() {
      this.$emit('update:visible', false)
    },
    statusLabel(status?: StatusProcesso): string {
      return status ? statusProcessoLabels[status] : '-'
    },
    statusSeverity(status?: StatusProcesso): string {
      return status ? statusProcessoSeverity[status] : 'secondary'
    },
  },
})
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Detalhes do Processo"
    :style="{ width: '34rem', maxWidth: '96vw' }"
    @update:visible="$emit('update:visible', $event)"
  >
    <div class="flex flex-column gap-4">
      <div class="surface-50 border-round p-3 flex flex-column gap-3">
        <div class="flex flex-column gap-1">
          <span class="text-sm text-color-secondary">ID</span>
          <span class="font-medium">{{ processo?.id || '-' }}</span>
        </div>

        <div class="flex flex-column gap-1">
          <span class="text-sm text-color-secondary">Numero do Processo</span>
          <span class="font-medium">{{ processo?.numeroProcesso || '-' }}</span>
        </div>

        <div class="grid">
          <div class="col-12 md:col-6">
            <div class="flex flex-column gap-1">
              <span class="text-sm text-color-secondary">Cliente</span>
              <span class="font-medium">{{ processo?.clienteNome || '-' }}</span>
            </div>
          </div>

          <div class="col-12 md:col-6">
            <div class="flex flex-column gap-1">
              <span class="text-sm text-color-secondary">Cliente ID</span>
              <span class="font-medium">{{ processo?.clienteId || '-' }}</span>
            </div>
          </div>
        </div>

        <div class="flex flex-column gap-1">
          <span class="text-sm text-color-secondary">Status</span>
          <div>
            <Tag :value="statusLabel(processo?.status)" :severity="statusSeverity(processo?.status)" />
          </div>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="flex justify-content-end">
        <Button label="Fechar" severity="secondary" text @click="closeDialog" />
      </div>
    </template>
  </Dialog>
</template>