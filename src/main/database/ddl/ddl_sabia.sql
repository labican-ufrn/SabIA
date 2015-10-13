--Script DDL Projeto SabIA.

--Excluir tabelas caso elas já existam.
--Tabelas que armazenam dados do IBGE referente a localização.
DROP TABLE IF EXISTS macrorregiao CASCADE;
DROP TABLE IF EXISTS estado CASCADE;
DROP TABLE IF EXISTS mesorregiao CASCADE;
DROP TABLE IF EXISTS microrregiao CASCADE;
DROP TABLE IF EXISTS municipio CASCADE;
DROP TABLE IF EXISTS distrito CASCADE;
DROP TABLE IF EXISTS localidade CASCADE;
DROP TABLE IF EXISTS categoria CASCADE;

--Tabela de endereço utilizadas por pessoa e unidade ensino.
DROP TABLE IF EXISTS endereco CASCADE;

--Tabelas que armazenam dados pessoais dos usuários.
DROP TABLE IF EXISTS pais CASCADE;
DROP TABLE IF EXISTS rg CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS permissao CASCADE;
DROP TABLE IF EXISTS perfil CASCADE;
DROP TABLE IF EXISTS pessoa CASCADE;

--Tabelas que armazenam as unidades de ensino avaliadas e as unidades de ensino que os avaliadores trabalham.
DROP TABLE IF EXISTS rede_ensino CASCADE;
DROP TABLE IF EXISTS unidade_ensino CASCADE;

--Tabelas de contato utilizadas para pessoa e unidade de ensino.
DROP TABLE IF EXISTS tipo_contato CASCADE;
DROP TABLE IF EXISTS contato_pessoa CASCADE;
DROP TABLE IF EXISTS contato_unidade_ensino CASCADE;

--Tabelas que armazenam os dados dos avaliadores.
DROP TABLE IF EXISTS titulacao CASCADE;
DROP TABLE IF EXISTS cargo CASCADE;
DROP TABLE IF EXISTS nivel CASCADE;
DROP TABLE IF EXISTS tipo_status_avaliador CASCADE;
DROP TABLE IF EXISTS status_avaliador CASCADE;
DROP TABLE IF EXISTS eixo_tecnologico CASCADE;
DROP TABLE IF EXISTS avaliador CASCADE;
DROP TABLE IF EXISTS avaliador_eixo CASCADE;

--Tabelas que armazenam os dados das avaliações.
DROP TABLE IF EXISTS projeto CASCADE;
DROP TABLE IF EXISTS avaliador_projeto CASCADE;
DROP TABLE IF EXISTS semana_avaliacao CASCADE;
DROP TABLE IF EXISTS avaliador_disponibilidade CASCADE;
DROP TABLE IF EXISTS disponibilidade_semana CASCADE;
DROP TABLE IF EXISTS disponibilidade CASCADE;
DROP TABLE IF EXISTS avaliacao CASCADE;
DROP TABLE IF EXISTS equipe CASCADE;
DROP TABLE IF EXISTS integrante CASCADE;

--Tabelas que armazenam os dados de viagem e confirmação de viagem.
DROP TABLE IF EXISTS confirmacao_avaliador CASCADE;
DROP TABLE IF EXISTS viagem CASCADE;
DROP TABLE IF EXISTS atividade CASCADE;

--Tabelas que foram modificadas e não existem mais
DROP TABLE IF EXISTS cidade CASCADE; --Não existe mais.
DROP TABLE IF EXISTS sistema_ensino CASCADE; --Não existe mais
DROP TABLE IF EXISTS tipo_instituicao CASCADE; --Não existe mais
DROP TABLE IF EXISTS instituicao CASCADE; --Não existe mais
DROP TABLE IF EXISTS contato_instituicao CASCADE; --Não existe mais

--Criando as tabelas do banco.

-- *** Tabelas com os dados do IBGE. ***
CREATE TABLE macrorregiao( --Norte, Nordeste, Sul, Sudeste ou Centro-Oeste.
    id_macrorregiao                 SERIAL          NOT NULL,
    nome_macrorregiao               TEXT            NOT NULL,
    cod_mec_macrorregiao            INTEGER         NOT NULL UNIQUE, --Código utilizado pelo MEC para identificar as Regiões do país.

    PRIMARY KEY(id_macrorregiao) 
);

