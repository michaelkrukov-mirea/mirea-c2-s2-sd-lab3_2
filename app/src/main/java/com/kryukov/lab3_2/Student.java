package com.kryukov.lab3_2;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "students")
public class Student {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String lastname;

    @DatabaseField(canBeNull = false)
    private String middlename;

    @DatabaseField(canBeNull = false)
    private Date added;

    public Student() {};

    public int getId() {
        return id;
    }

    public Date getAdded() {
        return added;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setAdded(Date added) {
        this.added = added;
    }
}
