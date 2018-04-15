package database.entity;

import java.util.Objects;

public class Flagella extends Entity {
    private Integer alpha;
    private Integer beta;
    private Integer number;
    private Integer examinedId;

    public Flagella() {
    }

    public Flagella(Integer id, Integer alpha, Integer beta, Integer number, Integer examinedId) {
        super(id);
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
        this.examinedId = examinedId;
    }

    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    public Integer getBeta() {
        return beta;
    }

    public void setBeta(Integer beta) {
        this.beta = beta;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getExaminedId() {
        return examinedId;
    }

    public void setExaminedId(Integer examinedId) {
        this.examinedId = examinedId;
    }

    @Override
    public String toString() {
        return "Flagella{" +
                "id=" + getId() +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", number=" + number +
                ", examinedId=" + examinedId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flagella flagella = (Flagella) o;
        return Objects.equals(alpha, flagella.alpha) &&
                Objects.equals(beta, flagella.beta) &&
                Objects.equals(number, flagella.number) &&
                Objects.equals(examinedId, flagella.examinedId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alpha, beta, number, examinedId);
    }
}
