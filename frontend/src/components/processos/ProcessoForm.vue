<script lang="ts">
import { defineComponent } from 'vue'
import axios from 'axios'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import Message from 'primevue/message'
import Select from 'primevue/select'
import type { ProcessoPayload, StatusProcesso } from '../../models/processos/Processo'
import { statusProcessoLabels } from '../../models/processos/Processo'
import { useClienteStore } from '../../stores/clientes/clienteStore'
import { useProcessoStore } from '../../stores/processos/processoStore'

function createInitialForm(): ProcessoPayload {
  return {
    numeroProcesso: '',
    clienteId: '',
    status: undefined,
  }
}

export default defineComponent({
  name: 'ProcessoForm',
  components: {
    Button,
    Dialog,
    InputText,
    Message,
    Select,
  },
  props: {
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
      form: createInitialForm(),
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
        this.resetForm()
        if (!this.clienteStore.items.length) {
          this.clienteStore.listar()
        }
      }
    },
  },
  methods: {
    closeDialog() {
      this.$emit('update:visible', false)
    },
    resetForm() {
      this.form = createInitialForm()
      this.error = ''
      this.submitting = false
    },
    async submit() {
      this.submitting = true
      this.error = ''

      try {
        await this.processoStore.inserir({
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
          this.error = 'Nao foi possivel inserir o processo.'
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
    header="Inserir Processo"
    :style="{ width: '34rem', maxWidth: '96vw' }"
    @update:visible="$emit('update:visible', $event)"
  >
    <div class="flex flex-column gap-4">
      <Message v-if="error" severity="error" :closable="false">{{ error }}</Message>

      <div class="flex flex-column gap-2">
        <label for="processo-numero" class="font-semibold">Numero do Processo</label>
        <InputText id="processo-numero" v-model="form.numeroProcesso" fluid />
      </div>

      <div class="flex flex-column gap-2">
        <label for="processo-cliente" class="font-semibold">Cliente</label>
        <Select
          id="processo-cliente"
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
        <label for="processo-status" class="font-semibold">Status</label>
        <Select
          id="processo-status"
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
        <Button label="Inserir" :loading="submitting" @click="submit" />
      </div>
    </template>
  </Dialog>
</template>