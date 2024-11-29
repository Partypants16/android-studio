package edu.monash.week8.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {

    // private class variable to hold reference to DAO
    private StudentDAO studentDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Student>> allStudentsLiveData;

    // constructor to initialise the repository class
    StudentRepository(Application application) {
        // get reference/instance of the database
        StudentDatabase db = StudentDatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        studentDAO = db.studentDAO();

        // once the class is initialised get all the items in the form of LiveData
        allStudentsLiveData = studentDAO.getAllStudents();
    }

    /**
     * Repository method to get all cards
     * @return LiveData of type List<Item>
     */
    LiveData<List<Student>> getAllStudents() {
        return allStudentsLiveData;
    }


    /**
     * Repository method to insert one single item
     * @param student object containing details of new Item to be inserted
     */
    void insert(Student student) {
        StudentDatabase.databaseWriteExecutor.execute(() -> studentDAO.addStudent(student));
    }

    /**
     * Repository method to delete all records
     */
    void deleteAll() {
        StudentDatabase.databaseWriteExecutor.execute(() -> studentDAO.deleteAllStudents());
    }
}
