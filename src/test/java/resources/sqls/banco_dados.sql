insert into instrutor(id, nome, cpf, salario) values(1, 'Bob', '123.456.789-41', 1000.0);
insert into instrutor(id, nome, cpf, salario) values(2, 'Ana', '123.444.789-41', 1200.0);
insert into instrutor(id, nome, cpf, salario) values(3, 'Rafa', '123.456.888-41', 1500.0);
insert into instrutor(id, nome, cpf, salario) values(5, 'Maria', '111.456.789-41', 1700.0);
insert into instrutor(id, nome, cpf, salario) values(4, 'Juliana', '333.456.789-41', 2000.0);


insert into plano(id, descricao, preco) values(1, 'Padrão', 80.0);
insert into plano(id, descricao, preco) values(2, 'Colaborador', 50.0);
insert into plano(id, descricao, preco) values(4, 'Promocional', 60.0);
insert into plano(id, descricao, preco) values(3, 'Comerciário', 70.0);

insert into aluno(id, nome, cpf, plano) values(1, 'Amanda', '120.000.539-25', 1);
insert into aluno(id, nome, cpf, plano) values(2, 'Bruno', '123.456.789-99', 1);
insert into aluno(id, nome, cpf, plano) values(4, 'Carla', '987.456.123-33', 2);
insert into aluno(id, nome, cpf, plano) values(3, 'Douglas', '789.563.214-56', 3);


insert into ficha_treino(id, nome, aluno, instrutor) values(2, 'Treino A', 1, 1);
insert into ficha_treino(id, nome, aluno, instrutor) values(3, 'Treino B', 1, 1);
insert into ficha_treino(id, nome, aluno, instrutor) values(4, 'Treino C', 1, 2);

insert into ficha_treino(id, nome, aluno, instrutor) values(5, 'Treino A', 2, 1);
insert into ficha_treino(id, nome, aluno, instrutor) values(6, 'Treino B', 2, 1);
insert into ficha_treino(id, nome, aluno, instrutor) values(7, 'Treino C', 2, 2);