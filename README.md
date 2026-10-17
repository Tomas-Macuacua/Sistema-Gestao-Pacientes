# Sistema de Gestão de Pacientes

Sistema que gere pacientes, médicos, consultas e utilizadores de uma clínica.

## Tecnologias
- Java 17, Maven
- Hibernate 6 (JPA) e MySQL
- NetBeans

## Estrutura (MVC)
- `model`: entidades (`Pessoa` abstracta, `Paciente`, `Medico`, `Utilizador`, `Consulta`)
- `dao`: acesso à base de dados (DAO genérico + DAOs específicos)
- `controller`: validação e regras de negócio
- `exception`: excepções próprias
- `util`: `HibernateUtil` e `Validador`
- `enums`: `Sexo`, `Perfil`, `EstadoConsulta`

## Conceitos aplicados
- **Herança**: `Pessoa` → `Paciente` e `Medico`
- **Polimorfismo**: `getTipo()` e `getResumo()`
- **Interface**: `Descritivel`, `IGenericDAO`
- **CRUD** completo para todas as entidades
- **Regras de negócio**: número de processo único, conflito de horário do médico, ciclo de vida da consulta

## Como executar
1. Ligar o MySQL e criar a base de dados:
   `CREATE DATABASE sistema_pacientes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
2. Ajustar o utilizador e a palavra-passe em `src/main/resources/hibernate.cfg.xml`.
3. Abrir no NetBeans e executar `com.pacientes.app.Main`.
   As tabelas são criadas automaticamente.
