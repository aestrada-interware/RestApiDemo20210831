-- ===============================================================================
CREATE TABLE USUARIO (
    USUARIO          CHAR(1)         NOT NULL ,
    NOMBRE           VARCHAR (255)   NOT NULL ,
    PATERNO          VARCHAR (255)   NOT NULL ,
    MATERNO          VARCHAR (255)   NOT NULL ,
    SEXO             VARCHAR (1)   NOT NULL ,
    TELEFONO         VARCHAR (12)   NOT NULL ,
    EMAIL            VARCHAR (255)   NOT NULL ,
    PRIMARY KEY (USUARIO)
);