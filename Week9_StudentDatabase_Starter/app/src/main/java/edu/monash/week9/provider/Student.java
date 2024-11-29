package edu.monash.week9.provider;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity(tableName = Student.TABLE_NAME)
public class Student {
    public static final String TABLE_NAME = "students";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "studentId")
    private int studentId;

    @ColumnInfo(name = "studentCountry")
    private String studentCountry;

    @ColumnInfo(name = "active")
    private boolean isRecordActive;

    public Student(String name, int studentId, boolean isRecordActive, String studentCountry) {
        this.name = name;
        this.studentId = studentId;
        this.isRecordActive = isRecordActive;
        this.studentCountry = studentCountry;
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

    public String getStudentCountry() {
        return studentCountry;
    }
}
