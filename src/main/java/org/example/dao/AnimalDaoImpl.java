package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AnimalDaoImpl implements AnimalDao{

    private final Connection connection;

    public AnimalDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists animals (" +
                "id integer not null auto_increment, " +
                "name varchar(100), " +
                "species varchar(100), " +
                "primary key (id))");
    }

    @Override
    public void dropTable() throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute("drop table animals");
    }
}
