--Script DDL Projeto SabIA

--Excluir tabelas caso elas já existam
DROP TABLE IF EXISTS macrorregiao CASCADE;
DROP TABLE IF EXISTS estado CASCADE;
DROP TABLE IF EXISTS mesorregiao CASCADE;
DROP TABLE IF EXISTS microrregiao CASCADE;
DROP TABLE IF EXISTS cidade CASCADE;
DROP TABLE IF EXISTS pais CASCADE;
DROP TABLE IF EXISTS endereco CASCADE;
DROP TABLE IF EXISTS rg CASCADE;
DROP TABLE IF EXISTS permissao CASCADE;
DROP TABLE IF EXISTS perfil CASCADE;
DROP TABLE IF EXISTS perfil_permissao CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS usuario_perfil CASCADE;
DROP TABLE IF EXISTS pessoa CASCADE;
DROP TABLE IF EXISTS tipo_contato CASCADE;
DROP TABLE IF EXISTS contato_pessoa CASCADE;
DROP TABLE IF EXISTS sistema_ensino CASCADE;
DROP TABLE IF EXISTS tipo_instituicao CASCADE;
DROP TABLE IF EXISTS instituicao CASCADE;
DROP TABLE IF EXISTS contato_instituicao CASCADE;
DROP TABLE IF EXISTS titulacao CASCADE;
DROP TABLE IF EXISTS cargo CASCADE;
DROP TABLE IF EXISTS tipo_status_avaliador CASCADE;
DROP TABLE IF EXISTS status_avaliador CASCADE;
DROP TABLE IF EXISTS eixo_tecnologico CASCADE;
DROP TABLE IF EXISTS avaliador CASCADE;
DROP TABLE IF EXISTS avaliador_eixo CASCADE;
DROP TABLE IF EXISTS projeto CASCADE;
DROP TABLE IF EXISTS semana_avaliacao CASCADE;
DROP TABLE IF EXISTS disponibilidade CASCADE;
DROP TABLE IF EXISTS avaliacao CASCADE;
DROP TABLE IF EXISTS equipe CASCADE;
DROP TABLE IF EXISTS integrante CASCADE;
DROP TABLE IF EXISTS confirmacao_avaliador CASCADE;
DROP TABLE IF EXISTS viagem CASCADE;
DROP TABLE IF EXISTS atividade CASCADE;

--Criando as tabelas do banco

--Tabelas com os dados do IBGE
CREATE TABLE macrorregiao(
    id_macrorregiao                 SERIAL      NOT NULL,
    nome_macrorregiao               TEXT        NOT NULL,
    cod_ibge_macrorregiao           INTEGER     NOT NULL UNIQUE,

    PRIMARY KEY(id_macrorregiao)
);

CREATE TABLE estado(
    id_estado                       SERIAL      NOT NULL,
    nome_estado                     TEXT        NOT NULL,
    sigla_estado                    CHAR(2)     NOT NULL,
    cod_ibge_estado                 INTEGER     NOT NULL UNIQUE,
    cod_macrorregiao                INTEGER     NOT NULL,

    PRIMARY KEY(id_estado),
    FOREIGN KEY(cod_macrorregiao)               REFERENCES  macrorregiao(id_macrorregiao)
);

CREATE TABLE mesorregiao(
    id_mesorregiao                  SERIAL      NOT NULL,
    nome_mesorregiao                TEXT        NOT NULL,
    sigla_mesorregiao               CHAR(2)     NOT NULL,
    cod_ibge_mesorregiao            INTEGER     NOT NULL UNIQUE,
    cod_estado                      INTEGER     NOT NULL,

    PRIMARY KEY(id_mesorregiao),
    FOREIGN KEY(cod_estado)                     REFERENCES  estado(id_estado)
);

CREATE TABLE microrregiao(
    id_microrregiao                 SERIAL      NOT NULL,
    nome_microrregiao               TEXT        NOT NULL,
    cod_ibge_microrregiao           INTEGER     NOT NULL UNIQUE,
    cod_mesorregiao                 INTEGER     NOT NULL,

    PRIMARY KEY(id_microrregiao),
    FOREIGN KEY(cod_mesorregiao)                REFERENCES  mesorregiao(id_mesorregiao)
);

CREATE TABLE cidade(
    id_cidade                       SERIAL      NOT NULL,
    nome_cidade                     TEXT        NOT NULL,
    cod_ibge_cidade                 INTEGER     NOT NULL UNIQUE,
    cod_microrregiao                INTEGER     NOT NULL,

    PRIMARY KEY(id_cidade),
    FOREIGN KEY(cod_microrregiao)               REFERENCES  microrregiao(id_microrregiao)
);

