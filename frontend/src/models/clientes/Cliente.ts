export interface Cliente {
  id: string
  nomeCompleto: string
  cpf: string
  dataNascimento: string
}

export interface ClientePayload {
  nomeCompleto: string
  cpf: string
  dataNascimento: string
}