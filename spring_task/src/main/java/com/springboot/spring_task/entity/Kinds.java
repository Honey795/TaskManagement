package com.springboot.spring_task.entity;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "kind")
@Entity
public class Kinds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Long priority;
    public Kinds() {
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kinds kinds = (Kinds) o;
        return Objects.equals(id, kinds.id) && Objects.equals(description, kinds.description) && Objects.equals(priority, kinds.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, priority);
    }

    @Override
    public String toString() {
        return "Kinds{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}