CREATE TABLE estado(
    id_estado                       SERIAL          NOT NULL,
    nome_estado                     TEXT            NOT NULL,
    sigla_estado                    CHAR(2)         NOT NULL UNIQUE,
    cod_macrorregiao                INTEGER         NOT NULL,

    PRIMARY KEY(id_estado),
    FOREIGN KEY(cod_macrorregiao)                   REFERENCES  macrorregiao(id_macrorregiao)
);

CREATE TABLE mesorregiao(
    id_mesorregiao                  SERIAL          NOT NULL,
    nome_mesorregiao                TEXT            NOT NULL,
    cod_estado                      INTEGER         NOT NULL,

    PRIMARY KEY(id_mesorregiao),
    FOREIGN KEY(cod_estado)                         REFERENCES  estado(id_estado)
);

CREATE TABLE microrregiao(
    id_microrregiao                 SERIAL          NOT NULL,
    nome_microrregiao               TEXT            NOT NULL,
    cod_mesorregiao                 INTEGER         NOT NULL,

    PRIMARY KEY(id_microrregiao),
    FOREIGN KEY(cod_mesorregiao)                    REFERENCES  mesorregiao(id_mesorregiao)
);

--Tabela utilizada também para obter a naturalidade de uma pessoa
CREATE TABLE municipio(
    id_municipio                    SERIAL          NOT NULL,
    nome_municipio                  TEXT            NOT NULL,
    geocod_municipio                INTEGER         NOT NULL UNIQUE, --Geocódigo do IBGE para o municípi\o.
    populacao                       INTEGER, --População estimada de acordo com as pesquisas do IBGE.
    ano_populacao                   INTEGER, --Ano em que a pesquisa do IBGE foi feita.
    cod_microrregiao                INTEGER         NOT NULL,

    PRIMARY KEY(id_municipio),
    FOREIGN KEY(cod_microrregiao)                   REFERENCES  microrregiao(id_microrregiao)
);

CREATE TABLE distrito(
    id_distrito                     SERIAL          NOT NULL,
    nome_distrito                   TEXT            NOT NULL,
    geocod_distrito                 INTEGER         NOT NULL UNIQUE, --Geocódigo do IBGE para o Distrito.
    cod_municipio                   INTEGER         NOT NULL,

    PRIMARY KEY(id_distrito),
    FOREIGN KEY(cod_municipio)                      REFERENCES municipio(id_municipio)
);

CREATE TABLE categoria( --Aldeia indígena, AUI, Cidade, Lugarejo, Núcleo, Povoado, Projeto de assentamento ou Vila.
    id_categoria                    SERIAL          NOT NULL,
    nome_categoria                  TEXT            NOT NULL,

    PRIMARY KEY(id_categoria)
);

CREATE TABLE localidade(
    id_localidade                   SERIAL          NOT NULL,
    nome_localidade                 TEXT            NOT NULL,
    tipo_localidade                 TEXT            NOT NULL, --Rural ou Urbano
    geocodigo                       INTEGER         NOT NULL UNIQUE, --Geocódigo completo da localidade (UF, Município, Distrito, Subdistrito, Setor).
    latitude                        DECIMAL(16,13),
    longitude                       DECIMAL(16,13),
    altitude                        DECIMAL(9,6),
    cod_categoria                   INTEGER         NOT NULL,
    cod_municipio                   INTEGER         NOT NULL,

    PRIMARY KEY(id_localidade),
    FOREIGN KEY(cod_categoria)                      REFERENCES categoria(id_categoria),
    FOREIGN KEY(cod_municipio)                      REFERENCES municipio(id_municipio)
);
-- *** Fim das tabelas com os dados do IBGE. ***

-- Tabela de endereço utilizada por pessoa e unidade de ensino.
CREATE TABLE endereco(
    id_endereco                     SERIAL          NOT NULL,
    logradouro                      TEXT            NOT NULL,
    numero_endereco                 TEXT            NOT NULL,
    complemento                     TEXT,
    bairro                          TEXT            NOT NULL,
    ponto_referencia                TEXT,
    tipo_endereco                   TEXT            NOT NULL,
    cod_localidade                  INTEGER         NOT NULL,

    PRIMARY KEY(id_endereco),
    FOREIGN KEY(cod_localidade)                     REFERENCES localidade(id_localidade)
);

