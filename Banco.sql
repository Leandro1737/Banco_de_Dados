create database Banco_Biblioteca;

create table Livros(
Titulo varchar (155),
Autores varchar (90),
Ano_Publicação varchar (10),
Num_Exemplares int
);

create table Emprestimo(
Data_Emprestimo varchar (10)
);

create table Cliente (
Id int,
Nome varchar (100),
Email varchar (255)

);