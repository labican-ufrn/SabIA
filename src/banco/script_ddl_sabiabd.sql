--Script DDL Projeto SabIA

--Excluir tabelas caso elas já existam
DROP TABLE IF EXISTS macrorregiao CASCADE;
DROP TABLE IF EXISTS estado CASCADE;
DROP TABLE IF EXISTS mesorregiao CASCADE;
DROP TABLE IF EXISTS microrregiao CASCADE;
DROP TABLE IF EXISTS cidade CASCADE;
DROP TABLE IF EXISTS pais CASCADE;

--Criando as tabelas do banco

--Tabelas com os dados do IBGE
CREATE TABLE macrorregiao(
    id_macrorregiao         SERIAL      NOT NULL,
    nome_macrorregiao       TEXT        NOT NULL,
    cod_ibge_macrorregiao   INTEGER     NOT NULL,

    PRIMARY KEY(id_macrorregiao)
);

CREATE TABLE estado(
    id_estado               SERIAL      NOT NULL,
    nome_estado             TEXT        NOT NULL,
    sigla_estado            CHAR(2)     NOT NULL,
    cod_ibge_estado         INTEGER     NOT NULL,
    cod_macrorregiao        INTEGER     NOT NULL,

    PRIMARY KEY(id_estado),
    FOREIGN KEY(cod_macrorregiao)       REFERENCES  macrorregiao(id_macrorregiao)
);

CREATE TABLE mesorregiao(
    id_mesorregiao          SERIAL      NOT NULL,
    nome_mesorregiao        TEXT        NOT NULL,
    sigla_mesorregiao       CHAR(2)     NOT NULL,
    cod_ibge_mesorregiao    INTEGER     NOT NULL,
    cod_estado              INTEGER     NOT NULL,

    PRIMARY KEY(id_mesorregiao),
    FOREIGN KEY(cod_estado)             REFERENCES  estado(id_estado)
);

CREATE TABLE microrregiao(
    id_microrregiao         SERIAL      NOT NULL,
    nome_microrregiao       TEXT        NOT NULL,
    cod_ibge_microrregiao   INTEGER     NOT NULL,
    cod_mesorregiao         INTEGER     NOT NULL,

    PRIMARY KEY(id_microrregiao),
    FOREIGN KEY(cod_mesorregiao)        REFERENCES  mesorregiao(id_mesorregiao)
);

CREATE TABLE cidade(
    id_cidade               SERIAL      NOT NULL,
    nome_cidade             TEXT        NOT NULL,
    cod_ibge_cidade         INTEGER     NOT NULL,
    cod_microrregiao        INTEGER     NOT NULL,

    PRIMARY KEY(id_cidade),
    FOREIGN KEY(cod_microrregiao)       REFERENCES  microrregiao(id_microrregiao)
);

CREATE TABLE pais(
    id_pais                 SERIAL      NOT NULL,
    nome_pais               TEXT        NOT NULL,
    sigla_pais              CHAR(2)     NOT NULL,
    cod_ibge_pais           INTEGER     NOT NULL,

    PRIMARY KEY(id_pais)
);