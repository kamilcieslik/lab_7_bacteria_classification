package database.entity;

import java.util.Objects;

public class Toughness extends Entity {
    private Integer beta;
    private Integer gamma;
    private String rank;
    private Integer examinedId;

    public Toughness() {
    }

    public Toughness(Integer id, Integer beta, Integer gamma, String rank, Integer examinedId) {
        super(id);
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
        this.examinedId = examinedId;
    }

    public Integer getBeta() {
        return beta;
    }

    public void setBeta(Integer beta) {
        this.beta = beta;
    }

    public Integer getGamma() {
        return gamma;
    }

    public void setGamma(Integer gamma) {
        this.gamma = gamma;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getExaminedId() {
        return examinedId;
    }

    public void setExaminedId(Integer examinedId) {
        this.examinedId = examinedId;
    }

    @Override
    public String toString() {
        return "Toughness{" +
                "id=" + getId() +
                ", beta=" + beta +
                ", gamma=" + gamma +
                ", rank='" + rank + '\'' +
                ", examinedId=" + examinedId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toughness toughness = (Toughness) o;
        return Objects.equals(beta, toughness.beta) &&
                Objects.equals(gamma, toughness.gamma) &&
                Objects.equals(rank, toughness.rank) &&
                Objects.equals(examinedId, toughness.examinedId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(beta, gamma, rank, examinedId);
    }
}
