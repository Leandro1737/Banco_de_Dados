package TrabalhoAndre;

public class Emprestimo {
    private Livros livro;
    private Cliente cliente;
    private String dataEmprestimo;

    // Construtor
    public Emprestimo(Livros livro, String dataEmprestimo, Cliente cliente) {
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.cliente = cliente;
    }

    // Métodos de acesso
    public Livros getLivro() {
        return livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    // Verifica se o cliente já possui um empréstimo
    public static boolean empCadastrado(Emprestimo[] emprestimos, int clienteId) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo != null && emprestimo.getCliente().getId() == clienteId) {
                return true;  // Cliente já tem um empréstimo ativo
            }
        }
        return false; // Cliente não possui empréstimo ativo
    }

    // Lista todos os empréstimos
    public static void listaEmprestimos(Emprestimo[] emprestimos) {
        System.out.println("Empréstimos cadastrados:");
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo != null) {
                System.out.println("Livro: " + emprestimo.getLivro().getTitulo() + ", Cliente: " + emprestimo.getCliente().getNome() + ", Data: " + emprestimo.getDataEmprestimo());
            }
        }
    }

    // Método para devolver um livro
    public static void devolverLivro(Emprestimo[] emprestimos, Livros[] livros, String titulo, int clienteId) {
        for (int i = 0; i < emprestimos.length; i++) {
            if (emprestimos[i] != null && emprestimos[i].getLivro().getTitulo().equalsIgnoreCase(titulo) && emprestimos[i].getCliente().getId() == clienteId) {
                emprestimos[i].getLivro().devolverExemplar(); // Devolve o exemplar
                emprestimos[i] = null; // Remove o empréstimo
                System.out.println("Livro devolvido com sucesso.");
                return;
            }
        }
        System.out.println("Empréstimo não encontrado.");
    }
}
