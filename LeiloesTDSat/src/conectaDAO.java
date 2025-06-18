import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    /**
     * Método responsável por estabelecer a conexão com o banco de dados.
     * @return um objeto Connection se a conexão for bem-sucedida, ou null se falhar.
     */
    public Connection connectDB() {
        Connection conn = null;
        try {
            // VERIFIQUE O NOME DO SEU BANCO DE DADOS AQUI!
            // O seu parece ser "uc11". Se estiver errado, troque.
            String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false";
            String user = "root";
            String password = ""; // A senha do XAMPP/WAMP costuma ser vazia.

            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException erro) {
            // Em vez de apenas mostrar a mensagem, também imprimimos o erro no console
            // para facilitar o debug.
            System.err.println("Erro de conexão com o Banco de Dados!");
            erro.printStackTrace(); // Isso mostra o erro completo no console de Saída
            JOptionPane.showMessageDialog(null, "Erro em conectaDAO: Não foi possível conectar ao banco de dados.\n" + erro.getMessage());
        }
        return conn;
    }

    /**
     * Fecha a conexão com o banco de dados.
     * @param conn A conexão a ser fechada.
     */
    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    /**
     * Fecha a conexão e o PreparedStatement.
     * @param conn A conexão a ser fechada.
     * @param pstm O PreparedStatement a ser fechado.
     */
    public void closeConnection(Connection conn, PreparedStatement pstm) {
        closeConnection(conn);
        try {
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
        }
    }

    /**
     * Fecha a conexão, o PreparedStatement e o ResultSet.
     * @param conn A conexão a ser fechada.
     * @param pstm O PreparedStatement a ser fechado.
     * @param rs O ResultSet a ser fechado.
     */
    public void closeConnection(Connection conn, PreparedStatement pstm, ResultSet rs) {
        closeConnection(conn, pstm);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
        }
    }
}