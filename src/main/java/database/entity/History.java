package database.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class History extends Entity {
    private Timestamp date;
    private Integer examinedId;

    public History() {
    }

    public History(Integer examinedId) {
        this.date = new Timestamp(System.currentTimeMillis());
        this.examinedId = examinedId;
    }

    public History(Integer id, Timestamp date, Integer examinedId) {
        super(id);
        this.date = date;
        this.examinedId = examinedId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getExaminedId() {
        return examinedId;
    }

    public void setExaminedId(Integer examinedId) {
        this.examinedId = examinedId;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + getId() +
                ", date=" + date +
                ", examinedId=" + examinedId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(date, history.date) &&
                Objects.equals(examinedId, history.examinedId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, examinedId);
    }
}
