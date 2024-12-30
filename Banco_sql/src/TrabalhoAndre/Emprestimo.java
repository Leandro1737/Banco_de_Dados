package TrabalhoAndre;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Emprestimo {
    private Livros livro;
    private String dataEmprestimo;
    private Cliente cliente;  

    public Emprestimo(Livros livro, String dataEmprestimo, Cliente cliente) {
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.cliente = cliente;
    }

    public Livros getLivro() {
        return livro;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    public void inserir() {

        Connection conexao = new Conexao().getConexao();


        String sql = "INSERT INTO tb_emprestimo (livro, dataEmprestimo, cliente) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt;
            stmt = conexao.prepareStatement(sql);
            stmt.setLivros(1, this.livro);
            stmt.setString(2, this.dataEmprestimo);
            stmt.setCliente(3, this.cliente);


            stmt.execute();
            stmt.close();

            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //Metodo que percorre os emprestimos e printa o que é diferente de vazio
    public static void listaEmprestimos(Emprestimo[] emprestimos) {
        System.out.println("Empréstimos realizados:");
        boolean encontrouEmprestimos = false;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo != null) {
                System.out.println("Cliente: " + emprestimo.getCliente().getNome() + " - Livro: " 
                    + emprestimo.getLivro().titulo + " - Data do Empréstimo: " 
                    + emprestimo.getDataEmprestimo());
                encontrouEmprestimos = true;
            }
        }

        if (!encontrouEmprestimos) {
            System.out.println("Nenhum empréstimo realizado.");
        }
    }
    // executa o metodo devolver do livro e printa
    public void devolver() {
        livro.devolverExemplar();
        System.out.println("Livro devolvido com sucesso!");
    }
    // percorre os emprestimos e o que for diferente de vazio e igual ao id do cliente informado ele da como true
    public static boolean empCadastrado(Emprestimo[] emprestimos, int clienteId) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo != null && emprestimo.getCliente().getID() == clienteId) {
                return true;
            }
        }
        return false;
    }
}