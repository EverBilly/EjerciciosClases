import java.sql.*;

public class ConexionMysql {

    private static Connection connection = null;
    private static CallableStatement  callableStatement = null;
    private static ResultSet resultSet = null;
    private static String url = "jdbc:mysql://localhost:3306/java?serverTimezone=GMT", user = "root", pass = "";

    public static void main(String[] args) {

        try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);

                callableStatement = connection.prepareCall("{call MostrarUsuarios(?)}");
                callableStatement.setInt(1, 2);
                resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                    System.out.println(resultSet.getString("nombre") + " " +
                            resultSet.getString("apellido") + " " +
                            resultSet.getString("telefono") + " " +
                            resultSet.getInt("id"));
                }
                connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
