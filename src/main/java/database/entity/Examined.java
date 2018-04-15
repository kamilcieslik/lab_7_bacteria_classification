package database.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Examined extends Entity {
    @XmlAttribute(name = "bacteriaGenotype")
    private String genotype;
    @XmlAttribute(name = "bacteriaClass")
    private String bacteriaClass;
    private Integer flagellaId;
    private Integer toughnessId;

    public Examined() {
    }

    public Examined(Integer id, String genotype, String bacteriaClass) {
        super(id);
        this.genotype = genotype;
        this.bacteriaClass = bacteriaClass;
    }

    public Examined(Integer id, String genotype, String bacteriaClass, Integer flagellaId, Integer toughnessId) {
        super(id);
        this.genotype = genotype;
        this.bacteriaClass = bacteriaClass;
        this.flagellaId = flagellaId;
        this.toughnessId = toughnessId;
    }

    public Examined(String genotype, String bacteriaClass, Integer flagellaId, Integer toughnessId) {
        this.genotype = genotype;
        this.bacteriaClass = bacteriaClass;
        this.flagellaId = flagellaId;
        this.toughnessId = toughnessId;
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

    public Integer getFlagellaId() {
        return flagellaId;
    }

    public void setFlagellaId(Integer flagellaId) {
        this.flagellaId = flagellaId;
    }

    public Integer getToughnessId() {
        return toughnessId;
    }

    public void setToughnessId(Integer toughnessId) {
        this.toughnessId = toughnessId;
    }

    @Override
    public String toString() {
        return "Examined{" +
                "genotype='" + genotype + '\'' +
                ", bacteriaClass='" + bacteriaClass + '\'' +
                ", flagellaId=" + flagellaId +
                ", toughnessId=" + toughnessId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Examined examined = (Examined) o;
        return Objects.equals(genotype, examined.genotype) &&
                Objects.equals(bacteriaClass, examined.bacteriaClass) &&
                Objects.equals(flagellaId, examined.flagellaId) &&
                Objects.equals(toughnessId, examined.toughnessId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(genotype, bacteriaClass, flagellaId, toughnessId);
    }
}
