package database.procedures_results;

import java.sql.Timestamp;
import java.util.Objects;

public class HistoryViewProcedureResultModel {
    private Timestamp date;
    private String genotype;
    private Integer alpha;
    private Integer beta;
    private Integer gamma;
    private String bacteriaClass;

    public HistoryViewProcedureResultModel() {
    }

    public HistoryViewProcedureResultModel(Timestamp date, String genotype, Integer alpha, Integer beta, Integer gamma, String bacteriaClass) {
        this.date = date;
        this.genotype = genotype;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.bacteriaClass = bacteriaClass;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getGenotype() {
        return genotype;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public Integer getBeta() {
        return beta;
    }

    public Integer getGamma() {
        return gamma;
    }

    public String getBacteriaClass() {
        return bacteriaClass;
    }

    @Override
    public String toString() {
        return "HistoryViewProcedureResultModel{" +
                "date=" + date +
                ", genotype='" + genotype + '\'' +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", gamma=" + gamma +
                ", bacteriaClass='" + bacteriaClass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryViewProcedureResultModel that = (HistoryViewProcedureResultModel) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(genotype, that.genotype) &&
                Objects.equals(alpha, that.alpha) &&
                Objects.equals(beta, that.beta) &&
                Objects.equals(gamma, that.gamma) &&
                Objects.equals(bacteriaClass, that.bacteriaClass);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, genotype, alpha, beta, gamma, bacteriaClass);
    }
}
