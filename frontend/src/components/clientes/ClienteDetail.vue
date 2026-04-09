<script lang="ts">
import { defineComponent } from 'vue'
import type { PropType } from 'vue'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import type { Cliente } from '../../models/clientes/Cliente'

export default defineComponent({
  name: 'ClienteDetail',
  components: {
    Button,
    Dialog,
  },
  props: {
    cliente: {
      type: Object as PropType<Cliente | null>,
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
  },
})
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Detalhes do Cliente"
    :style="{ width: '32rem', maxWidth: '96vw' }"
    @update:visible="$emit('update:visible', $event)"
  >
    <div class="flex flex-column gap-4">
      <div class="surface-50 border-round p-3 flex flex-column gap-3">
        <div class="flex flex-column gap-1">
          <span class="text-sm text-color-secondary">ID</span>
          <span class="font-medium">{{ cliente?.id || '-' }}</span>
        </div>

        <div class="flex flex-column gap-1">
          <span class="text-sm text-color-secondary">Nome Completo</span>
          <span class="font-medium">{{ cliente?.nomeCompleto || '-' }}</span>
        </div>

        <div class="grid">
          <div class="col-12 md:col-6">
            <div class="flex flex-column gap-1">
              <span class="text-sm text-color-secondary">CPF</span>
              <span class="font-medium">{{ cliente?.cpf || '-' }}</span>
            </div>
          </div>

          <div class="col-12 md:col-6">
            <div class="flex flex-column gap-1">
              <span class="text-sm text-color-secondary">Data de Nascimento</span>
              <span class="font-medium">{{ cliente?.dataNascimento || '-' }}</span>
            </div>
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