package database;

import database.dao.ExaminedDAO;
import database.dao.FlagellaDAO;
import database.dao.ToughnessDAO;
import database.entity.Examined;
import database.entity.Flagella;
import database.entity.Toughness;
import java.sql.SQLException;
import java.util.List;

public class BacteriaClassifierService {
    private FlagellaDAO flagellaDAO;
    private ToughnessDAO toughnessDAO;
    private ExaminedDAO examinedDAO;

    public BacteriaClassifierService(DbConnection dbConnection) {
        flagellaDAO = new FlagellaDAO(dbConnection);
        toughnessDAO = new ToughnessDAO(dbConnection);
        examinedDAO = new ExaminedDAO(dbConnection);
    }

    // Flagella:
    public List<Flagella> getFlagellaList() throws SQLException {
        return flagellaDAO.getEntities();
    }

    public Boolean saveFlagella(Flagella flagella) throws SQLException {
        return flagellaDAO.saveEntity(flagella);
    }

    public Boolean updateFlagella(Flagella flagella) throws SQLException {
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

    public Boolean saveToughness(Toughness toughness) throws SQLException {
        return toughnessDAO.saveEntity(toughness);
    }

    public Boolean updateToughness(Toughness toughness) throws SQLException {
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

    public Boolean saveExamined(Examined toughness) throws SQLException {
        return examinedDAO.saveEntity(toughness);
    }

    public Boolean updateExamined(Examined toughness) throws SQLException {
        return examinedDAO.updateEntity(toughness);
    }

    public Examined getExaminedById(Integer id) throws SQLException {
        return examinedDAO.getEntityById(id);
    }

    public Boolean deleteExaminedById(Integer id) throws SQLException {
        return examinedDAO.deleteEntityById(id);
    }
}
