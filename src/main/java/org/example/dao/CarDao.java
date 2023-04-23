package org.example.dao;

import org.example.model.Car;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {

    void createTable() throws SQLException;

    void createCar(Car car) throws SQLException;

    List<Car> readAllCars() throws SQLException;

    void updateCar(Car updatedCar) throws SQLException;

    void deleteCar(Integer carId) throws SQLException;

    void dropTable() throws SQLException;
}
