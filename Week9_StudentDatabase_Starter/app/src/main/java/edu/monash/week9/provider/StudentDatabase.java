package edu.monash.week9.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {

    // database name, this is important as data is contained inside a file named "card_database"
    public static final String STUDENT_DATABASE = "student_database_w9";

    // reference to DAO, here RoomDatabase parent class will implement this interface
    public abstract StudentDAO studentDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile StudentDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Since this class is an absract class, to get the database reference we would need
     * to implement a way to get reference to the database.
     *
     * @param context Application of Activity Context
     * @return a reference to the database for read and write operation
     */
    static StudentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StudentDatabase.class, STUDENT_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}