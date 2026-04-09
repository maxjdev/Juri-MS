<script lang="ts">
import { defineComponent } from 'vue'
import axios from 'axios'
import Button from 'primevue/button'
import Dialog from 'primevue/dialog'
import InputMask from 'primevue/inputmask'
import InputText from 'primevue/inputtext'
import Message from 'primevue/message'
import type { ClientePayload } from '../../models/clientes/Cliente'
import { useClienteStore } from '../../stores/clientes/clienteStore'

function createInitialForm(): ClientePayload {
  return {
    nomeCompleto: '',
    cpf: '',
    dataNascimento: '',
  }
}

export default defineComponent({
  name: 'ClienteForm',
  components: {
    Button,
    Dialog,
    InputMask,
    InputText,
    Message,
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
      form: createInitialForm(),
      error: '',
      submitting: false,
    }
  },
  watch: {
    visible(newValue: boolean) {
      if (newValue) {
        this.resetForm()
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
        await this.clienteStore.inserir({
          nomeCompleto: this.form.nomeCompleto.trim(),
          cpf: this.form.cpf,
          dataNascimento: this.form.dataNascimento,
        })

        this.$emit('saved')
        this.closeDialog()
      } catch (error) {
        if (axios.isAxiosError(error) && error.response?.data?.message) {
          this.error = String(error.response.data.message)
        } else {
          this.error = 'Nao foi possivel inserir o cliente.'
        }
      } finally {
        this.submitting = false
      }
    },
  },
})
</script>

<template>
  <Dialog
    :visible="visible"
    modal
    header="Inserir Cliente"
    :style="{ width: '32rem', maxWidth: '96vw' }"
    @update:visible="$emit('update:visible', $event)"
  >
    <div class="flex flex-column gap-4">
      <Message v-if="error" severity="error" :closable="false">{{ error }}</Message>

      <div class="flex flex-column gap-2">
        <label for="cliente-nome" class="font-semibold">Nome Completo</label>
        <InputText id="cliente-nome" v-model="form.nomeCompleto" fluid />
      </div>

      <div class="grid">
        <div class="col-12 md:col-6">
          <div class="flex flex-column gap-2">
            <label for="cliente-cpf" class="font-semibold">CPF</label>
            <InputMask id="cliente-cpf" v-model="form.cpf" mask="99999999999" :unmask="true" fluid />
          </div>
        </div>

        <div class="col-12 md:col-6">
          <div class="flex flex-column gap-2">
            <label for="cliente-data" class="font-semibold">Data de Nascimento</label>
            <InputMask id="cliente-data" v-model="form.dataNascimento" mask="99/99/9999" fluid />
          </div>
        </div>
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