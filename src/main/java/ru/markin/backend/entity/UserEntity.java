package ru.markin.backend.entity;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_TAB")
public class UserEntity implements Serializable, Comparable<UserEntity> {
    private static final long serialVersionUID = -6564962875578953355L;

    private Integer id;
    private String name;
    private String surname;
    private Date birthdayDate;

    @Id
    @Column(name = "ID", length = 32)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @SequenceGenerator(name = "userSeq", sequenceName = "USER_TAB_ID_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(@Nonnull final Integer id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 32, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {
        this.name = name;
    }

    @Column(name = "SURNAME", length = 32, nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(@Nonnull final String surname) {
        this.surname = surname;
    }

    @Column(name = "BIRTHDAY_DATE", nullable = false)
    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(@Nonnull final Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    @Override
    public boolean equals(@Nonnull final Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        final UserEntity that = (UserEntity) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(@Nonnull final UserEntity o) {
        final int result = this.surname.compareTo(o.getSurname());
        return result != 0 ? result : this.name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdayDate=" + birthdayDate +
                '}';
    }

    public enum Col {
        ID("id"),
        NAME("name"),
        SURNAME("surname"),
        BIRTHDAY_DATE("birthdayDate");

        private final String property;

        Col(final String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }
    }
}
