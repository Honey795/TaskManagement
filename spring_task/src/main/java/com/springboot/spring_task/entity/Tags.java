package com.springboot.spring_task.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "tag")
@Entity
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "Описание не должно быть пустым")
    private String description;
    @Id
    @NotNull(message = "Тэг не должен быть пустым")
    private String code;
    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<Tasks> tasks = new ArrayList<>();

    public Tags() {
    }

    public Tags(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Tasks> getTask() {
        return tasks;
    }

    public void setTask(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tags tags = (Tags) o;
        return Objects.equals(description, tags.description) && Objects.equals(code, tags.code) && Objects.equals(tasks, tags.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, code, tasks);
    }

    @Override
    public String toString() {
        return "Tags{" +
                "description='" + description + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
