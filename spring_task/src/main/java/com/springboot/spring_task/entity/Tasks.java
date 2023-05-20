package com.springboot.spring_task.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Table(name = "task")
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="kind_id", nullable = false)
    private Kinds kinds;
    //наименование
    @NotNull(message = "Название не должно быть пустым")
    private String name;
    //описание
    private String description;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Дата не должна быть пустой")
    @Column(name = "plan_date")
    private Date planDate;
    //тег
    private String tag;

    public Tasks() {
    }

    public Tasks( Kinds kinds, String name, String description, Date planDate, String tag) {
        this.kinds = kinds;
        this.name = name;
        this.description = description;
        this.planDate = planDate;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Kinds getKind() {
        return kinds;
    }

    public void setKind(Kinds kinds) {
        this.kinds = kinds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getTag() {return tag;}

    public void setTag(String tag) {this.tag = tag;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return Objects.equals(id, tasks.id) && Objects.equals(kinds, tasks.kinds) && Objects.equals(name, tasks.name) && Objects.equals(description, tasks.description) && Objects.equals(planDate, tasks.planDate) && Objects.equals(tag, tasks.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kinds, name, description, planDate, tag);
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", kinds=" + kinds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", plan_date=" + planDate +
                ", tag_id=" + tag +
                '}';
    }
}
