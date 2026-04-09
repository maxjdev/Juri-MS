<script lang="ts">
import { defineComponent } from 'vue'
import type { PropType } from 'vue'
import axios from 'axios'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import Message from 'primevue/message'
import Select from 'primevue/select'
import type { Processo, ProcessoPayload, StatusProcesso } from '../../models/processos/Processo'
import { statusProcessoLabels } from '../../models/processos/Processo'
import { useClienteStore } from '../../stores/clientes/clienteStore'
import { useProcessoStore } from '../../stores/processos/processoStore'

function createFormFromProcesso(processo: Processo | null): ProcessoPayload {
  return {
    numeroProcesso: processo?.numeroProcesso ?? '',
    clienteId: processo?.clienteId ?? '',
    status: processo?.status,
  }
}

export default defineComponent({
  name: 'ProcessoUpdate',
  components: {
    Button,
    Dialog,
    InputText,
    Message,
    Select,
  },
  props: {
    processo: {
      type: Object as PropType<Processo | null>,
      default: null,
    },
    visible: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['update:visible', 'saved'],
  data() {
    return {
      clienteStore: useClienteStore(),
      processoStore: useProcessoStore(),
      form: createFormFromProcesso(this.processo),
      error: '',
      submitting: false,
      statusOptions: Object.entries(statusProcessoLabels).map(([value, label]) => ({
        label,
        value,
      })),
    }
  },
  computed: {
    clienteOptions(): Array<{ label: string; value: string }> {
      return this.clienteStore.items.map((cliente) => ({
        label: cliente.nomeCompleto,
        value: cliente.id,
      }))
    },
  },
  watch: {
    visible(newValue: boolean) {
      if (newValue) {
        this.syncForm()
        if (!this.clienteStore.items.length) {
          this.clienteStore.listar()
        }
      }
    },
    processo() {
      if (this.visible) {
        this.syncForm()
      }
    },
  },
  methods: {
    closeDialog() {
      this.$emit('update:visible', false)
    },
    syncForm() {
      this.form = createFormFromProcesso(this.processo)
      this.error = ''
      this.submitting = false
    },
    async submit() {
      if (!this.processo) {
        return
      }

      this.submitting = true
      this.error = ''

      try {
        await this.processoStore.atualizar(this.processo.id, {
          numeroProcesso: this.form.numeroProcesso.trim(),
          clienteId: this.form.clienteId,
          status: this.form.status,
        })

        this.$emit('saved')
        this.closeDialog()
      } catch (error) {
        if (axios.isAxiosError(error) && error.response?.data?.message) {
          this.error = String(error.response.data.message)
        } else {
          this.error = 'Nao foi possivel atualizar o processo.'
        }
      } finally {
        this.submitting = false
      }
    },
    updateStatus(value: StatusProcesso | undefined) {
      this.form.status = value
    },
  },
})
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Editar Processo"
    :style="{ width: '34rem', maxWidth: '96vw' }"
    @update:visible="$emit('update:visible', $event)"
  >
    <div class="flex flex-column gap-4">
      <Message v-if="error" severity="error" :closable="false">{{ error }}</Message>

      <div class="flex flex-column gap-2">
        <label for="processo-update-numero" class="font-semibold">Numero do Processo</label>
        <InputText id="processo-update-numero" v-model="form.numeroProcesso" fluid />
      </div>

      <div class="flex flex-column gap-2">
        <label for="processo-update-cliente" class="font-semibold">Cliente</label>
        <Select
          id="processo-update-cliente"
          v-model="form.clienteId"
          :options="clienteOptions"
          optionLabel="label"
          optionValue="value"
          placeholder="Selecione um cliente"
          fluid
          filter
        />
      </div>

      <div class="flex flex-column gap-2">
        <label for="processo-update-status" class="font-semibold">Status</label>
        <Select
          id="processo-update-status"
          :modelValue="form.status"
          :options="statusOptions"
          optionLabel="label"
          optionValue="value"
          placeholder="Selecione um status"
          fluid
          showClear
          @update:modelValue="updateStatus"
        />
      </div>
    </div>

    <template #footer>
      <div class="flex justify-content-end gap-2">
        <Button label="Cancelar" severity="secondary" text @click="closeDialog" />
        <Button label="Salvar" :loading="submitting" @click="submit" />
      </div>
    </template>
  </Dialog>
</template>