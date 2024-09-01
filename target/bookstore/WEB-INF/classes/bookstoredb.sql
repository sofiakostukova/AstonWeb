PGDMP      &                |         	   bookstore    16.3    16.3 7    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16504 	   bookstore    DATABASE     }   CREATE DATABASE bookstore WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE bookstore;
                postgres    false            �            1259    16635    admins    TABLE     _   CREATE TABLE public.admins (
    id integer NOT NULL,
    admin_role character varying(255)
);
    DROP TABLE public.admins;
       public         heap    postgres    false            �            1259    16506    authors    TABLE     c   CREATE TABLE public.authors (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.authors;
       public         heap    postgres    false            �            1259    16505    authors_id_seq    SEQUENCE     �   CREATE SEQUENCE public.authors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.authors_id_seq;
       public          postgres    false    216            �           0    0    authors_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.authors_id_seq OWNED BY public.authors.id;
          public          postgres    false    215            �            1259    16533    book_categories    TABLE     h   CREATE TABLE public.book_categories (
    book_id integer NOT NULL,
    category_id integer NOT NULL
);
 #   DROP TABLE public.book_categories;
       public         heap    postgres    false            �            1259    16513    books    TABLE     �   CREATE TABLE public.books (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    publication_year integer,
    author_id integer,
    price integer
);
    DROP TABLE public.books;
       public         heap    postgres    false            �            1259    16512    books_id_seq    SEQUENCE     �   CREATE SEQUENCE public.books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.books_id_seq;
       public          postgres    false    218            �           0    0    books_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.books_id_seq OWNED BY public.books.id;
          public          postgres    false    217            �            1259    16525 
   categories    TABLE     f   CREATE TABLE public.categories (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);
    DROP TABLE public.categories;
       public         heap    postgres    false            �            1259    16524    categories_id_seq    SEQUENCE     �   CREATE SEQUENCE public.categories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.categories_id_seq;
       public          postgres    false    220            �           0    0    categories_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;
          public          postgres    false    219            �            1259    16655 	   customers    TABLE     e   CREATE TABLE public.customers (
    id integer NOT NULL,
    customer_type character varying(255)
);
    DROP TABLE public.customers;
       public         heap    postgres    false            �            1259    16645 
   moderators    TABLE     g   CREATE TABLE public.moderators (
    id integer NOT NULL,
    moderator_role character varying(255)
);
    DROP TABLE public.moderators;
       public         heap    postgres    false            �            1259    16625    user_id_sequence    SEQUENCE     z   CREATE SEQUENCE public.user_id_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.user_id_sequence;
       public          postgres    false            �            1259    16671    user_seq    SEQUENCE     r   CREATE SEQUENCE public.user_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.user_seq;
       public          postgres    false            �            1259    16627    users    TABLE     "  CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(255),
    password character varying(255),
    user_type character varying(31),
    admin_role character varying(255),
    moderator_role character varying(255),
    customer_type character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16626    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    224            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    223            ;           2604    16509 
   authors id    DEFAULT     h   ALTER TABLE ONLY public.authors ALTER COLUMN id SET DEFAULT nextval('public.authors_id_seq'::regclass);
 9   ALTER TABLE public.authors ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            <           2604    16516    books id    DEFAULT     d   ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);
 7   ALTER TABLE public.books ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            =           2604    16528    categories id    DEFAULT     n   ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);
 <   ALTER TABLE public.categories ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219    220            >           2604    16630    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223    224            �          0    16635    admins 
   TABLE DATA           0   COPY public.admins (id, admin_role) FROM stdin;
    public          postgres    false    225   i;       �          0    16506    authors 
   TABLE DATA           +   COPY public.authors (id, name) FROM stdin;
    public          postgres    false    216   �;       �          0    16533    book_categories 
   TABLE DATA           ?   COPY public.book_categories (book_id, category_id) FROM stdin;
    public          postgres    false    221   �;       �          0    16513    books 
   TABLE DATA           N   COPY public.books (id, title, publication_year, author_id, price) FROM stdin;
    public          postgres    false    218   <       �          0    16525 
   categories 
   TABLE DATA           .   COPY public.categories (id, name) FROM stdin;
    public          postgres    false    220   e<       �          0    16655 	   customers 
   TABLE DATA           6   COPY public.customers (id, customer_type) FROM stdin;
    public          postgres    false    227   �<       �          0    16645 
   moderators 
   TABLE DATA           8   COPY public.moderators (id, moderator_role) FROM stdin;
    public          postgres    false    226   �<       �          0    16627    users 
   TABLE DATA           m   COPY public.users (id, username, password, user_type, admin_role, moderator_role, customer_type) FROM stdin;
    public          postgres    false    224   =       �           0    0    authors_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.authors_id_seq', 4, true);
          public          postgres    false    215            �           0    0    books_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.books_id_seq', 5, true);
          public          postgres    false    217                        0    0    categories_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.categories_id_seq', 4, true);
          public          postgres    false    219                       0    0    user_id_sequence    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.user_id_sequence', 1, false);
          public          postgres    false    222                       0    0    user_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.user_seq', 151, true);
          public          postgres    false    228                       0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 3, true);
          public          postgres    false    223            L           2606    16639    admins admins_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.admins
    ADD CONSTRAINT admins_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.admins DROP CONSTRAINT admins_pkey;
       public            postgres    false    225            @           2606    16511    authors authors_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.authors DROP CONSTRAINT authors_pkey;
       public            postgres    false    216            H           2606    16537 $   book_categories book_categories_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY public.book_categories
    ADD CONSTRAINT book_categories_pkey PRIMARY KEY (book_id, category_id);
 N   ALTER TABLE ONLY public.book_categories DROP CONSTRAINT book_categories_pkey;
       public            postgres    false    221    221            B           2606    16518    books books_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.books DROP CONSTRAINT books_pkey;
       public            postgres    false    218            D           2606    16624    categories categories_name_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_name_key UNIQUE (name);
 H   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_name_key;
       public            postgres    false    220            F           2606    16530    categories categories_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public            postgres    false    220            P           2606    16659    customers customers_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_pkey;
       public            postgres    false    227            N           2606    16649    moderators moderators_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.moderators
    ADD CONSTRAINT moderators_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.moderators DROP CONSTRAINT moderators_pkey;
       public            postgres    false    226            J           2606    16634    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    224            T           2606    16640    admins admins_id_fkey    FK CONSTRAINT     o   ALTER TABLE ONLY public.admins
    ADD CONSTRAINT admins_id_fkey FOREIGN KEY (id) REFERENCES public.users(id);
 ?   ALTER TABLE ONLY public.admins DROP CONSTRAINT admins_id_fkey;
       public          postgres    false    224    225    4682            R           2606    16538 ,   book_categories book_categories_book_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.book_categories
    ADD CONSTRAINT book_categories_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.books(id) ON DELETE CASCADE;
 V   ALTER TABLE ONLY public.book_categories DROP CONSTRAINT book_categories_book_id_fkey;
       public          postgres    false    4674    221    218            S           2606    16543 0   book_categories book_categories_category_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.book_categories
    ADD CONSTRAINT book_categories_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.categories(id) ON DELETE CASCADE;
 Z   ALTER TABLE ONLY public.book_categories DROP CONSTRAINT book_categories_category_id_fkey;
       public          postgres    false    4678    220    221            Q           2606    16519    books books_author_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.authors(id) ON DELETE SET NULL;
 D   ALTER TABLE ONLY public.books DROP CONSTRAINT books_author_id_fkey;
       public          postgres    false    216    4672    218            V           2606    16660    customers customers_id_fkey    FK CONSTRAINT     u   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_id_fkey FOREIGN KEY (id) REFERENCES public.users(id);
 E   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_id_fkey;
       public          postgres    false    224    227    4682            U           2606    16650    moderators moderators_id_fkey    FK CONSTRAINT     w   ALTER TABLE ONLY public.moderators
    ADD CONSTRAINT moderators_id_fkey FOREIGN KEY (id) REFERENCES public.users(id);
 G   ALTER TABLE ONLY public.moderators DROP CONSTRAINT moderators_id_fkey;
       public          postgres    false    224    4682    226            �   "   x�3�,.-H-�OL����240������� l��      �   1   x�3�t��O�/Rp�/.�O-+ή�2�t�+��Sp�H���/����� !��      �      x�3�4�2�4�2�4����� Z      �   P   x�3��HUp�H-*�T�/J�H,J�4�40�4�45�2�t.��MUH�KQ(��,��M�+�4�03�4�4251����� �B       �   7   x�3�t�I,.�LV��,I-J,)-J�2�����/�/Ȩ�2�t)J�M����� w��      �      x�3�,JM/�I,����� #��      �   !   x�3�L��+I�+���OI-J,�/����� s�      �   b   x�3�LL��̋/-N-�2����R \�?�2���OI-J,�/�(Gp�Z�BmƜɥ�%���P]p\L�����3+?#�3/����91z\\\ D�@�     