-- *** Tabelas com os dados pessoais. ***
CREATE TABLE rg(
    id_rg                           SERIAL          NOT NULL,
    numero_rg                       TEXT            NOT NULL,
    orgao_emissor                   TEXT            NOT NULL,
    data_emissao                    DATE            NOT NULL,
    cod_estado                      INTEGER         NOT NULL,

    PRIMARY KEY(id_rg),
    FOREIGN KEY(cod_estado)                         REFERENCES estado(id_estado)
);

--Tabela utlizada somente para obter a nacionalidade de uma pessoa.
CREATE TABLE pais(
    id_pais                         SERIAL          NOT NULL,
    nome_pais                       TEXT            NOT NULL,
    sigla_pais                      CHAR(3)         NOT NULL UNIQUE,

    PRIMARY KEY(id_pais)
);

CREATE TABLE usuario(
    id_usuario                      SERIAL          NOT NULL,
    login                           TEXT            NOT NULL UNIQUE,
    senha                           TEXT            NOT NULL,
    ativado                         BOOLEAN         NOT NULL, --Necessário para utilizar o spring security.

    PRIMARY KEY(id_usuario)
);

CREATE TABLE permissao(
    id_permissao                    SERIAL          NOT NULL,
    nome_permissao                  TEXT            NOT NULL,

    PRIMARY KEY(id_permissao)
);

--Tabela para fazer o relacionamento entre usuario e permissao.
CREATE TABLE perfil( --Entidade fraca para relacionar as permissões que um usuário terá.
    cod_usuario                     INTEGER         NOT NULL,
    cod_permissao                   INTEGER         NOT NULL,

    CONSTRAINT id_perfil                            PRIMARY KEY(cod_usuario, cod_permissao),
    FOREIGN KEY(cod_usuario)                        REFERENCES usuario(id_usuario),
    FOREIGN KEY(cod_permissao)                      REFERENCES permissao(id_permissao)
);

CREATE TABLE pessoa(
    id_pessoa                       SERIAL          NOT NULL,
    nome_primeiro_pessoa            TEXT            NOT NULL,
    nome_meio_pessoa                TEXT,
    nome_ultimo_pessoa              TEXT            NOT NULL,
    cpf                             TEXT            UNIQUE,
    sexo                            CHAR(8)         NOT NULL,
    data_nascimento                 DATE            NOT NULL,
    passaporte                      TEXT            UNIQUE,
    curriculo_lattes                TEXT            NOT NULL,
    naturalidade                    INTEGER,                  --Informação obtida a partir da tabela município.                  
    nacionalidade                   INTEGER         NOT NULL, --Informação obtida a partit da tabela pais.
    cod_endereco                    INTEGER         NOT NULL,
    cod_rg                          INTEGER,
    cod_usuario                     INTEGER         NOT NULL,

    PRIMARY KEY(id_pessoa),
    FOREIGN KEY(naturalidade)                       REFERENCES municipio(id_municipio),
    FOREIGN KEY(nacionalidade)                      REFERENCES pais(id_pais),
    FOREIGN KEY(cod_endereco)                       REFERENCES endereco(id_endereco),
    FOREIGN KEY(cod_rg)                             REFERENCES rg(id_rg),
    FOREIGN KEY(cod_usuario)                        REFERENCES usuario(id_usuario)
);
-- *** Fim das tabelas com os dados pessoais. ***

-- *** Tabelas com os dados das unidades de ensino. ***
CREATE TABLE rede_ensino( --Federal, Estadual, Senai, Senac, Senar, Senat.
    id_rede_ensino                  SERIAL          NOT NULL,
    nome_rede_ensino                TEXT            NOT NULL,

    PRIMARY KEY(id_rede_ensino)
);

