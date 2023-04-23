package org.example.dao;

import java.sql.SQLException;

// Animal Data Access Object <- clasă pentru a accesa date din "animals"
// manipulare structura baza de date (creare si stergere tabel)
// manipulare date - CRUD
public interface AnimalDao {

    // creat table
    void createTable() throws SQLException;

    // adaugare date            CREATE animals

    // gasire date              READ animals

    // modificare date          UPDATE animals

    // stergere date            DELETE animals

    // șters tabel
    void dropTable() throws SQLException;

}
