export type StatusProcesso =
  | 'SEM_STATUS'
  | 'ABERTO'
  | 'EM_ANDAMENTO'
  | 'CONCLUIDO'
  | 'CANCELADO'

export interface Processo {
  id: string
  numeroProcesso: string
  clienteId: string
  status: StatusProcesso
}

export interface ProcessoPayload {
  numeroProcesso: string
  clienteId: string
  status?: StatusProcesso
}

export interface ProcessoTabelaItem extends Processo {
  clienteNome: string
}

export const statusProcessoLabels: Record<StatusProcesso, string> = {
  SEM_STATUS: 'Sem Status',
  ABERTO: 'Aberto',
  EM_ANDAMENTO: 'Em Andamento',
  CONCLUIDO: 'Concluido',
  CANCELADO: 'Cancelado',
}

export const statusProcessoSeverity: Record<StatusProcesso, string> = {
  SEM_STATUS: 'secondary',
  ABERTO: 'info',
  EM_ANDAMENTO: 'warn',
  CONCLUIDO: 'success',
  CANCELADO: 'danger',
}