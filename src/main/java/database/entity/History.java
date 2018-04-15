package database.entity;

import java.sql.Date;
import java.util.Objects;

public class History extends Entity {
    private Date date;
    private Integer examinedId;

    public History() {
    }

    public History(Integer id, Date date, Integer examinedId) {
        super(id);
        this.date = date;
        this.examinedId = examinedId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
