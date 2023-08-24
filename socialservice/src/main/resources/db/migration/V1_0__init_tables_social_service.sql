CREATE TABLE IF NOT EXISTS social_service.friend_request
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone,
    dt_update timestamp without time zone,
    receiver_uuid uuid,
    sender_uuid uuid,
    status character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT friend_request_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS social_service.friendship
(
    uuid uuid NOT NULL,
    user1_uuid uuid,
    user2_uuid uuid,
    CONSTRAINT friendship_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS social_service.subscription
(
    uuid uuid NOT NULL,
    followed_user_uuid uuid,
    follower_uuid uuid,
    CONSTRAINT subscription_pkey PRIMARY KEY (uuid),
    CONSTRAINT ukg1ij7fqa1lml3pu1evfp7ijxo UNIQUE (follower_uuid, followed_user_uuid)
)