CREATE TABLE pais(
    id_pais                         SERIAL      NOT NULL,
    nome_pais                       TEXT        NOT NULL,
    sigla_pais                      CHAR(2)     NOT NULL,
    cod_ibge_pais                   INTEGER     NOT NULL UNIQUE,

    PRIMARY KEY(id_pais)
);

--Tabelas com os dados pessoais
CREATE TABLE endereco(
    id_endereco                     SERIAL      NOT NULL,
    logradoudo                      TEXT        NOT NULL,
    numero_endereco                 TEXT        NOT NULL,
    complemento                     TEXT,
    bairro                          TEXT,
    ponto_referencia                TEXT,
    tipo_endereco                   TEXT,

    PRIMARY KEY(id_endereco)
);

CREATE TABLE rg(
    id_rg                           SERIAL      NOT NULL,
    numero_rg                       TEXT        NOT NULL,
    orgao_emissor                   TEXT        NOT NULL,
    data_emissao                    DATE        NOT NULL,
    cod_estado                      INTEGER     NOT NULL,

    PRIMARY KEY(id_rg),
    FOREIGN KEY(cod_estado)                     REFERENCES estado(id_estado)
);

CREATE TABLE permissao(
    id_permissao                    SERIAL      NOT NULL,
    nome_permissao                  TEXT        NOT NULL,

    PRIMARY KEY(id_permissao)
);

CREATE TABLE perfil(
    id_perfil                       SERIAL      NOT NULL,
    nome_perfil                     TEXT        NOT NULL,

    PRIMARY KEY(id_perfil)
);

CREATE TABLE pessoa(
    id_pessoa                       SERIAL      NOT NULL,
    nome_primeiro_pessoa            TEXT        NOT NULL,
    nome_meio_pessoa                TEXT,
    nome_ultimo_pessoa              TEXT        NOT NULL,
    cpf                             TEXT        UNIQUE,
    sexo                            TEXT        NOT NULL,
    data_nascimento                 DATE        NOT NULL,
    passaporte                      TEXT        UNIQUE,
    curriculo_lattes                TEXT        NOT NULL,
    naturalidade                    INTEGER,
    nacionalidade                   INTEGER     NOT NULL,
    cod_endereco                    INTEGER     NOT NULL,
    cod_rg                          INTEGER,
    cod_usuario                     INTEGER,

    PRIMARY KEY(id_pessoa),
    FOREIGN KEY(naturalidade)                   REFERENCES cidade(id_cidade),
    FOREIGN KEY(nacionalidade)                  REFERENCES pais(id_pais),
    FOREIGN KEY(cod_rg)                         REFERENCES rg(id_rg)
);

CREATE TABLE perfil_permissao(
    cod_perfil                      INTEGER     NOT NULL,
    cod_permissao                   INTEGER     NOT NULL,

    CONSTRAINT id_perfil_permissao              PRIMARY KEY(cod_perfil, cod_permissao),
    FOREIGN KEY(cod_perfil)                     REFERENCES perfil(id_perfil),
    FOREIGN KEY(cod_permissao)                  REFERENCES permissao(id_permissao)
);

CREATE TABLE usuario(
    id_usuario                      SERIAL      NOT NULL,
    login                           TEXT        NOT NULL UNIQUE,
    senha                           TEXT        NOT NULL,
    status                          TEXT        NOT NULL,
    cod_pessoa                      INTEGER 	NOT NULL,

    PRIMARY KEY(id_usuario),
    FOREIGN KEY(cod_pessoa)                     REFERENCES pessoa(id_pessoa)
);

CREATE TABLE usuario_perfil(
    cod_usuario                     INTEGER     NOT NULL,
    cod_perfil                      INTEGER     NOT NULL,

    CONSTRAINT id_usuario_perfil                PRIMARY KEY(cod_usuario, cod_perfil),
    FOREIGN KEY(cod_usuario)                    REFERENCES usuario(id_usuario),
    FOREIGN KEY(cod_perfil)                     REFERENCES perfil(id_perfil)
);

CREATE TABLE tipo_contato(
    id_tipo_contato                 SERIAL      NOT NULL,
    nome_tipo_contato               TEXT        NOT NULL,
    expressao_regular               TEXT        NOT NULL,

    PRIMARY KEY(id_tipo_contato)
);

CREATE TABLE contato_pessoa(
    id_contato_pessoa               SERIAL      NOT NULL,
    valor_contato_pessoa            TEXT        NOT NULL,
    cod_tipo_contato                INTEGER     NOT NULL,
    cod_pessoa                      INTEGER     NOT NULL,

    PRIMARY KEY(id_contato_pessoa),
    FOREIGN KEY(cod_tipo_contato)               REFERENCES tipo_contato(id_tipo_contato),
    FOREIGN KEY(cod_pessoa)                     REFERENCES pessoa(id_pessoa)
);

