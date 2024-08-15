--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-08 18:17:31

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16506)
-- Name: authors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authors (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.authors OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16505)
-- Name: authors_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.authors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.authors_id_seq OWNER TO postgres;

--
-- TOC entry 4822 (class 0 OID 0)
-- Dependencies: 215
-- Name: authors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.authors_id_seq OWNED BY public.authors.id;


--
-- TOC entry 221 (class 1259 OID 16533)
-- Name: book_categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book_categories (
    book_id integer NOT NULL,
    category_id integer NOT NULL
);


ALTER TABLE public.book_categories OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16513)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    publication_year integer,
    author_id integer,
    price integer
);


ALTER TABLE public.books OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16512)
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.books_id_seq OWNER TO postgres;

--
-- TOC entry 4823 (class 0 OID 0)
-- Dependencies: 217
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.books_id_seq OWNED BY public.books.id;


--
-- TOC entry 223 (class 1259 OID 16610)
-- Name: bookstore.public; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."bookstore.public" (
    id integer NOT NULL,
    title character varying(255),
    publication_year integer,
    author_id integer,
    price integer
);


ALTER TABLE public."bookstore.public" OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16609)
-- Name: bookstore.public_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."bookstore.public_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."bookstore.public_id_seq" OWNER TO postgres;

--
-- TOC entry 4824 (class 0 OID 0)
-- Dependencies: 222
-- Name: bookstore.public_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."bookstore.public_id_seq" OWNED BY public."bookstore.public".id;


--
-- TOC entry 220 (class 1259 OID 16525)
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16524)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO postgres;

--
-- TOC entry 4825 (class 0 OID 0)
-- Dependencies: 219
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 4648 (class 2604 OID 16509)
-- Name: authors id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authors ALTER COLUMN id SET DEFAULT nextval('public.authors_id_seq'::regclass);


--
-- TOC entry 4649 (class 2604 OID 16516)
-- Name: books id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);


--
-- TOC entry 4651 (class 2604 OID 16613)
-- Name: bookstore.public id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."bookstore.public" ALTER COLUMN id SET DEFAULT nextval('public."bookstore.public_id_seq"'::regclass);


--
-- TOC entry 4650 (class 2604 OID 16528)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 4809 (class 0 OID 16506)
-- Dependencies: 216
-- Data for Name: authors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.authors (id, name) FROM stdin;
1	Leo Tolstoy
2	Fyodor Dostoevsky
3	Anton Chekhov
\.


--
-- TOC entry 4814 (class 0 OID 16533)
-- Dependencies: 221
-- Data for Name: book_categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book_categories (book_id, category_id) FROM stdin;
2	1
3	3
2	2
\.


--
-- TOC entry 4811 (class 0 OID 16513)
-- Dependencies: 218
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.books (id, title, publication_year, author_id, price) FROM stdin;
3	The Cherry Orchard	1904	3	954
2	Crime and Punishment	1866	2	2542
5	New Book	2024	1	300
\.


--
-- TOC entry 4816 (class 0 OID 16610)
-- Dependencies: 223
-- Data for Name: bookstore.public; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."bookstore.public" (id, title, publication_year, author_id, price) FROM stdin;
1	War and Peace	1869	1	1254
2	Crime and Punishment	1866	2	2542
3	The Cherry Orchard	1904	3	\N
\.


--
-- TOC entry 4813 (class 0 OID 16525)
-- Dependencies: 220
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, name) FROM stdin;
1	Classic Literature
2	Philosophy
3	Drama
\.


--
-- TOC entry 4826 (class 0 OID 0)
-- Dependencies: 215
-- Name: authors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.authors_id_seq', 4, true);


--
-- TOC entry 4827 (class 0 OID 0)
-- Dependencies: 217
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.books_id_seq', 5, true);


--
-- TOC entry 4828 (class 0 OID 0)
-- Dependencies: 222
-- Name: bookstore.public_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."bookstore.public_id_seq"', 1, false);


--
-- TOC entry 4829 (class 0 OID 0)
-- Dependencies: 219
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 4, true);


--
-- TOC entry 4653 (class 2606 OID 16511)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- TOC entry 4661 (class 2606 OID 16537)
-- Name: book_categories book_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_categories
    ADD CONSTRAINT book_categories_pkey PRIMARY KEY (book_id, category_id);


--
-- TOC entry 4655 (class 2606 OID 16518)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- TOC entry 4657 (class 2606 OID 16532)
-- Name: categories categories_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_name_key UNIQUE (name);


--
-- TOC entry 4659 (class 2606 OID 16530)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 4663 (class 2606 OID 16538)
-- Name: book_categories book_categories_book_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_categories
    ADD CONSTRAINT book_categories_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE;


--
-- TOC entry 4664 (class 2606 OID 16543)
-- Name: book_categories book_categories_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book_categories
    ADD CONSTRAINT book_categories_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE CASCADE;


--
-- TOC entry 4662 (class 2606 OID 16519)
-- Name: books books_author_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.authors(id) ON DELETE SET NULL;


-- Completed on 2024-08-08 18:17:31

--
-- PostgreSQL database dump complete
--

