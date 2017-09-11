--Atualizações: 

--  Criação de View

-- Rule: "_RETURN" ON public.rel_geral

-- DROP RULE "_RETURN" ON public.rel_geral;

CREATE OR REPLACE RULE "_RETURN" AS
    ON SELECT TO rel_geral DO INSTEAD  SELECT (c.nm_cliente::text || ' - '::text) || c.cd_cliente AS cliente,
    (o.nm_obra::text || ' - '::text) || o.cd_obra AS obra,
        CASE
            WHEN o.status = 'A'::bpchar THEN 'ABERTA'::text
            WHEN o.status = 'F'::bpchar THEN 'FECHADA'::text
            ELSE 'OUTROS'::text
        END AS status_obra,
    sum(COALESCE(s.valor_cobrar, 0::numeric)) AS total_servicos,
    sum(COALESCE(d.valor_despesa, 0::numeric)) AS total_despesas,
    sum(COALESCE(r.valor_pago, 0::numeric)) AS total_pago,
    sum(COALESCE(r.valor_pago, 0::numeric)) - sum(COALESCE(s.valor_cobrar, 0::numeric)) - sum(COALESCE(d.valor_despesa, 0::numeric)) AS total_geral
   FROM obra o
     JOIN cliente c ON o.cd_cliente = c.cd_cliente
     LEFT JOIN lanc_serv_prestado s ON o.cd_obra = s.cd_obra
     LEFT JOIN lancamento_despesa d ON o.cd_obra = d.cd_obra
     LEFT JOIN lancamento_recebimento r ON o.cd_obra = r.cd_obra
  GROUP BY ((c.nm_cliente::text || ' - '::text) || c.cd_cliente), ((o.nm_obra::text || ' - '::text) || o.cd_obra), (
        CASE
            WHEN o.status = 'A'::bpchar THEN 'ABERTA'::text
            WHEN o.status = 'F'::bpchar THEN 'FECHADA'::text
            ELSE 'OUTROS'::text
        END)

--  PK Criada em Lista Lançamento de Despesas

CREATE TABLE public.lancamento_despesa
(
  cd_despesa integer NOT NULL,
  data_despesa date NOT NULL,
  cd_obra integer,
  valor_despesa numeric(10,2),
  conta character varying(15),
  CONSTRAINT pk_lancamento_despesa PRIMARY KEY (cd_despesa, data_despesa),
  CONSTRAINT fk_lancamento_despesa FOREIGN KEY (cd_despesa)
      REFERENCES public.despesa (cd_despesa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_lancamento_despesa_conta FOREIGN KEY (conta)
      REFERENCES public.conta (conta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_lancamento_despesa_obra FOREIGN KEY (cd_obra)
      REFERENCES public.obra (cd_obra) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


--  EPI

CREATE TABLE public."EPI"
(
  ds_epi text,
  valor numeric(10,2),
  data_retirada timestamp without time zone,
  funcionario integer NOT NULL,
  "id_EPI" integer NOT NULL,
  CONSTRAINT pk_id_epi PRIMARY KEY ("id_EPI"),
  CONSTRAINT "FK_EPI_Funcionario" FOREIGN KEY (funcionario)
      REFERENCES public.funcionario (cpf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."EPI"
  OWNER TO postgres;


--  ALTER TABLE public.lancamento_despesa


ALTER TABLE public.lancamento_despesa
   ADD COLUMN quatidade integer;

--  Sequence EPI

CREATE SEQUENCE public.id_epi
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.id_epi
  OWNER TO postgres;




/**************************************************************************************************
                                     UPPER CASE NOS CAMPOS EXISTENTES
**************************************************************************************************/

UPDATE PUBLIC.CLIENTE
   SET NM_CLIENTE = UPPER(NM_CLIENTE),
       ENDERECO   = UPPER(ENDERECO),
       BAIRRO     = UPPER(BAIRRO),
       CIDADE     = UPPER(CIDADE),
       UF         = UPPER(UF),
       TP_PESSOA  = UPPER(TP_PESSOA);

UPDATE PUBLIC.BEM SET DS_BEM = UPPER(DS_BEM), TP_BEM = UPPER(TP_BEM);

UPDATE PUBLIC.DESPESA SET DS_DESPESA = UPPER(DS_DESPESA);

UPDATE PUBLIC.FUNCIONARIO
   SET NM_FUNCIONARIO = UPPER(NM_FUNCIONARIO),
       APELIDO        = UPPER(APELIDO),
       RG             = UPPER(RG),
       CTPS           = UPPER(CTPS),
       PIS_PASEP      = UPPER(PIS_PASEP),
       CNH            = UPPER(CNH),
       ENDERECO       = UPPER(ENDERECO),
       BAIRRO         = UPPER(BAIRRO),
       CIDADE         = UPPER(CIDADE),
       UF             = UPPER(UF),
       BANCO          = UPPER(BANCO),
       AGENCIA        = UPPER(AGENCIA),
       CONTA          = UPPER(CONTA),
       FONE_RESID     = UPPER(FONE_RESID),
       CELULAR1       = UPPER(CELULAR1);

UPDATE PUBLIC.CONTA
   SET BANCO   = UPPER(BANCO),
       AGENCIA = UPPER(AGENCIA),
       STATUS  = UPPER(STATUS);

UPDATE PUBLIC.INTEGRACAO
   SET EMPRESA_INTEGRACAO = UPPER(EMPRESA_INTEGRACAO);

UPDATE PUBLIC.LANC_SERV_PRESTADO SET NOTA_FISCAL = UPPER(NOTA_FISCAL);

UPDATE PUBLIC.OBRA
   SET NM_OBRA  = UPPER(NM_OBRA),
       ENDERECO = UPPER(ENDERECO),
       BAIRRO   = UPPER(BAIRRO),
       CIDADE   = UPPER(CIDADE),
       UF       = UPPER(UF),
       STATUS   = UPPER(STATUS);

UPDATE PUBLIC.SERVICO SET DESC_SERVICO = UPPER(DESC_SERVICO);
