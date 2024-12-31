CREATE DATABASE IF NOT EXISTS biblioteca;

USE biblioteca;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de Livros
CREATE TABLE IF NOT EXISTS livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autores VARCHAR(255) NOT NULL,
    ano_publicacao VARCHAR(4),
    num_exemplares INT NOT NULL,
    exemplares_disponiveis INT NOT NULL
);


-- Tabela de Empréstimos
CREATE TABLE IF NOT EXISTS emprestimos (
    cliente_id INT NOT NULL,
    livro_id INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    PRIMARY KEY (cliente_id, livro_id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE
);