CREATE TABLE unidade_ensino(
    id_unidade_ensino               SERIAL          NOT NULL,
    nome_unidade_ensino             TEXT            NOT NULL,
    sigla                           TEXT,
    cod_sistec_unidade_ensino       INTEGER         NOT NULL UNIQUE,
    cod_inep_unidade_ensino         INTEGER         NOT NULL UNIQUE,
    categoria_administrativa        CHAR(7)         NOT NULL, -- Pública ou Privada.
    cod_rede_ensino                 INTEGER         NOT NULL,
    cod_endereco                    INTEGER         NOT NULL,

    PRIMARY KEY(id_unidade_ensino),
    FOREIGN KEY(cod_rede_ensino)                    REFERENCES rede_ensino(id_rede_ensino),
    FOREIGN KEY(cod_endereco)                       REFERENCES endereco(id_endereco)
);
-- *** Fim das tabelas com os dados das e. ***

-- *** Tabelas de contato utilizadas para pessoa e unidade ensino. ***
CREATE TABLE tipo_contato(
    id_tipo_contato                 SERIAL          NOT NULL,
    nome_tipo_contato               TEXT            NOT NULL,
    expressao_regular               TEXT,

    PRIMARY KEY(id_tipo_contato)
);

CREATE TABLE contato_pessoa(
    id_contato_pessoa               SERIAL          NOT NULL,
    valor_contato_pessoa            TEXT            NOT NULL,
    cod_tipo_contato                INTEGER         NOT NULL,
    cod_pessoa                      INTEGER         NOT NULL,

    PRIMARY KEY(id_contato_pessoa),
    FOREIGN KEY(cod_tipo_contato)                   REFERENCES tipo_contato(id_tipo_contato),
    FOREIGN KEY(cod_pessoa)                         REFERENCES pessoa(id_pessoa)
);

CREATE TABLE contato_unidade_ensino(
    id_contato_unidade_ensino       SERIAL          NOT NULL,
    valor_contato_unidade_ensino    TEXT            NOT NULL,
    descricao                       TEXT            NOT NULL,
    cod_tipo_contato                INTEGER         NOT NULL,
    cod_unidade_ensino              INTEGER         NOT NULL,

    PRIMARY KEY(id_contato_unidade_ensino),
    FOREIGN KEY(cod_tipo_contato)                   REFERENCES tipo_contato(id_tipo_contato),
    FOREIGN KEY(cod_unidade_ensino)                 REFERENCES unidade_ensino(id_unidade_ensino)
);
-- *** Fim das tabelas de contato utilizadas para pessoa e unidade ensino. ***

-- *** Tabelas com dados dos avaliadores. ***
CREATE TABLE titulacao( --Doutor, Mestre, ...
    id_titulacao                    SERIAL          NOT NULL,
    nome_titulacao                  TEXT            NOT NULL,

    PRIMARY KEY(id_titulacao)
);

CREATE TABLE cargo( --Cargo dentro da unidade ensino que trabalha, ex: Professor titular.
    id_cargo                        SERIAL          NOT NULL,
    nome_cargo                      TEXT            NOT NULL,

    PRIMARY KEY(id_cargo)
);

CREATE TABLE nivel( --Para qualificar o nivel de experiência de um avaliador. Sugestão: este atributo pode ser atualizado pela data de engresso e a quantidade de avaliações que ele já participou.
    id_nivel                        SERIAL          NOT NULL,
    nome_nivel                      TEXT            NOT NULL, --Atualmente se trabalha com (A, B, C, Z).

    PRIMARY KEY(id_nivel)
);

CREATE TABLE tipo_status_avaliador( --Ativo, inativo, suspenso, pendente de confirmação, desligado.
    id_tipo_status_avaliador        SERIAL          NOT NULL,
    nome_tipo_status_avaliador      TEXT            NOT NULL,

    PRIMARY KEY(id_tipo_status_avaliador)
);

CREATE TABLE status_avaliador(
    id_status_avaliador             SERIAL          NOT NULL,
    justificativa_status_avaliador  TEXT            NOT NULL, --É necessário justificar a alteração do status de um avaliador.
    data_status_avaliador           DATE            NOT NULL,
    cod_tipo_status_avaliador       INTEGER         NOT NULL,

    PRIMARY KEY(id_status_avaliador),
    FOREIGN KEY(cod_tipo_status_avaliador)          REFERENCES tipo_status_avaliador(id_tipo_status_avaliador)
);

