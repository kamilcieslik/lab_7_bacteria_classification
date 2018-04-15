package database.dao;

import database.DbConnection;
import database.entity.Toughness;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToughnessDAO implements EntityCRUD<Toughness> {
    private DbConnection dbConnection;

    public ToughnessDAO(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Toughness> getEntities() throws SQLException {
        List<Toughness> toughnessList = new ArrayList<>();
        String statement = "SELECT * FROM flagella";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next())
            toughnessList.add(new Toughness(resultSet.getInt("id"),
                    resultSet.getInt("beta"),
                    resultSet.getInt("gamma"),
                    resultSet.getString("rank"),
                    resultSet.getInt("examined_id")));

        preparedStatement.close();
        return toughnessList;
    }

    @Override
    public Toughness saveEntity(Toughness entity) throws SQLException {
        String statement = "INSERT INTO toughness (beta, gamma, rank, examined_id) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, entity.getBeta());
        preparedStatement.setInt(2, entity.getGamma());
        preparedStatement.setString(3, entity.getRank());
        preparedStatement.setInt(4, entity.getExaminedId());
        Boolean methodSucceeded = preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next())
            entity.setId(resultSet.getInt(1));

        preparedStatement.close();
        return (methodSucceeded) ? entity : null;
    }

    @Override
    public Toughness updateEntity(Toughness entity) throws SQLException {
        String statement = "UPDATE toughness SET beta=?, gamma=?, rank=?, examined_id=? WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, entity.getBeta());
        preparedStatement.setInt(2, entity.getGamma());
        preparedStatement.setString(3, entity.getRank());
        preparedStatement.setInt(4, entity.getExaminedId());
        preparedStatement.setInt(5, entity.getId());

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return (methodSucceeded) ? entity : null;
    }

    @Override
    public Toughness getEntityById(int id) throws SQLException {
        String statement = "SELECT * FROM toughness WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Toughness toughness = new Toughness(resultSet.getInt("id"),
                resultSet.getInt("beta"),
                resultSet.getInt("gamma"),
                resultSet.getString("rank"),
                resultSet.getInt("examined_id"));

        preparedStatement.close();
        return toughness;
    }

    @Override
    public Boolean deleteEntityById(int id) throws SQLException {
        String statement = "DELETE FROM toughness WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return methodSucceeded;
    }
}
