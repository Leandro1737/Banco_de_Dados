package TrabalhoAndre;

public class Livros {
    private String titulo;
    private String autores;
    private String anoPublicacao;
    private int numExemplares;
    private int exemplaresDisponiveis;

    // Construtor
    public Livros(String titulo, String autores, String anoPublicacao, int numExemplares) {
        this.titulo = titulo;
        this.autores = autores;
        this.anoPublicacao = anoPublicacao;
        this.numExemplares = numExemplares;
        this.exemplaresDisponiveis = numExemplares;  // Inicializa com o número total de exemplares
    }

    // Métodos de acesso
    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    public int getNumExemplares() {
        return numExemplares;
    }

    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    // Métodos para empréstimo e devolução
    public boolean emprestarExemplar() {
        if (exemplaresDisponiveis > 0) {
            exemplaresDisponiveis--;
            return true;  // Empréstimo bem-sucedido
        }
        return false;  // Não há exemplares disponíveis
    }

    public void devolverExemplar() {
        if (exemplaresDisponiveis < numExemplares) {
            exemplaresDisponiveis++;
        }
    }

    // Método estático para listar os livros
    public static void listarLivros(Livros[] livros) {
        System.out.println("Livros cadastrados:");
        for (Livros livro : livros) {
            if (livro != null) {
                System.out.println("Título: " + livro.getTitulo() + ", Autores: " + livro.getAutores() + ", Ano: " + livro.getAnoPublicacao() + ", Exemplares disponíveis: " + livro.getExemplaresDisponiveis());
            }
        }
    }
}
