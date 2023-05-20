CREATE TABLE IF NOT EXISTS public.kind
(
    id serial NOT NULL,
    description character varying COLLATE pg_catalog."default",
    priority integer NOT NULL DEFAULT 1,
    CONSTRAINT kind_pkey PRIMARY KEY (id)
);

INSERT INTO public.kind(
	id, description, priority)
	VALUES (1, 'Срочная', 1);
	
INSERT INTO public.kind(
	id, description, priority)
VALUES (2, 'Важная', 2);

INSERT INTO public.kind(
	id, description, priority)
VALUES (3, 'Обычная', 3);

CREATE TABLE IF NOT EXISTS public.tag
(
    description character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT code_id PRIMARY KEY (code),
    CONSTRAINT code_unique UNIQUE (code)
);

INSERT INTO public.tag(
	description, code)
	VALUES ('dev1', 'tag1');
	
INSERT INTO public.tag(
	description, code)
	VALUES ('dev2', 'tag2');


CREATE TABLE IF NOT EXISTS public.task
(
    id bigserial NOT NULL,
    kind_id bigint NOT NULL,
    name character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    plan_date date NOT NULL,
    tag character varying COLLATE pg_catalog."default",
    CONSTRAINT task_pkey PRIMARY KEY (id),
    CONSTRAINT fk_kind FOREIGN KEY (kind_id)
        REFERENCES public.kind (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO public.task(
	id, kind_id, name, description, plan_date, tag)
	VALUES (1, 2, 'name1', 'desc1', '2023-05-11', 'tag1');
	
INSERT INTO public.task(
	id, kind_id, name, description, plan_date, tag)
	VALUES (2, 3, 'name2', 'desc1', '2023-05-12', 'tag1');
	