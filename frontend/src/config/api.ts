const resolveApiBaseUrl = (serviceUrl: string | undefined): string => {
  if (import.meta.env.PROD) {
    return '/api'
  }

  const normalizedServiceUrl = serviceUrl?.trim()

  return normalizedServiceUrl && normalizedServiceUrl.length > 0 ? normalizedServiceUrl : '/api'
}

export const apiConfig = {
  clienteBaseUrl: resolveApiBaseUrl(import.meta.env.VITE_CLIENTE_API),
  processoBaseUrl: resolveApiBaseUrl(import.meta.env.VITE_PROCESSO_API),
}