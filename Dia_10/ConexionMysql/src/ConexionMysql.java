import java.sql.*;

public class ConexionMysql {

    private static Connection connection = null;
    private static CallableStatement  callableStatement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String url = "jdbc:mysql://localhost:3306/java?serverTimezone=GMT", user = "root", pass = "";

    public static void main(String[] args) {
        EjercicioCallableStatement();
        EjercicioPreparedStatement();

    }

    public static void EjercicioCallableStatement() {

        long inicio = System.currentTimeMillis();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);

            //Mostrar Usuarios
            //callableStatement = connection.prepareCall("{call MostrarUsuarios()}");

            //Mostrar Usuario por Id
            callableStatement = connection.prepareCall("{call MostrarUsuarioId(?)}");
            callableStatement.setInt(1, 4);
            resultSet = callableStatement.executeQuery();


            while (resultSet.next()) {
                System.out.println(resultSet.getString("nombre") + " " +
                        resultSet.getString("apellido") + " " +
                        resultSet.getString("telefono") + " " +
                        resultSet.getInt("id"));
            }

            //Insertar Usuario
            callableStatement = connection.prepareCall("{call InsertarUsuario(?,?,?)}");
            callableStatement.setString(1, "Maria");
            callableStatement.setString(2, "Lopez");
            callableStatement.setString(3, "44342110");
            resultSet = callableStatement.executeQuery();
            System.out.println("Insert");

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long fin = System.currentTimeMillis();
        System.out.println("CallableStatement: " + (fin-inicio));
    }

    public static void EjercicioPreparedStatement() {
        long inicio = System.currentTimeMillis();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            preparedStatement = connection.prepareStatement("{call InsertarUsuario(?,?,?)}");
//            String sql = "insert into usuario (nombre, apellido, telefono) values(?,?,?)";
//            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < 5500; i++) {
                preparedStatement.setString(1, "Juan");
                preparedStatement.setString(2, "Herrera");
                preparedStatement.setString(3, "44662233");
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long fin = System.currentTimeMillis();
        System.out.println("PreparedStatement: " + (fin-inicio));
    }
}
