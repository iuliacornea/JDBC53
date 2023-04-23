package org.example.dao;

import org.example.model.Food;

import java.sql.*;

public class FoodDaoImpl implements FoodDao{

    private final Connection connection;

    public FoodDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table food (" +
                "id integer auto_increment, " +
                "name varchar(100), " +
                "description varchar(100)," +
                "calories_per_100 integer, " +
                "expiration_date date, " +
                "primary key (id) )");
    }

    @Override
    public void create(Food food) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into food (name, description, calories_per_100, expiration_date) values (?, ?, ?, ?)");
        preparedStatement.setString(1, food.getName());
        preparedStatement.setString(2, food.getDescription());
        preparedStatement.setInt(3, food.getCaloriesPer100());
        preparedStatement.setDate(4, food.getExpirationDate());
        // întotdeauna trebuie rulat .execute() dacă vrem să fie executat codul sql pe baza de date
        // comanda care trimite instrucțiunile sql către server (instrucțiunile pregătite mai sus)
        preparedStatement.execute();
    }

    @Override
    public void dropTable() throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute("drop table food");
    }
}
