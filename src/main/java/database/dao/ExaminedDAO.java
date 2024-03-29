package database.dao;

import database.DbConnection;
import database.entity.Examined;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExaminedDAO implements EntityCRUD<Examined> {
    private DbConnection dbConnection;

    public ExaminedDAO(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Examined> getEntities() throws SQLException {
        List<Examined> examinedList = new ArrayList<>();
        String statement = "SELECT * FROM examined";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next())
            examinedList.add(new Examined(resultSet.getInt("id"),
                    resultSet.getString("genotype"),
                    resultSet.getString("class"),
                    resultSet.getInt("flagella_id"),
                    resultSet.getInt("toughness_id")));

        preparedStatement.close();
        return examinedList;
    }

    @Override
    public Examined saveEntity(Examined entity) throws SQLException {
        String statement = "INSERT INTO examined (genotype, class, flagella_id, toughness_id) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getGenotype());
        preparedStatement.setString(2, entity.getBacteriaClass());
        preparedStatement.setInt(3, entity.getFlagellaId());
        preparedStatement.setInt(4, entity.getToughnessId());
        Boolean methodSucceeded = preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next())
            entity.setId(resultSet.getInt(1));

        preparedStatement.close();
        return (methodSucceeded) ? entity : null;
    }

    @Override
    public Examined updateEntity(Examined entity) throws SQLException {
        String statement = "UPDATE examined SET genotype=?, class=?, flagella_id=?, toughness_id=? WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setString(1, entity.getGenotype());
        preparedStatement.setString(2, entity.getBacteriaClass());
        preparedStatement.setInt(3, entity.getFlagellaId());
        preparedStatement.setInt(4, entity.getToughnessId());
        preparedStatement.setInt(5, entity.getId());

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return (methodSucceeded) ? entity : null;
    }

    @Override
    public Examined getEntityById(int id) throws SQLException {
        String statement = "SELECT * FROM examined WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Examined examined = new Examined(resultSet.getInt("id"),
                resultSet.getString("genotype"),
                resultSet.getString("class"),
                resultSet.getInt("flagella_id"),
                resultSet.getInt("toughness_id"));


        preparedStatement.close();
        return examined;
    }

    public Examined getEntityByGenotype(String genotype) throws SQLException {
        String statement = "SELECT * FROM examined WHERE genotype=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setString(1, genotype);
        ResultSet resultSet = preparedStatement.executeQuery();

        Examined examined;
        if (resultSet.next())
            examined = new Examined(resultSet.getInt("id"),
                    resultSet.getString("genotype"),
                    resultSet.getString("class"),
                    resultSet.getInt("flagella_id"),
                    resultSet.getInt("toughness_id"));
        else
            examined = null;

        preparedStatement.close();
        return examined;
    }

    @Override
    public Boolean deleteEntityById(int id) throws SQLException {
        String statement = "DELETE FROM examined WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return methodSucceeded;
    }
}