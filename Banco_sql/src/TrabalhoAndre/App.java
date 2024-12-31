package TrabalhoAndre;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("=== Sistema de Gerenciamento da Biblioteca ===");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Editar cliente");
            System.out.println("4. Apagar cliente");
            System.out.println("5. Cadastrar livro");
            System.out.println("6. Listar livros");
            System.out.println("7. Editar livro");
            System.out.println("8. Apagar livro");
            System.out.println("9. Cadastrar empréstimo");
            System.out.println("10. Listar empréstimos");
            System.out.println("11. Atualizar empréstimo");
            System.out.println("12. Devolver livro");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do cliente: ");
                        String nomeCliente = scanner.nextLine();
                        System.out.print("Digite o email do cliente: ");
                        String emailCliente = scanner.nextLine();
                        Cliente.cadastrarCliente(nomeCliente, emailCliente);
                        break;

                    case 2:
                        Cliente.listarClientes();
                        break;

                    case 3:
                        System.out.print("Digite o ID do cliente a ser editado: ");
                        int idClienteEdit = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha
                        System.out.print("Digite o novo nome do cliente: ");
                        String novoNomeCliente = scanner.nextLine();
                        System.out.print("Digite o novo email do cliente: ");
                        String novoEmailCliente = scanner.nextLine();
                        Cliente.editarCliente(idClienteEdit, novoNomeCliente, novoEmailCliente);
                        break;

                    case 4:
                        System.out.print("Digite o ID do cliente a ser apagado: ");
                        int idClienteDel = scanner.nextInt();
                        Cliente.apagarCliente(idClienteDel);
                        break;

                    case 5:
                        System.out.print("Digite o título do livro: ");
                        String tituloLivro = scanner.nextLine();
                        System.out.print("Digite os autores do livro: ");
                        String autoresLivro = scanner.nextLine();
                        System.out.print("Digite o ano de publicação: ");
                        String anoLivro = scanner.nextLine();
                        System.out.print("Digite o número de exemplares: ");
                        int numExemplares = scanner.nextInt();
                        Livros livro = new Livros(0, tituloLivro, autoresLivro, anoLivro, numExemplares);
                        Livros.cadastrarLivro(livro);
                        break;

                    case 6:
                        Livros.listarLivros();
                        break;

                    case 7:
                        System.out.print("Digite o ID do livro a ser editado: ");
                        int idLivroEdit = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha
                        System.out.print("Digite o novo título do livro: ");
                        String novoTituloLivro = scanner.nextLine();
                        System.out.print("Digite os novos autores do livro: ");
                        String novosAutoresLivro = scanner.nextLine();
                        System.out.print("Digite o novo ano de publicação: ");
                        String novoAnoLivro = scanner.nextLine();
                        System.out.print("Digite o novo número de exemplares: ");
                        int novoNumExemplares = scanner.nextInt();
                        Livros.atualizarLivro(idLivroEdit, novoTituloLivro, novosAutoresLivro, novoAnoLivro, novoNumExemplares);
                        break;

                    case 8:
                        System.out.print("Digite o ID do livro a ser apagado: ");
                        int idLivroDel = scanner.nextInt();
                        Livros.apagarLivro(idLivroDel);
                        break;

                    case 9:
                        System.out.print("Digite o ID do cliente: ");
                        int idClienteEmp = scanner.nextInt();
                        System.out.print("Digite o ID do livro: ");
                        int idLivroEmp = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha
                        System.out.print("Digite a data do empréstimo (YYYY-MM-DD): ");
                        String dataEmprestimo = scanner.nextLine();
                        Livros livroEmp = new Livros(idLivroEmp, "", "", "", 0); // Mock do livro com ID
                        Cliente clienteEmp = new Cliente("", "", idClienteEmp); // Mock do cliente com ID
                        Emprestimo.cadastrarEmprestimo(livroEmp, clienteEmp, dataEmprestimo);
                        break;

                    case 10:
                        Emprestimo.listaEmprestimos();
                        break;

                    case 11:
                        System.out.print("Digite o ID do cliente: ");
                        int idClienteUpdate = scanner.nextInt();
                        System.out.print("Digite o ID do livro: ");
                        int idLivroUpdate = scanner.nextInt();
                        scanner.nextLine(); // Consumir a nova linha
                        System.out.print("Digite a nova data do empréstimo (YYYY-MM-DD): ");
                        String novaDataEmprestimo = scanner.nextLine();
                        Emprestimo.atualizarEmprestimo(idClienteUpdate, idLivroUpdate, novaDataEmprestimo);
                        break;

                    case 12:
                        System.out.print("Digite o ID do cliente: ");
                        int idClienteDelEmp = scanner.nextInt();
                        System.out.print("Digite o ID do livro: ");
                        int idLivroDelEmp = scanner.nextInt();
                        Emprestimo.apagarEmprestimo(idClienteDelEmp, idLivroDelEmp);
                        break;

                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um valor válido.");
                scanner.nextLine(); // Limpar o buffer de entrada
            }
        } while (opcao != 0);

        scanner.close();
    }
}