package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.ModelException;
import model.Pet;

public interface PetDAO {
    void insert(Pet pet) throws SQLException, ModelException;
    void update(Pet pet) throws SQLException, ModelException;
    void delete(int id) throws SQLException, ModelException;
    Pet getById(int id) throws SQLException, ModelException;
    List<Pet> getAll() throws SQLException, ModelException;
}
