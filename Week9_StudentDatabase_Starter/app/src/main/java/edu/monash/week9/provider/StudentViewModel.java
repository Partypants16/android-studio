package edu.monash.week9.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;


/**
 * ViewModel class is used for pre-processing the data,
 * before passing it to the controllers (Activity or Fragments). ViewModel class should not hold
 * direct reference to database. ViewModel class relies on repository class, hence the database is
 * accessed using the Repository class.
 */
public class StudentViewModel extends AndroidViewModel {
    // reference to StudentRepository
    private StudentRepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<Student>> allStudentsLiveData;

    public StudentViewModel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new StudentRepository(application);

        // get all items by calling method defined in repository class
        allStudentsLiveData = repository.getAllStudents();
    }

    /**
     * ViewModel method to get all students
     * @return LiveData of type List<Student>
     */
    public LiveData<List<Student>> getAllStudents() {
        return allStudentsLiveData;
    }


    /**
     * ViewModel method to insert one single item
     * @param student object containing details of new Item to be inserted
     */
    public void insert(Student student) {
        repository.insert(student);
    }

    /**
     * ViewModel method to delete all records
     */
    public void deleteAll() {
        repository.deleteAll();
    }
}
