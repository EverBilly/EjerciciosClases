package org.madhawa.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaAliens {

    Connection connection = null;
//    List<Alien> aliens;

    public ListaAliens() {
//        aliens = new ArrayList<>();
//
//        Alien a1 = new Alien();
//        a1.setId(1);
//        a1.setName("Paul");
//        a1.setPoints(100);
//
//        Alien a2 = new Alien();
//        a2.setId(2);
//        a2.setName("Navi");
//        a2.setPoints(60);
//
//        aliens.add(a1);
//        aliens.add(a2);

        String url = "jdbc:mysql://localhost:3306/aliens";
        String user = "ever";
        String pass = "Ever2020--";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    List<Alien> aliens = new ArrayList<>();

    public List<Alien> getAliens() {
        String sql = "select * from alien";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Alien alien = new Alien();
                alien.setId(resultSet.getInt(1));
                alien.setName(resultSet.getString(2));
                alien.setPoints(resultSet.getInt(3));

                aliens.add(alien);
            }
        } catch (Exception e) {
            System.out.println("Error1: " + e.getMessage());
        }

        return aliens;
    }

    public Alien getAlien(int id) {
        String sql = "select * from alien where id =" + id;
                Alien alien = new Alien();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()) {
                alien.setId(resultSet.getInt(1));
                alien.setName(resultSet.getString(2));
                alien.setPoints(resultSet.getInt(3));

                aliens.add(alien);
            }
        } catch (Exception e) {
            System.out.println("Error2: " + e.getMessage());
        }

        return alien;
    }

    public void create(Alien alien) {
        String sql = "insert into alien (name, points) values(?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, alien.getName());
            preparedStatement.setInt(2, alien.getPoints());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error3: " + e.getMessage());
        }
    }

    public void update(Alien alien, int id) {
        String sql = "update alien set name = ?, points = ? where id =" +id;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, alien.getName());
            preparedStatement.setInt(2, alien.getPoints());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error4: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "delete from alien where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error5: " + e.getMessage());
        }
    }
}
