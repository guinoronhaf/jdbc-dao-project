package com.projeto.model.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of Department entity.
 * This class implements Serializable interface, once it is necessary that    its instancies can be transformed into byte sequencies.
 *
 * @author guinoronhaf
 */
public class Department implements Serializable {

    private Integer id;
    private String name;

    private static final long serialVersionUID = 1L;

    public Department() {}

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Department)) return false;
        Department oDepartment = (Department) o;
        return this.id.equals(oDepartment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }

}
