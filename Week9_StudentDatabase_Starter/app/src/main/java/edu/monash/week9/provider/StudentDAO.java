package edu.monash.week9.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDAO {

    @Query("select * from students")
    LiveData<List<Student>> getAllStudents();

    @Insert
    void addStudent(Student student);

    @Query("DELETE FROM students")
    void deleteAllStudents();
}
