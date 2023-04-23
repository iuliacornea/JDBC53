package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.dao.*;
import org.example.model.Animal;
import org.example.model.Car;
import org.example.model.Food;

import java.sql.*;
import java.util.List;
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
            CarDao carDao = new CarDaoImpl(connection);

            // statement <- folosim pentru a trimite comenzi sql la serverul de Baze de Date
            Statement statement = connection.createStatement();

            animalDao.createTable();
            foodDao.createTable();
            carDao.createTable();
            LOGGER.info("Tables created successfully");


            animalDao.create(new Animal(null, "Lucky", "Dog"));
            animalDao.create(new Animal(null, "Lucky", "Dog"));

            Date expirationDate = Date.valueOf("2024-10-12");
            foodDao.create(new Food(null, "ciocolata", "ciocolată de casă", 550, expirationDate));
            foodDao.create(new Food(null, "alune", "pungă de 500g de alune prajite", 650, expirationDate));

            carDao.createCar(new Car(null, "Dacie", Date.valueOf("2010-08-27")));
            carDao.createCar(new Car(null, "Renaul", Date.valueOf("2020-10-11")));
            carDao.createCar(new Car(null, "Dacie", Date.valueOf("2010-08-27")));
            carDao.createCar(new Car(null, "Dacie", Date.valueOf("2010-08-27")));
            LOGGER.info("Data insertion was successful");


            System.out.println("Cars:");
            List<Car> cars = carDao.readAllCars();
            for(Car c : cars) {
                System.out.println(c);
            }

            carDao.updateCar(new Car(2, "Toyota", Date.valueOf("2023-10-03")));
            carDao.deleteCar(1);


            System.out.println("Cars:");
            cars = carDao.readAllCars();
            for(Car c : cars) {
                System.out.println(c);
            }

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
                System.out.println(rs.getInt(1) + ". "
                        + rs.getString(2) + " - "
                + rs.getString(3) + " - "
                + rs.getInt(4) + "kcal per 100g - "
                + "expiră la data " + rs.getDate(5));
            }


            carDao.dropTable();
            animalDao.dropTable();
            foodDao.dropTable();
            LOGGER.info("Tables dropped successfully");

        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
            sqlException.printStackTrace();
        }
    }

}