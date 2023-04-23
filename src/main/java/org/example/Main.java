package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.dao.AnimalDao;
import org.example.dao.AnimalDaoImpl;
import org.example.dao.FoodDao;
import org.example.dao.FoodDaoImpl;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String dbLocation = "localhost:3306";
        String dbName = "jdbc_db";
        String dbUser = "root";
        String dbPassword = "root"; // parola voastră pentru baza de date

        // MysqlDataSource <- vine din mysql connector și o folosim ca să configurăm conexiunea la baza de date
        MysqlDataSource dataSource = new MysqlDataSource();
        // Formatul pentru url-ul de conectare la baza de date
        // jdbc:mysql://<<locația server-ului de baze de data>>/<<numele bazei de date>>
        // jdbc:mysql://localhost:3306/jdbc_db
        dataSource.setUrl("jdbc:mysql://" + dbLocation + "/" + dbName);
        dataSource.setUser(dbUser);
        dataSource.setPassword(dbPassword);

        try {
            LOGGER.log(Level.INFO, "Trying to connect to DB");
            Connection connection = dataSource.getConnection();
            LOGGER.log(Level.INFO, "Connection successful");

            AnimalDao animalDao = new AnimalDaoImpl(connection);
            FoodDao foodDao = new FoodDaoImpl(connection);

            // statement <- folosim pentru a trimite comenzi sql la serverul de Baze de Date
            Statement statement = connection.createStatement();

            animalDao.createTable();
            foodDao.createTable();
            LOGGER.info("Tables created successfully");

            // putem sa refolosim obiectul statement pentru a trimite alte instructiuni sql către baza de date
            statement.execute("insert into animals (name, species) values (\"Lucky\", \"Dog\")");
            statement.execute("insert into animals (name, species) values (\"Lucky\", \"Dog\")");
            LOGGER.info("Data insertion was successful");

            statement.execute("update animals set name = \"Bubu\" where id = 2");

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into food (name, description, calories_per_100, expiration_date) values (?, ?, ?, ?)");
            preparedStatement.setString(1, "ciocolată");
            preparedStatement.setString(2, "ciocolată de casă");
            preparedStatement.setInt(3, 550);
            Date expirationDate = Date.valueOf("2024-10-12");
            preparedStatement.setDate(4, expirationDate);
            // întotdeauna trebuie rulat .execute() dacă vrem să fie executat codul sql pe baza de date
            // comanda care trimite instrucțiunile sql către server (instrucțiunile pregătite mai sus)
            preparedStatement.execute();

            preparedStatement.setString(1, "alune");
            preparedStatement.setString(2, "pungă de 500g de alune prajite");
            preparedStatement.setInt(3, 600);
            preparedStatement.setDate(4, expirationDate);
            preparedStatement.execute();


            ResultSet rs = statement.executeQuery("SELECT * FROM animals");
            System.out.println("Animals:");
            while (rs.next() == true ) {
                System.out.println("Id: " + rs.getInt(1));
                System.out.println("Name: " + rs.getString((2)));
                System.out.println("Species: " + rs.getString((3)));
            }

            // display all foods :D
            rs = statement.executeQuery("SELECT * FROM food order by calories_per_100 desc");
            System.out.println("Foods:");
            while(rs.next() == true) {
//            Food:
//              1. ciocolata - ciocolata de casa - 550kcal per 100g - expiră la 2024-10-12
//              2. alune - pungă de 500g de alune prajite - 600kcal per 100g - expiră la 2024-10-12

                System.out.println(rs.getInt(1) + ". "
                        + rs.getString(2) + " - "
                + rs.getString(3) + " - "
                + rs.getInt(4) + "kcal per 100g - "
                + "expiră la data " + rs.getDate(5));
            }

            animalDao.dropTable();
            foodDao.dropTable();
            LOGGER.info("Tables dropped successfully");

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }
}