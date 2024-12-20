CREATE DATABASE Projeto;
CREATE TABLE Funcionarios (
id_funcionarios INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
departamento VARCHAR(100) NOT NULL,
salario DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Projetos (
id_projeto INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
descricao TEXT,
orcamento DECIMAL(15, 2) NOT NULL
);

CREATE TABLE Funcionarios_Projetos (
id_funcionarios INT,
id_projeto INT,
horas_trabalhadas INT NOT NULL,
PRIMARY KEY (id_funcionarios, id_projeto),
FOREIGN KEY (id_funcionarios) REFERENCES Funcionarios(id_funcionarios),
FOREIGN KEY (id_projeto) REFERENCES Projetos(id_projeto)
);

INSERT INTO Funcionarios (nome, departamento, salario) VALUES 
    ('Alice Silva', 'TI', 5500.00),
    ('Bruno Santos', 'RH', 4200.00),
    ('Clara Oliveira', 'Financeiro', 6000.00),
    ('Daniel Costa', 'Marketing', 4800.00);

INSERT INTO Projetos (nome, descricao, orcamento) VALUES 
    ('Sistema Web', 'Desenvolvimento de um sistema web corporativo', 150000.00),
    ('Recrutamento 2024', 'Planejamento e execução de novas contratações', 50000.00),
    ('Campanha Publicitária', 'Campanha de marketing para novos produtos', 80000.00);

INSERT INTO Funcionarios_Projetos (id_funcionarios, id_projeto, horas_trabalhadas) VALUES
    (1, 1, 120),  
    (1, 2, 30),   
    (2, 2, 50),   
    (3, 1, 80),  
    (3, 3, 60),  
    (4, 3, 100); 
    

SELECT 
    f.nome AS Nome_Funcionario,
    f.departamento AS Departamento,
    fp.horas_trabalhadas AS Horas_Trabalhadas
FROM 
    Funcionarios f
JOIN 
    Funcionarios_Projetos fp ON f.id_funcionarios = fp.id_funcionarios
JOIN 
    Projetos p ON fp.id_projeto = p.id_projeto
WHERE 
    p.nome = 'Sistema Web';