package model.dao;

import java.sql.*;
import java.util.*;

import model.ModelException;
import model.Pet;

public class MySQLPetDAO implements PetDAO {

    @Override
    public void insert(Pet pet) throws ModelException {
        String sql = "INSERT INTO pets (name, species, birthdate, owner_id, weight) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MySQLConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getName());
            stmt.setString(2, pet.getSpecies());
            stmt.setDate(3, pet.getBirthdate());
            stmt.setInt(4, pet.getOwnerId());
            stmt.setFloat(5, pet.getWeight());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ModelException("Erro ao inserir pet", e);
        }
    }

    @Override
    public void update(Pet pet) throws ModelException {
        String sql = "UPDATE pets SET name = ?, species = ?, birthdate = ?, owner_id = ?, weight = ? WHERE id = ?";
        try (Connection conn = MySQLConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getName());
            stmt.setString(2, pet.getSpecies());
            stmt.setDate(3, pet.getBirthdate());
            stmt.setInt(4, pet.getOwnerId());
            stmt.setFloat(5, pet.getWeight());
            stmt.setInt(6, pet.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ModelException("Erro ao atualizar pet", e);
        }
    }

    @Override
    public void delete(int id) throws ModelException {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (Connection conn = MySQLConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ModelException("Erro ao deletar pet", e);
        }
    }

    @Override
    public Pet getById(int id) throws ModelException {
        String sql = "SELECT * FROM pets WHERE id = ?";
        try (Connection conn = MySQLConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createPetFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new ModelException("Erro ao buscar pet por ID", e);
        }
        return null;
    }

    @Override
    public List<Pet> getAll() throws ModelException {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets";

        try (Connection conn = MySQLConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pets.add(createPetFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new ModelException("Erro ao listar pets", e);
        }
        return pets;
    }

    private Pet createPetFromResultSet(ResultSet rs) throws SQLException {
        Pet pet = new Pet();
        pet.setId(rs.getInt("id"));
        pet.setName(rs.getString("name"));
        pet.setSpecies(rs.getString("species"));
        pet.setBirthdate(rs.getDate("birthdate"));
        pet.setOwnerId(rs.getInt("owner_id"));
        pet.setWeight(rs.getFloat("weight"));
        return pet;
    }
}
