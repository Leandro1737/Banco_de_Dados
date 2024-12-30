package TrabalhoAndre;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Cliente[] clientes = new Cliente[3];
            Livros[] livros = new Livros[3];
            Emprestimo[] emprestimos = new Emprestimo[3];
            int contadorLivro = 0, contadorCliente = 0, contadorEmprestimo = 0;
            int proximoID = 1;

            int opcao = 0;
            do {
                System.out.println("Selecione uma opção");
                System.out.println("1 - Cadastrar Livro"
                        + "\n" + "2 - Cadastrar Cliente"
                        + "\n" + "3 - Realizar Empréstimo"
                        + "\n" + "4 - Realizar Devolução"
                        + "\n" + "5 - Verificar clientes, livros ou empréstimos cadastrados"
                        + "\n" + "6 - Salvar e Sair");

                boolean entrada = false;
                while (!entrada) {
                    try { // impede o usuario de digitar um caracter
                        opcao = scanner.nextInt();
                        scanner.nextLine();
                        if (opcao < 1 || opcao > 6) {
                            System.out.println("Por favor, digite uma opção válida entre 1 e 6.");
                        } else {
                            entrada = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Por favor, digite um número entre 1 e 6.");
                        scanner.nextLine();
                    }
                }

                switch (opcao) {

                    case 1: //cadastro livro
                        if (contadorLivro < livros.length) {
                            System.out.println("Informe o título do livro:");
                            String titulo = scanner.nextLine();

                            System.out.println("Informe os autores do livro:");
                            String autores = scanner.nextLine();

                            System.out.println("Informe o ano de publicação do livro:");
                            String anoPublicacao = scanner.nextLine();

                            int numExemplares = 0;
                            boolean validarExemplares = false;
                            while (!validarExemplares) {
                                try {
                                    System.out.println("Informe o número de exemplares do livro:");
                                    numExemplares = scanner.nextInt();
                                    scanner.nextLine();
                                    validarExemplares = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Entrada inválida. Por favor, digite um número inteiro para o número de exemplares.");
                                    scanner.nextLine(); // Limpa a entrada inválida
                                }
                            }

                            livros[contadorLivro] = new Livros(titulo, autores, anoPublicacao, numExemplares);
                            contadorLivro++;
                        } else {
                            System.out.println("Limite de livros atingido.");
                            System.out.println("Deseja apagar algum livro? S ou N");
                            String resposta = scanner.nextLine();
                            if (resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("sim")) {
                                Livros.listarLivros(livros);
                                System.out.println("Digite o título do livro que deseja remover:");
                                String tituloParaRemover = scanner.nextLine();
                                if (Livros.removerLivro(livros, tituloParaRemover)) {
                                    contadorLivro--;
                                }
                            }
                        }
                        break;

                    case 2: //cadastro cliente
                        if (contadorCliente < clientes.length) {
                            System.out.println("Informe o nome do cliente:");
                            String nome = scanner.nextLine();

                            System.out.println("Informe o email do cliente:");
                            String email = scanner.nextLine();

                            clientes[contadorCliente] = new Cliente(nome, email, proximoID);
                            contadorCliente++;
                            proximoID++;
                        } else {
                            System.out.println("Limite de clientes atingido.");
                            System.out.println("Deseja apagar algum cliente? S ou N");
                            String resposta = scanner.nextLine();
                            if (resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("sim")) {
                                Cliente.listarClientes(clientes);

                                int idParaRemover = 0;
                                boolean validarId = false;
                                while (!validarId) {
                                    try { // impede o usuario de digitar um caracter
                                        System.out.println("Digite o ID do cliente que deseja remover:");
                                        idParaRemover = scanner.nextInt();
                                        scanner.nextLine();
                                        validarId = true;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Entrada inválida. Por favor, digite um número inteiro para o ID do cliente.");
                                        scanner.nextLine(); // Limpa a entrada inválida
                                    }
                                }

                                if (Cliente.removerCliente(clientes, idParaRemover)) {
                                    contadorCliente--;
                                    System.out.println("Cliente removido");
                                } else {
                                    System.out.println("Não foi encontrado um cliente com esses dados.");
                                }
                            }
                        }
                        break;

                    case 3: //realizar emprestimo
                        if (contadorEmprestimo < emprestimos.length) {
                            int clienteId = 0;
                            boolean validarClienteId = false;
                            while (!validarClienteId) {
                                try { // impede o usuario de digitar um caracter
                                    System.out.println("Informe o ID do cliente:");
                                    clienteId = scanner.nextInt();
                                    scanner.nextLine();
                                    validarClienteId = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Entrada inválida. Por favor, digite um número inteiro para o ID do cliente.");
                                    scanner.nextLine(); // Limpa a entrada inválida
                                }
                            }

                            if (clienteId > 0 && clienteId <= clientes.length && clientes[clienteId - 1] != null) {
                                Cliente cliente = clientes[clienteId - 1];

                                // Verificar se o cliente já possui um empréstimo ativo
                                if (Emprestimo.empCadastrado(emprestimos, clienteId)) {
                                    System.out.println("Cliente já possui um empréstimo, devolva o livro para pegar outro");
                                    break;
                                }

                                System.out.println("Informe o título do livro:");
                                String tituloLivro = scanner.nextLine();

                                Livros livro = null;
                                for (Livros l : livros) {
                                    if (l != null && l.titulo.equalsIgnoreCase(tituloLivro)) {
                                        livro = l;
                                        break;
                                    }
                                }

                                if (livro != null) {
                                    if (livro.emprestarExemplar()) {
                                        System.out.println("Informe a data do empréstimo:");
                                        String dataEmprestimo = scanner.nextLine();
                                        emprestimos[contadorEmprestimo] = new Emprestimo(livro, dataEmprestimo, cliente);
                                        contadorEmprestimo++;
                                        System.out.println("Empréstimo realizado");
                                    } else {
                                        System.out.println("Não há exemplares disponíveis para empréstimo.");
                                    }
                                } else {
                                    System.out.println("Livro não encontrado.");
                                }
                            } else {
                                System.out.println("ID de cliente não encontrado.");
                            }
                        } else {
                            System.out.println("Limite de empréstimos atingido.");
                        }
                        break;

                    case 4: //devoluçao
                        System.out.println("Informe o título do livro para devolução:");
                        String tituloLivro = scanner.nextLine();

                        System.out.println("Informe o nome do cliente que pegou o livro:");
                        String nomeCliente = scanner.nextLine();

                        System.out.println("Informe a data do empréstimo:");
                        String dataEmprestimo = scanner.nextLine();

                        Livros livro = null;
                        for (Livros l : livros) {
                            if (l != null && l.titulo.equalsIgnoreCase(tituloLivro)) {
                                livro = l;
                                break;
                            }
                        }

                        if (livro != null) {
                            boolean emprestimoEncontrado = false;
                            for (int i = 0; i < emprestimos.length; i++) {
                                if (emprestimos[i] != null
                                        && emprestimos[i].getLivro().titulo.equalsIgnoreCase(tituloLivro)
                                        && emprestimos[i].getCliente().getNome().equalsIgnoreCase(nomeCliente)
                                        && emprestimos[i].getDataEmprestimo().equals(dataEmprestimo)) {

                                    livro.devolverExemplar();
                                    System.out.println("Livro devolvido");

                                    emprestimos[i] = null;
                                    contadorEmprestimo--;
                                    emprestimoEncontrado = true;
                                    System.out.println("Empréstimo removido");
                                    break;
                                }
                            }

                            if (!emprestimoEncontrado) {
                                System.out.println("Empréstimo não encontrado para esse cliente, livro e data.");
                            }
                        } else {
                            System.out.println("Livro não encontrado.");
                        }
                        break;

                    case 5: // verificação de clientes, livros ou empréstimos cadastrados
                        System.out.println("Digite " + "\n"
                        + "1 para visualizar clientes cadastrados" + "\n"
                        + "2 para visualizar livros cadastrados" + "\n"
                        + "3 para visualizar empréstimos cadastrados");

                        int opcVizu = 0;
                        boolean validarOpcao = false;

                        while (!validarOpcao) {  
                            try {
                                opcVizu = scanner.nextInt();  
                                scanner.nextLine();  
                                validarOpcao = true; 
                            } catch (InputMismatchException e) {  
                            System.out.println("Por favor, digite um número válido.");
                            scanner.nextLine();  
                            }
                        }

                        if (opcVizu == 1) {
                            Cliente.listarClientes(clientes);
                        } else if (opcVizu == 2) {
                            Livros.listarLivros(livros);
                        } else if (opcVizu == 3) {
                            Emprestimo.listaEmprestimos(emprestimos);
                        } else {
                            System.out.println("Opção inválida");
                        }
                        break;
                }
            } while (opcao != 6);

            scanner.close();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
    }
}