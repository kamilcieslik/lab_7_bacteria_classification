package database;

import database.dao.ExaminedDAO;
import database.dao.FlagellaDAO;
import database.dao.HistoryDAO;
import database.dao.ToughnessDAO;
import database.entity.Examined;
import database.entity.Flagella;
import database.entity.History;
import database.entity.Toughness;
import database.procedures_results.HistoryViewProcedureResultModel;

import java.sql.SQLException;
import java.util.List;

public class BacteriaClassifierService {
    private FlagellaDAO flagellaDAO;
    private ToughnessDAO toughnessDAO;
    private ExaminedDAO examinedDAO;
    private HistoryDAO historyDAO;

    public BacteriaClassifierService(DbConnection dbConnection) {
        flagellaDAO = new FlagellaDAO(dbConnection);
        toughnessDAO = new ToughnessDAO(dbConnection);
        examinedDAO = new ExaminedDAO(dbConnection);
        historyDAO = new HistoryDAO(dbConnection);
    }

    // Flagella:
    public List<Flagella> getFlagellaList() throws SQLException {
        return flagellaDAO.getEntities();
    }

    public Flagella saveFlagella(Flagella flagella) throws SQLException {
        return flagellaDAO.saveEntity(flagella);
    }

    public Flagella updateFlagella(Flagella flagella) throws SQLException {
        return flagellaDAO.updateEntity(flagella);
    }

    public Flagella getFlagellaById(Integer id) throws SQLException {
        return flagellaDAO.getEntityById(id);
    }

    public Boolean deleteFlagellaById(Integer id) throws SQLException {
        return flagellaDAO.deleteEntityById(id);
    }

    // Toughness:
    public List<Toughness> getToughnessList() throws SQLException {
        return toughnessDAO.getEntities();
    }

    public Toughness saveToughness(Toughness toughness) throws SQLException {
        return toughnessDAO.saveEntity(toughness);
    }

    public Toughness updateToughness(Toughness toughness) throws SQLException {
        return toughnessDAO.updateEntity(toughness);
    }

    public Toughness getToughnessById(Integer id) throws SQLException {
        return toughnessDAO.getEntityById(id);
    }

    public Boolean deleteToughnessById(Integer id) throws SQLException {
        return toughnessDAO.deleteEntityById(id);
    }

    // Examined:
    public List<Examined> getExaminedList() throws SQLException {
        return examinedDAO.getEntities();
    }

    public Examined saveExamined(Examined toughness) throws SQLException {
        return examinedDAO.saveEntity(toughness);
    }

    public Examined updateExamined(Examined toughness) throws SQLException {
        return examinedDAO.updateEntity(toughness);
    }

    public Examined getExaminedById(Integer id) throws SQLException {
        return examinedDAO.getEntityById(id);
    }

    public Boolean deleteExaminedById(Integer id) throws SQLException {
        return examinedDAO.deleteEntityById(id);
    }

    // History:
    public List<History> getHistoryList() throws SQLException {
        return historyDAO.getEntities();
    }

    public History saveHistory(History history) throws SQLException {
        return historyDAO.saveEntity(history);
    }

    public History updateHistory(History history) throws SQLException {
        return historyDAO.updateEntity(history);
    }

    public History getHistoryById(Integer id) throws SQLException {
        return historyDAO.getEntityById(id);
    }

    public Boolean deleteHistoryById(Integer id) throws SQLException {
        return historyDAO.deleteEntityById(id);
    }

    public List<HistoryViewProcedureResultModel> getHistoryOfExaminedBacteria(String genotype) throws SQLException {
        return historyDAO.getHistoryOfExaminedBacteria(genotype);
    }

    // Other:
    public Examined classifyTheBacteria(String genotype, List<Flagella> flagellaList, List<Toughness> toughnessList){
        // TODO:

        return null;
    }
}
