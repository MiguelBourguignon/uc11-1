import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        conn = new conectaDAO().connectDB(); // Obtém a conexão

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor()); // Correto: usando setDouble para o tipo Double
            prep.setString(3, produto.getStatus());
            prep.execute();

            // A mensagem de sucesso será exibida na VIEW para melhor experiência do usuário
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro em ProdutosDAO (cadastrarProduto): " + erro.getMessage());
        } finally {
            // Garante que a conexão e o PreparedStatement sejam fechados
            new conectaDAO().closeConnection(conn, prep);
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT id, nome, valor, status FROM produtos";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor")); // Correto: usando getDouble
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro em ProdutosDAO (listarProdutos): " + erro.getMessage());
        } finally {
            new conectaDAO().closeConnection(conn, prep, resultset);
        }
        return lista;
    }

    public void venderProduto(int idProduto) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, idProduto);

            int rowsAffected = prep.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Produto com ID " + idProduto + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro em ProdutosDAO (venderProduto): " + erro.getMessage());
        } finally {
            new conectaDAO().closeConnection(conn, prep);
        }
    }
// NOVO MÉTODO PARA LISTAR APENAS OS VENDIDOS
public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    // A única mudança é o "WHERE status = 'Vendido'" no final da query
    String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'";
    
    ArrayList<ProdutosDTO> lista = new ArrayList<>();
    conn = new conectaDAO().connectDB();

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getDouble("valor"));
            produto.setStatus(resultset.getString("status"));
            lista.add(produto);
        }
    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro em ProdutosDAO (listarProdutosVendidos): " + erro.getMessage());
    } finally {
        new conectaDAO().closeConnection(conn, prep, resultset);
    }
    return lista;
}
} // Fim da classe ProdutosDAO