package database.entity;

import java.util.Objects;

public class Flagella extends Entity {
    private Integer alpha;
    private Integer beta;
    private Integer number;

    public Flagella() {
    }

    public Flagella(Integer alpha, Integer beta, Integer number) {
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
    }

    public Flagella(Integer id, Integer alpha, Integer beta, Integer number) {
        super(id);
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
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

    @Override
    public String toString() {
        return "Flagella{" +
                "id=" + getId() +
                ", alpha=" + alpha +
                ", beta=" + beta +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flagella flagella = (Flagella) o;
        return Objects.equals(alpha, flagella.alpha) &&
                Objects.equals(beta, flagella.beta) &&
                Objects.equals(number, flagella.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(alpha, beta, number);
    }
}
