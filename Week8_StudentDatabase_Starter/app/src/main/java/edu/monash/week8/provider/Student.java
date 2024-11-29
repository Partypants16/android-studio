package edu.monash.week8.provider;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "studentId")
    private int studentId;

    @ColumnInfo(name = "active")
    private boolean isRecordActive;

    public Student(String name, int studentId, boolean isRecordActive) {
        this.name = name;
        this.studentId = studentId;
        this.isRecordActive = isRecordActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStudentId() {
        return studentId;
    }

    public boolean isRecordActive() {
        return isRecordActive;
    }


}
