# Acesso ao FTP do DATASUS no macOS (Finder)

Esta documentação descreve como acessar o **FTP público do DATASUS** utilizando o **Finder do macOS**, sem necessidade de instalação de ferramentas adicionais.

O método é indicado para:
- Download manual de arquivos do SIA/SUS
- Primeiros testes
- Validação de dados

---

## Pré-requisitos

- macOS
- Conexão com a internet
- Permissão de acesso ao Finder

Nenhuma credencial especial é necessária, pois o FTP é público.

---

## Passo a passo

### 1. Abrir o Finder
Abra qualquer pasta para garantir que o Finder esteja ativo.

---

### 2. Conectar a um servidor FTP
No menu superior do macOS, clique em:

Ir → Conectar ao Servidor…

Ou utilize o atalho de teclado:

⌘ + K


---

### 3. Informar o endereço do FTP
No campo **Endereço do Servidor**, informe:

ftp://ftp.datasus.gov.br


Clique em **Conectar**.

---

### 4. Autenticação
Quando o sistema solicitar usuário e senha:

- **Usuário:** `anonymous`
- **Senha:** deixe em branco ou informe seu e-mail

Clique em **Conectar**.

---

### 5. Navegar até os dados do SIA/SUS
Após a conexão, navegue pelas pastas seguindo o caminho abaixo:

dissemin/
└── publicos/
└── SIASUS/
└── 200801_/
└── Dados/


Esta pasta contém os arquivos oficiais do **Sistema de Informações Ambulatoriais do SUS (SIA/SUS)**.

---

### 6. Identificação dos arquivos
Os arquivos possuem extensão **.DBF** e seguem o padrão:

PAYYYYMM.dbf


Exemplo:
- `PA202501.dbf` → Janeiro de 2025
- `PA202502.dbf` → Fevereiro de 2025

Onde:
- **PA** = Produção Ambulatorial
- **YYYYMM** = Ano e mês da competência

---

### 7. Download dos arquivos
Para baixar:
- Selecione os arquivos desejados
- Arraste para uma pasta local no seu Mac

O download será iniciado automaticamente.

---

## Observações importantes

- Os arquivos estão no formato **DBF**, padrão histórico do DATASUS
- Para uso em sistemas modernos, é recomendado:
  - Converter DBF → CSV
  - Importar para banco de dados (ex: PostgreSQL)
- Os dados são **públicos e anonimizados**
- Não contêm CPF, CNS ou identificação de pacientes

---

## Próximos passos recomendados

Após o download, recomenda-se:

1. Converter DBF para CSV
2. Normalizar os dados
3. Cruzar com:
   - SIGTAP (procedimentos)
   - CNES (estabelecimentos)
   - IBGE (território)
4. Criar dashboards e relatórios analíticos

---

## Referência oficial

FTP DATASUS:

ftp://ftp.datasus.gov.br


Sistema:
- **SIA/SUS – Sistema de Informações Ambulatoriais do SUS**

---

## Licença dos dados

Os dados disponibilizados pelo DATASUS são públicos e podem ser utilizados para:
- Pesquisa
- Análise
- Desenvolvimento de sistemas
- Relatórios e BI

Desde que respeitada a legislação vigente e a LGPD.
