CREATE TABLE IF NOT EXISTS user_service."user"
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone,
    dt_update timestamp without time zone,
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    surname character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (uuid),
    CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email)
)