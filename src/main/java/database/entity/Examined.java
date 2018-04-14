package database.entity;

import java.util.Objects;

public class Examined extends Entity {
    private String genotype;
    private String bacteriaClass;

    public Examined() {
    }

    public Examined(Integer id, String genotype, String bacteriaClass) {
        super(id);
        this.genotype = genotype;
        this.bacteriaClass = bacteriaClass;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getBacteriaClass() {
        return bacteriaClass;
    }

    public void setBacteriaClass(String bacteriaClass) {
        this.bacteriaClass = bacteriaClass;
    }

    @Override
    public String toString() {
        return "Examined{" +
                "id=" + getId() +
                ", genotype='" + genotype + '\'' +
                ", bacteriaClass='" + bacteriaClass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Examined examined = (Examined) o;
        return Objects.equals(genotype, examined.genotype) &&
                Objects.equals(bacteriaClass, examined.bacteriaClass);
    }

    @Override
    public int hashCode() {

        return Objects.hash(genotype, bacteriaClass);
    }
}
