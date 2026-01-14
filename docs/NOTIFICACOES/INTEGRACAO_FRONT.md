# Integração Frontend — Módulo de Notificações Brevo

Este documento fornece um **guia completo** para integrar o módulo de notificações no frontend do UPSaúde.

---

## Índice

1. [Visão Geral](#visão-geral)
2. [Endpoints Disponíveis](#endpoints-disponíveis)
3. [Exemplos de Integração](#exemplos-de-integração)
4. [Componentes React/Vue](#componentes-reactvue)
5. [Tratamento de Erros](#tratamento-de-erros)
6. [Boas Práticas](#boas-práticas)

---

## Visão Geral

O frontend pode interagir com o módulo de notificações de duas formas:

1. **Consulta de Notificações**: Visualizar notificações enviadas para o usuário/paciente
2. **Envio Manual**: Criar notificações manualmente (casos específicos)

**Importante**: A maioria das notificações é enviada **automaticamente** pelo backend. O frontend geralmente apenas **consulta** o status e histórico.

---

## Endpoints Disponíveis

### Base URL

```javascript
const API_BASE_URL = 'http://localhost:8080/api/v1';
```

### Autenticação

Todos os endpoints requerem token JWT:

```javascript
const headers = {
  'Authorization': `Bearer ${token}`,
  'Content-Type': 'application/json'
};
```

---

## Exemplos de Integração

### 1. Service de Notificações (TypeScript)

```typescript
// services/notificacaoService.ts

import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api/v1';

export interface Notificacao {
  id: string;
  tipoNotificacao: string;
  canal: string;
  destinatario: string;
  assunto: string;
  mensagem: string;
  statusEnvio: 'PENDENTE' | 'ENVIADO' | 'FALHA';
  dataEnvio?: string;
  dataEnvioPrevista?: string;
  idExterno?: string;
  erroEnvio?: string;
  createdAt: string;
}

export interface NotificacaoListResponse {
  content: Notificacao[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

class NotificacaoService {
  private getHeaders() {
    const token = localStorage.getItem('token');
    return {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
  }

  /**
   * Listar notificações com filtros
   */
  async listar(filtros: {
    pacienteId?: string;
    statusEnvio?: string;
    tipoNotificacao?: string;
    page?: number;
    size?: number;
  }): Promise<NotificacaoListResponse> {
    const params = new URLSearchParams();
    
    if (filtros.pacienteId) params.append('pacienteId', filtros.pacienteId);
    if (filtros.statusEnvio) params.append('statusEnvio', filtros.statusEnvio);
    if (filtros.tipoNotificacao) params.append('tipoNotificacao', filtros.tipoNotificacao);
    if (filtros.page !== undefined) params.append('page', filtros.page.toString());
    if (filtros.size !== undefined) params.append('size', filtros.size.toString());

    const response = await axios.get(
      `${API_BASE_URL}/notificacoes?${params.toString()}`,
      { headers: this.getHeaders() }
    );
    
    return response.data;
  }

  /**
   * Obter notificação por ID
   */
  async obterPorId(id: string): Promise<Notificacao> {
    const response = await axios.get(
      `${API_BASE_URL}/notificacoes/${id}`,
      { headers: this.getHeaders() }
    );
    
    return response.data;
  }

  /**
   * Criar notificação manualmente
   */
  async criar(notificacao: {
    tipoNotificacao: string;
    canal: string;
    destinatario: string;
    assunto: string;
    mensagem: string;
    pacienteId?: string;
    parametrosJson?: string;
  }): Promise<Notificacao> {
    const response = await axios.post(
      `${API_BASE_URL}/notificacoes`,
      notificacao,
      { headers: this.getHeaders() }
    );
    
    return response.data;
  }

  /**
   * Listar templates de notificação
   */
  async listarTemplates(filtros: {
    tipoNotificacao?: string;
    canal?: string;
    page?: number;
    size?: number;
  }) {
    const params = new URLSearchParams();
    
    if (filtros.tipoNotificacao) params.append('tipoNotificacao', filtros.tipoNotificacao);
    if (filtros.canal) params.append('canal', filtros.canal);
    if (filtros.page !== undefined) params.append('page', filtros.page.toString());
    if (filtros.size !== undefined) params.append('size', filtros.size.toString());

    const response = await axios.get(
      `${API_BASE_URL}/templates-notificacao?${params.toString()}`,
      { headers: this.getHeaders() }
    );
    
    return response.data;
  }
}

export default new NotificacaoService();
```

---

### 2. Hook React para Notificações

```typescript
// hooks/useNotificacoes.ts

import { useState, useEffect } from 'react';
import notificacaoService, { Notificacao, NotificacaoListResponse } from '../services/notificacaoService';

interface UseNotificacoesOptions {
  pacienteId?: string;
  statusEnvio?: string;
  tipoNotificacao?: string;
  autoRefresh?: boolean;
  refreshInterval?: number; // em milissegundos
}

export function useNotificacoes(options: UseNotificacoesOptions = {}) {
  const [notificacoes, setNotificacoes] = useState<Notificacao[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const [pagination, setPagination] = useState({
    page: 0,
    size: 20,
    totalElements: 0,
    totalPages: 0
  });

  const carregarNotificacoes = async (page: number = 0) => {
    try {
      setLoading(true);
      setError(null);
      
      const response: NotificacaoListResponse = await notificacaoService.listar({
        pacienteId: options.pacienteId,
        statusEnvio: options.statusEnvio,
        tipoNotificacao: options.tipoNotificacao,
        page,
        size: pagination.size
      });

      setNotificacoes(response.content);
      setPagination({
        page: response.number,
        size: response.size,
        totalElements: response.totalElements,
        totalPages: response.totalPages
      });
    } catch (err) {
      setError(err as Error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarNotificacoes();

    if (options.autoRefresh && options.refreshInterval) {
      const interval = setInterval(() => {
        carregarNotificacoes(pagination.page);
      }, options.refreshInterval);

      return () => clearInterval(interval);
    }
  }, [options.pacienteId, options.statusEnvio, options.tipoNotificacao]);

  return {
    notificacoes,
    loading,
    error,
    pagination,
    recarregar: () => carregarNotificacoes(pagination.page),
    proximaPagina: () => carregarNotificacoes(pagination.page + 1),
    paginaAnterior: () => carregarNotificacoes(pagination.page - 1)
  };
}
```

---

### 3. Componente React de Listagem

```tsx
// components/NotificacoesList.tsx

import React from 'react';
import { useNotificacoes } from '../hooks/useNotificacoes';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

interface NotificacoesListProps {
  pacienteId?: string;
  statusEnvio?: 'PENDENTE' | 'ENVIADO' | 'FALHA';
}

export const NotificacoesList: React.FC<NotificacoesListProps> = ({
  pacienteId,
  statusEnvio
}) => {
  const { notificacoes, loading, error, pagination, proximaPagina, paginaAnterior } = useNotificacoes({
    pacienteId,
    statusEnvio,
    autoRefresh: true,
    refreshInterval: 30000 // 30 segundos
  });

  const getStatusBadge = (status: string) => {
    const badges = {
      PENDENTE: { color: 'bg-yellow-100 text-yellow-800', label: 'Pendente' },
      ENVIADO: { color: 'bg-green-100 text-green-800', label: 'Enviado' },
      FALHA: { color: 'bg-red-100 text-red-800', label: 'Falha' }
    };
    
    const badge = badges[status as keyof typeof badges] || badges.PENDENTE;
    
    return (
      <span className={`px-2 py-1 rounded text-xs font-medium ${badge.color}`}>
        {badge.label}
      </span>
    );
  };

  const getTipoLabel = (tipo: string) => {
    const tipos: Record<string, string> = {
      USUARIO_CRIADO: 'Usuário Criado',
      SENHA_ALTERADA: 'Senha Alterada',
      DADOS_PESSOAIS_ATUALIZADOS: 'Dados Atualizados',
      AGENDAMENTO_CONFIRMADO: 'Agendamento Confirmado',
      AGENDAMENTO_CANCELADO: 'Agendamento Cancelado',
      LEMBRETE_24H: 'Lembrete 24h',
      LEMBRETE_1H: 'Lembrete 1h'
    };
    
    return tipos[tipo] || tipo;
  };

  if (loading) {
    return <div className="text-center py-8">Carregando notificações...</div>;
  }

  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 rounded p-4">
        <p className="text-red-800">Erro ao carregar notificações: {error.message}</p>
      </div>
    );
  }

  if (notificacoes.length === 0) {
    return (
      <div className="text-center py-8 text-gray-500">
        Nenhuma notificação encontrada.
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="bg-white shadow rounded-lg">
        <div className="px-4 py-3 border-b border-gray-200">
          <h3 className="text-lg font-medium text-gray-900">
            Notificações ({pagination.totalElements})
          </h3>
        </div>
        
        <div className="divide-y divide-gray-200">
          {notificacoes.map((notificacao) => (
            <div key={notificacao.id} className="px-4 py-4 hover:bg-gray-50">
              <div className="flex items-start justify-between">
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-1">
                    <span className="font-medium text-gray-900">
                      {getTipoLabel(notificacao.tipoNotificacao)}
                    </span>
                    {getStatusBadge(notificacao.statusEnvio)}
                  </div>
                  
                  <p className="text-sm text-gray-600 mb-1">
                    {notificacao.assunto || 'Sem assunto'}
                  </p>
                  
                  <p className="text-xs text-gray-500">
                    Para: {notificacao.destinatario}
                  </p>
                  
                  {notificacao.dataEnvio && (
                    <p className="text-xs text-gray-500 mt-1">
                      Enviado em: {format(new Date(notificacao.dataEnvio), "dd/MM/yyyy 'às' HH:mm", { locale: ptBR })}
                    </p>
                  )}
                  
                  {notificacao.erroEnvio && (
                    <p className="text-xs text-red-600 mt-1">
                      Erro: {notificacao.erroEnvio}
                    </p>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
        
        {pagination.totalPages > 1 && (
          <div className="px-4 py-3 border-t border-gray-200 flex items-center justify-between">
            <button
              onClick={paginaAnterior}
              disabled={pagination.page === 0}
              className="px-3 py-1 border rounded disabled:opacity-50"
            >
              Anterior
            </button>
            
            <span className="text-sm text-gray-600">
              Página {pagination.page + 1} de {pagination.totalPages}
            </span>
            
            <button
              onClick={proximaPagina}
              disabled={pagination.page >= pagination.totalPages - 1}
              className="px-3 py-1 border rounded disabled:opacity-50"
            >
              Próxima
            </button>
          </div>
        )}
      </div>
    </div>
  );
};
```

---

### 4. Componente de Envio Manual

```tsx
// components/EnviarNotificacao.tsx

import React, { useState } from 'react';
import notificacaoService from '../services/notificacaoService';

interface EnviarNotificacaoProps {
  pacienteId?: string;
  onSucesso?: () => void;
}

export const EnviarNotificacao: React.FC<EnviarNotificacaoProps> = ({
  pacienteId,
  onSucesso
}) => {
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    tipoNotificacao: 'USUARIO_CRIADO',
    canal: 'EMAIL',
    destinatario: '',
    assunto: '',
    mensagem: ''
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      setLoading(true);
      
      await notificacaoService.criar({
        ...formData,
        pacienteId
      });
      
      alert('Notificação criada com sucesso!');
      onSucesso?.();
      
      // Limpar formulário
      setFormData({
        tipoNotificacao: 'USUARIO_CRIADO',
        canal: 'EMAIL',
        destinatario: '',
        assunto: '',
        mensagem: ''
      });
    } catch (error: any) {
      alert(`Erro ao criar notificação: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Tipo de Notificação
        </label>
        <select
          value={formData.tipoNotificacao}
          onChange={(e) => setFormData({ ...formData, tipoNotificacao: e.target.value })}
          className="w-full border rounded px-3 py-2"
          required
        >
          <option value="USUARIO_CRIADO">Usuário Criado</option>
          <option value="SENHA_ALTERADA">Senha Alterada</option>
          <option value="DADOS_PESSOAIS_ATUALIZADOS">Dados Atualizados</option>
          <option value="AGENDAMENTO_CONFIRMADO">Agendamento Confirmado</option>
        </select>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Destinatário (E-mail)
        </label>
        <input
          type="email"
          value={formData.destinatario}
          onChange={(e) => setFormData({ ...formData, destinatario: e.target.value })}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Assunto
        </label>
        <input
          type="text"
          value={formData.assunto}
          onChange={(e) => setFormData({ ...formData, assunto: e.target.value })}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Mensagem
        </label>
        <textarea
          value={formData.mensagem}
          onChange={(e) => setFormData({ ...formData, mensagem: e.target.value })}
          className="w-full border rounded px-3 py-2"
          rows={4}
          required
        />
      </div>

      <button
        type="submit"
        disabled={loading}
        className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 disabled:opacity-50"
      >
        {loading ? 'Enviando...' : 'Criar Notificação'}
      </button>
    </form>
  );
};
```

---

## Tratamento de Erros

### Erros Comuns

```typescript
// utils/errorHandler.ts

export function handleNotificacaoError(error: any): string {
  if (error.response) {
    switch (error.response.status) {
      case 400:
        return 'Dados inválidos. Verifique os campos preenchidos.';
      case 401:
        return 'Não autenticado. Faça login novamente.';
      case 403:
        return 'Acesso negado. Você não tem permissão para esta ação.';
      case 404:
        return 'Notificação não encontrada.';
      case 500:
        return 'Erro interno do servidor. Tente novamente mais tarde.';
      default:
        return `Erro ${error.response.status}: ${error.response.data?.message || 'Erro desconhecido'}`;
    }
  }
  
  if (error.request) {
    return 'Erro de conexão. Verifique sua internet.';
  }
  
  return 'Erro inesperado. Tente novamente.';
}
```

---

## Boas Práticas

### 1. Cache e Refresh

- Use cache para reduzir chamadas à API
- Implemente refresh automático para notificações pendentes
- Use debounce para evitar múltiplas requisições

### 2. Feedback ao Usuário

- Mostre loading states durante carregamento
- Exiba mensagens de sucesso/erro claras
- Use toasts/notificações para feedback

### 3. Performance

- Implemente paginação para listas grandes
- Use virtual scrolling se necessário
- Lazy load de detalhes de notificação

### 4. Acessibilidade

- Use labels descritivos
- Implemente navegação por teclado
- Adicione ARIA labels onde necessário

---

## Exemplo Completo de Página

```tsx
// pages/NotificacoesPage.tsx

import React, { useState } from 'react';
import { NotificacoesList } from '../components/NotificacoesList';
import { EnviarNotificacao } from '../components/EnviarNotificacao';

export const NotificacoesPage: React.FC = () => {
  const [filtroStatus, setFiltroStatus] = useState<string>('');
  const [mostrarFormulario, setMostrarFormulario] = useState(false);

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-6 flex items-center justify-between">
        <h1 className="text-2xl font-bold">Notificações</h1>
        
        <div className="flex gap-2">
          <select
            value={filtroStatus}
            onChange={(e) => setFiltroStatus(e.target.value)}
            className="border rounded px-3 py-2"
          >
            <option value="">Todos os status</option>
            <option value="PENDENTE">Pendentes</option>
            <option value="ENVIADO">Enviadas</option>
            <option value="FALHA">Falhas</option>
          </select>
          
          <button
            onClick={() => setMostrarFormulario(!mostrarFormulario)}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            {mostrarFormulario ? 'Cancelar' : 'Nova Notificação'}
          </button>
        </div>
      </div>

      {mostrarFormulario && (
        <div className="mb-6 bg-white p-6 rounded shadow">
          <h2 className="text-xl font-semibold mb-4">Enviar Notificação Manual</h2>
          <EnviarNotificacao
            onSucesso={() => {
              setMostrarFormulario(false);
              // Recarregar lista
            }}
          />
        </div>
      )}

      <NotificacoesList statusEnvio={filtroStatus || undefined} />
    </div>
  );
};
```

---

Para mais detalhes, consulte:
- [ENDPOINTS.md](./ENDPOINTS.md) - Documentação completa de endpoints
- [EXEMPLOS.md](./EXEMPLOS.md) - Exemplos práticos de uso