--Tabelas com os dados das instituições
CREATE TABLE sistema_ensino(
    id_sistema_ensino               SERIAL      NOT NULL,
    nome_sistema_ensino             TEXT        NOT NULL,

    PRIMARY KEY(id_sistema_ensino)
);

CREATE TABLE tipo_instituicao(
    id_tipo_instituicao             SERIAL      NOT NULL,
    nome_tipo_instituicao           TEXT        NOT NULL,

    PRIMARY KEY(id_tipo_instituicao)
);

CREATE TABLE instituicao(
    id_instituicao                  SERIAL      NOT NULL,
    nome_instituicao                TEXT        NOT NULL,
    cod_sistec_instituicao          INTEGER     NOT NULL UNIQUE,
    cod_inep_instituicao            INTEGER     NOT NULL UNIQUE,
    cod_sistema_ensino              INTEGER     NOT NULL,
    cod_tipo_instituicao            INTEGER     NOT NULL,
    cod_endereco                    INTEGER     NOT NULL,

    PRIMARY KEY(id_instituicao),
    FOREIGN KEY(cod_sistema_ensino)             REFERENCES sistema_ensino(id_sistema_ensino),
    FOREIGN KEY(cod_tipo_instituicao)           REFERENCES tipo_instituicao(id_tipo_instituicao),
    FOREIGN KEY(cod_endereco)                   REFERENCES endereco(id_endereco)
);

CREATE TABLE contato_instituicao(
    id_contato_instituicao          SERIAL      NOT NULL,
    valor_contato_instituicao       TEXT        NOT NULL,
    cod_tipo_contato                INTEGER     NOT NULL,
    cod_instituicao                 INTEGER     NOT NULL,

    PRIMARY KEY(id_contato_instituicao),
    FOREIGN KEY(cod_tipo_contato)               REFERENCES tipo_contato(id_tipo_contato),
    FOREIGN KEY(cod_instituicao)                REFERENCES instituicao(id_instituicao)
);

--Tabelas com os dados dos avaliadores
CREATE TABLE titulacao(
    id_titulacao                    SERIAL      NOT NULL,
    nome_titulacao                  TEXT        NOT NULL,

    PRIMARY KEY(id_titulacao)
);

CREATE TABLE cargo(
    id_cargo                        SERIAL      NOT NULL,
    nome_cargo                      TEXT        NOT NULL,

    PRIMARY KEY(id_cargo)
);

CREATE TABLE tipo_status_avaliador(
    id_tipo_status_avaliador        SERIAL      NOT NULL,
    nome_tipo_status_avaliador      TEXT        NOT NULL,

    PRIMARY KEY(id_tipo_status_avaliador)
);

CREATE TABLE status_avaliador(
    id_status_avaliador             SERIAL      NOT NULL,
    justificativa_status_avaliador  TEXT        NOT NULL,
    data_status_avaliador           DATE        NOT NULL,
    cod_tipo_status_avaliador       INTEGER     NOT NULL,

    PRIMARY KEY(id_status_avaliador),
    FOREIGN KEY(cod_tipo_status_avaliador)      REFERENCES tipo_status_avaliador(id_tipo_status_avaliador)
);

CREATE TABLE eixo_tecnologico(
    id_eixo_tecnologico             SERIAL      NOT NULL,
    nome_eixo_tecnologico           TEXT        NOT NULL,

    PRIMARY KEY(id_eixo_tecnologico)
);

CREATE TABLE avaliador(
    id_avaliador                    SERIAL      NOT NULL,
    experiencia_ensino              INTEGER     NOT NULL,
    siape_avaliador                 TEXT        NOT NULL,
    cod_pessoa                      INTEGER     NOT NULL,
    cod_instituicao                 INTEGER     NOT NULL,
    cod_cargo                       INTEGER     NOT NULL,
    cod_status_avaliador            INTEGER     NOT NULL,

    PRIMARY KEY(id_avaliador),
    FOREIGN KEY(cod_pessoa)                     REFERENCES pessoa(id_pessoa),
    FOREIGN KEY(cod_instituicao)                REFERENCES instituicao(id_instituicao),
    FOREIGN KEY(cod_cargo)                      REFERENCES cargo(id_cargo),
    FOREIGN KEY(cod_status_avaliador)           REFERENCES status_avaliador(id_status_avaliador)
);

