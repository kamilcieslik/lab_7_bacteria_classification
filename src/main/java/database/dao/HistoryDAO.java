package database.dao;

import database.DbConnection;
import database.entity.Flagella;
import database.entity.History;
import database.procedures_results.HistoryViewProcedureResultModel;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO implements EntityCRUD<History> {
    private DbConnection dbConnection;

    public HistoryDAO(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<History> getEntities() throws SQLException {
        List<History> historyList = new ArrayList<>();
        String statement = "SELECT * FROM history";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next())
            historyList.add(new History(resultSet.getInt("id"),
                    resultSet.getDate("date"),
                    resultSet.getInt("examined_id")));

        preparedStatement.close();
        return historyList;
    }

    @Override
    public Boolean saveEntity(History entity) throws SQLException {
        String statement = "INSERT INTO history (date, examined_id) VALUES (?, ?)";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setDate(1, entity.getDate());
        preparedStatement.setInt(2, entity.getExaminedId());
        Boolean methodSucceeded = preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next())
            entity.setId(resultSet.getInt(1));

        preparedStatement.close();
        return methodSucceeded;
    }

    @Override
    public Boolean updateEntity(History entity) throws SQLException {
        String statement = "UPDATE history SET date=?, examined_id=? WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setDate(1, entity.getDate());
        preparedStatement.setInt(2, entity.getExaminedId());
        preparedStatement.setInt(3, entity.getId());

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return methodSucceeded;
    }

    @Override
    public History getEntityById(int id) throws SQLException {
        String statement = "SELECT * FROM history WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        History history = new History(resultSet.getInt("id"),
                resultSet.getDate("date"),
                resultSet.getInt("examined_id"));

        preparedStatement.close();
        return history;
    }

    @Override
    public Boolean deleteEntityById(int id) throws SQLException {
        String statement = "DELETE FROM history WHERE id=?";

        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(statement);
        preparedStatement.setInt(1, id);

        Boolean methodSucceeded = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return methodSucceeded;
    }

    public List<HistoryViewProcedureResultModel> getHistoryOfExaminedBacteria(String genotype) throws SQLException {
        List<HistoryViewProcedureResultModel> viewProcedureResultModelList = new ArrayList<>();
        CallableStatement callableStatement;

        if (genotype == null)
            callableStatement = dbConnection.getConnection().prepareCall("call GetHistoryOfExaminedBacteria()");
        else {
            callableStatement = dbConnection.getConnection().prepareCall("call GetHistoryOfExaminedBacteriaByGenotype()");
            callableStatement.setString("genotype", genotype);
        }
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next())
            viewProcedureResultModelList.add(new HistoryViewProcedureResultModel(rs.getDate("date"),
                    rs.getString("genotype"),
                    rs.getInt("alpha"),
                    rs.getInt("beta"),
                    rs.getInt("gama"),
                    rs.getString("class")));

        callableStatement.close();
        return viewProcedureResultModelList;
    }
}
