CREATE TABLE IF NOT EXISTS public.certificates
(
    certificate_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL DEFAULT '',
    description text NOT NULL DEFAULT '',
    price numeric(8,2) NOT NULL DEFAULT 0,
    duration smallint NOT NULL DEFAULT 0,
    create_date timestamp with time zone NOT NULL DEFAULT current_timestamp,
    last_update_date timestamp with time zone NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (certificate_id)
);

ALTER TABLE public.certificates
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.tags
(
    tag_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL DEFAULT '',
    PRIMARY KEY (tag_id)
);

ALTER TABLE public.tags
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.certificates_tags
(
    certificate_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    PRIMARY KEY (certificate_id, tag_id),
    FOREIGN KEY (certificate_id)
        REFERENCES public.certificates (certificate_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (tag_id)
        REFERENCES public.tags (tag_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE public.certificates_tags
    OWNER to postgres;