CREATE TABLE avaliador_eixo(
    cod_avaliador                   INTEGER     NOT NULL,
    cod_eixo_tecnologico            INTEGER     NOT NULL,

    CONSTRAINT id_avaliador_eixo                PRIMARY KEY(cod_avaliador, cod_eixo_tecnologico),
    FOREIGN KEY(cod_avaliador)                  REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_eixo_tecnologico)           REFERENCES eixo_tecnologico(id_eixo_tecnologico)
);

--Tabelas com os dados das avaliações
CREATE TABLE projeto(
    id_projeto                      SERIAL      NOT NULL,
    nome_projeto                    TEXT        NOT NULL,

    PRIMARY KEY(id_projeto)
);

CREATE TABLE semana_avaliacao(
    id_semana_avaliacao             SERIAL      NOT NULL,
    numero_semana_avaliacao         INTEGER     NOT NULL UNIQUE,
    data_inicio_semana_avalicao     DATE        NOT NULL,
    data_fim_semana_avalicao        DATE        NOT NULL,

    PRIMARY KEY(id_semana_avaliacao)
);

CREATE TABLE disponibilidade(
    id_disponibilidade              SERIAL      NOT NULL,
    data_disponibilidade            DATE        NOT NULL,
    cod_semana_avaliacao            INTEGER     NOT NULL,

    PRIMARY KEY(id_disponibilidade),
    FOREIGN KEY(cod_semana_avaliacao)           REFERENCES semana_avaliacao(id_semana_avaliacao)
);

CREATE TABLE avaliacao(
    id_avaliacao                    SERIAL      NOT NULL,
    cod_projeto                     INTEGER     NOT NULL,
    cod_semana_avaliacao            INTEGER     NOT NULL,
    cod_instituicao                 INTEGER     NOT NULL,
    cod_equipe                      INTEGER,

    PRIMARY KEY(id_avaliacao),
    FOREIGN KEY(cod_projeto)                    REFERENCES projeto(id_projeto),
    FOREIGN KEY(cod_semana_avaliacao)           REFERENCES semana_avaliacao(id_semana_avaliacao),
    FOREIGN KEY(cod_instituicao)                REFERENCES instituicao(id_instituicao)
);

CREATE TABLE equipe(
    id_equipe                       SERIAL      NOT NULL,
    cod_avaliacao                   INTEGER     NOT NULL,

    PRIMARY KEY(id_equipe),
    FOREIGN KEY(cod_avaliacao)                  REFERENCES avaliacao(id_avaliacao)
);

CREATE TABLE integrante(
    cod_avaliador                   INTEGER     NOT NULL,
    cod_equipe                      INTEGER     NOT NULL,

    CONSTRAINT id_integrante                    PRIMARY KEY(cod_avaliador, cod_equipe),
    FOREIGN KEY(cod_avaliador)                  REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_equipe)                     REFERENCES equipe(id_equipe)
);

--Tabelas com os dados de confirmação e viajem
CREATE TABLE confirmacao_avaliador(
    id_confirmacao_avaliador        SERIAL      NOT NULL,
    status_confirmacao_avaliador    TEXT        NOT NULL,
    data_confirmacao_avaliador      DATE        NOT NULL,
    cod_avaliador                   INTEGER     NOT NULL,

    PRIMARY KEY(id_confirmacao_avaliador),
    FOREIGN KEY(cod_avaliador)                  REFERENCES avaliador(id_avaliador)
);

CREATE TABLE viagem(
    id_viagem                       SERIAL      NOT NULL,
    percurso                        TEXT        NOT NULL,
    data_saida_viagem               DATE        NOT NULL,
    data_chegada_viagem             DATE        NOT NULL,
    cod_confirmacao_avaliador       INTEGER     NOT NULL,

    PRIMARY KEY(id_viagem),
    FOREIGN KEY(cod_confirmacao_avaliador)      REFERENCES confirmacao_avaliador(id_confirmacao_avaliador)
);

CREATE TABLE atividade(
    id_atividade                    SERIAL      NOT NULL,
    data_atividade                  DATE        NOT NULL,
    descricao_atividade             TEXT        NOT NULL,
    cod_viagem                      INTEGER     NOT NULL,

    PRIMARY KEY(id_atividade),
    FOREIGN KEY(cod_viagem)                         REFERENCES viagem(id_viagem)
);

--Adicionando pessoa a usuario
ALTER TABLE pessoa
ADD FOREIGN KEY(cod_usuario)                    REFERENCES usuario(id_usuario);


--Adicionando equipe a avalicao
ALTER TABLE avaliacao
ADD FOREIGN KEY(cod_equipe)                     REFERENCES equipe(id_equipe);