CREATE TABLE eixo_tecnologico(
    id_eixo_tecnologico             SERIAL          NOT NULL,
    nome_eixo_tecnologico           TEXT            NOT NULL,

    PRIMARY KEY(id_eixo_tecnologico)
);

CREATE TABLE avaliador(
    id_avaliador                    SERIAL          NOT NULL,
    data_engresso                   INTEGER         NOT NULL, --Necessário para saber a experiência de ensino, a data é referente a quando ele comeu a lecionar.
    siape_avaliador                 TEXT            NOT NULL,
    cod_pessoa                      INTEGER         NOT NULL,
    cod_titulacao                   INTEGER         NOT NULL,
    cod_nivel                       INTEGER         NOT NULL,
    cod_unidade_ensino              INTEGER         NOT NULL,
    cod_cargo                       INTEGER         NOT NULL,
    cod_status_avaliador            INTEGER         NOT NULL,

    PRIMARY KEY(id_avaliador),
    FOREIGN KEY(cod_pessoa)                         REFERENCES pessoa(id_pessoa),
    FOREIGN KEY(cod_titulacao)                      REFERENCES titulacao(id_titulacao),
    FOREIGN KEY(cod_nivel)                          REFERENCES nivel(id_nivel),
    FOREIGN KEY(cod_unidade_ensino)                 REFERENCES unidade_ensino(id_unidade_ensino),
    FOREIGN KEY(cod_cargo)                          REFERENCES cargo(id_cargo),
    FOREIGN KEY(cod_status_avaliador)               REFERENCES status_avaliador(id_status_avaliador)
);

CREATE TABLE avaliador_eixo( --Entidade fraca para fazer o relacionamento entre avaliador e eixo tecnológico
    cod_avaliador                   INTEGER         NOT NULL,
    cod_eixo_tecnologico            INTEGER         NOT NULL,

    CONSTRAINT id_avaliador_eixo                    PRIMARY KEY(cod_avaliador, cod_eixo_tecnologico),
    FOREIGN KEY(cod_avaliador)                      REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_eixo_tecnologico)               REFERENCES eixo_tecnologico(id_eixo_tecnologico)
);
-- *** Tabelas com dados dos avaliadores. ***

-- *** Tabelas com os dados das avaliações. ***
CREATE TABLE projeto( --Ex. mulheres mil.
    id_projeto                      SERIAL          NOT NULL,
    nome_projeto                    TEXT            NOT NULL,

    PRIMARY KEY(id_projeto)
);

CREATE TABLE avaliador_projeto( --Entidade fraca para fazer o relacionamento entre o avaliador e projeto que ele capacitado para avaliar.
    cod_avaliador                   INTEGER         NOT NULL,
    cod_projeto                     INTEGER         NOT NULL,

    CONSTRAINT id_avaliador_projeto                 PRIMARY KEY(cod_avaliador, cod_projeto),
    FOREIGN KEY(cod_avaliador)                      REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_projeto)                        REFERENCES projeto(id_projeto)
);

CREATE TABLE semana_avaliacao(
    id_semana_avaliacao             SERIAL          NOT NULL,
    numero_semana_avaliacao         INTEGER         NOT NULL UNIQUE,
    data_inicio_semana_avalicao     DATE            NOT NULL,
    data_fim_semana_avalicao        DATE            NOT NULL,

    PRIMARY KEY(id_semana_avaliacao)
);

CREATE TABLE disponibilidade(
    id_disponibilidade              SERIAL          NOT NULL,
    data_cadastro                   DATE            NOT NULL,

    PRIMARY KEY(id_disponibilidade)
);

CREATE TABLE avaliador_disponibilidade( --Entidade fraca que faz o relacionamento entre avaliador e sua disponibilidade.
    cod_avaliador                   INTEGER         NOT NULL,
    cod_disponibilidade             INTEGER         NOT NULL,

    CONSTRAINT id_avaliador_disponibilidade         PRIMARY KEY(cod_avaliador, cod_disponibilidade),
    FOREIGN KEY(cod_avaliador)                      REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_disponibilidade)                REFERENCES disponibilidade(id_disponibilidade)
);

CREATE TABLE disponibilidade_semana( --Entidade fraca que faz o relacionamento entre disponibilidade e as semana de avaliação
    cod_disponibilidade             INTEGER         NOT NULL,
    cod_semana_avaliacao            INTEGER         NOT NULL,

    CONSTRAINT id_disponibilidade_semana            PRIMARY KEY(cod_disponibilidade, cod_semana_avaliacao),
    FOREIGN KEY(cod_disponibilidade)                REFERENCES disponibilidade(id_disponibilidade),
    FOREIGN KEY(cod_semana_avaliacao)               REFERENCES semana_avaliacao(id_semana_avaliacao)
);

CREATE TABLE avaliacao(
    id_avaliacao                    SERIAL          NOT NULL,
    cod_projeto                     INTEGER         NOT NULL,
    cod_semana_avaliacao            INTEGER         NOT NULL,
    cod_unidade_ensino              INTEGER         NOT NULL,

    PRIMARY KEY(id_avaliacao),
    FOREIGN KEY(cod_projeto)                        REFERENCES projeto(id_projeto),
    FOREIGN KEY(cod_semana_avaliacao)               REFERENCES semana_avaliacao(id_semana_avaliacao),
    FOREIGN KEY(cod_unidade_ensino)                 REFERENCES unidade_ensino(id_unidade_ensino)
);

CREATE TABLE equipe(
    id_equipe                       SERIAL          NOT NULL,
    numero_equipe                   INTEGER         NOT NULL, --Para que um operador possa identificar a equipe (ele não precisa saber o id_equipe).
    cod_avaliacao                   INTEGER         NOT NULL, --A equipe formada estará sempre ligada uma avaliação.

    PRIMARY KEY(id_equipe),
    FOREIGN KEY(cod_avaliacao)                      REFERENCES avaliacao(id_avaliacao)
);

CREATE TABLE integrante( --Entidade fraca para fazer o relacionamento dos avaliadores que irão compor uma equipe para uma avaliação.
    cod_avaliador                   INTEGER         NOT NULL,
    cod_equipe                      INTEGER         NOT NULL,

    CONSTRAINT id_integrante                        PRIMARY KEY(cod_avaliador, cod_equipe),
    FOREIGN KEY(cod_avaliador)                      REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_equipe)                         REFERENCES equipe(id_equipe)
);
-- *** Fim das tabelas com os dados das avaliações. ***

-- *** Tabelas com os dados de viagem e confirmação de viagem. ***
CREATE TABLE viagem(
    id_viagem                       SERIAL          NOT NULL,
    percurso                        TEXT            NOT NULL, --Locais que a avaliador irá passar para chegar a unidade ensino a ser avaliada. Ida e volda.
    data_saida_viagem               DATE            NOT NULL,
    data_chegada_viagem             DATE            NOT NULL,

    PRIMARY KEY(id_viagem)
);

CREATE TABLE confirmacao_avaliador(
    id_confirmacao_avaliador        SERIAL          NOT NULL,
    status_confirmacao_avaliador    TEXT            NOT NULL, --Confirmado, negado, dependente.
    data_confirmacao_avaliador      DATE            NOT NULL,
    cod_avaliador                   INTEGER         NOT NULL,
    cod_viagem                      INTEGER         NOT NULL,

    PRIMARY KEY(id_confirmacao_avaliador),
    FOREIGN KEY(cod_avaliador)                      REFERENCES avaliador(id_avaliador),
    FOREIGN KEY(cod_viagem)                         REFERENCES viagem(id_viagem)
);

CREATE TABLE atividade( --Cada atividade feita pelo avaliador deverá ser registrada posteriormente.
    id_atividade                    SERIAL          NOT NULL,
    data_atividade                  DATE            NOT NULL,
    descricao_atividade             TEXT            NOT NULL,
    cod_viagem                      INTEGER         NOT NULL,

    PRIMARY KEY(id_atividade),
    FOREIGN KEY(cod_viagem)                         REFERENCES viagem(id_viagem)
);
-- *** Fim das tabelas com os dados de viajem e confirmação de viajem. ***