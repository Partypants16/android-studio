package edu.monash.week9.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class StudentDBContentProvider extends ContentProvider {
    StudentDatabase db;

    // URI of this content provider exactly same as specified in A.1
    public static final String CONTENT_AUTHORITY = "fit2081.student.db.provider";

    // Using the content authority above form the absolute path to this content provider
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Uri Matcher helps us to match requested URI with the implemented URIs of this content provider
    private static final UriMatcher sUriMatcher = createUriMatcher();

    // to differentiate between single vs multiple row operations
    private static final int MULTIPLE_ROWS_STUDENTS = 1;
    private static final int SINGLE_ROW_STUDENTS = 2;

    /**
     * Define URI matched method, which helps us to match
     * requested URI with the implemented URIs of this content provider.
     *
     * @return UriMatcher reference, which later will be used to get matched code
     */
    private static UriMatcher createUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // sUriMatcher will return code 1 if uri like authority/students
        uriMatcher.addURI(authority, Student.TABLE_NAME, MULTIPLE_ROWS_STUDENTS);

        // sUriMatcher will return code 2 if uri like e.g. authority/students/7 (where 7 is id of row in students table)
        uriMatcher.addURI(authority, Student.TABLE_NAME + "/#", SINGLE_ROW_STUDENTS);


        return uriMatcher;
    }

    public StudentDBContentProvider() {
    }

    /**
     * This method help other applications to delete data from "students" table.
     * Notice this method is generic and other applications can pass criteria
     * to identify records and then perform delete.
     *
     * @param uri The full URI to query, including a row ID (if a specific
     *            record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @param selectionArgs
     * @return number of rows deleted
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(Student.TABLE_NAME, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * This method allows other application to create new record into "students" database table.
     *
     * @param uri The content:// URI of the insertion request.
     * @param values A set of column_name/value pairs to add to the database.
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // returns Id of newly inserted record
        long rowId = db
                .getOpenHelper()
                .getWritableDatabase()
                .insert(Student.TABLE_NAME, 0, values); // insert into "students" using values (key-value pairs)

        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        // call get database method and hold reference in the class variable db
        db = StudentDatabase.getDatabase(getContext());

        // must return true if the provider is successfully loaded
        return true;
    }

    /**
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     *      if the client is requesting a specific record, the URI will end in a record number
     *      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *      that _id value.
     * @param projection The list of columns to put into the cursor. If
     *      {@code null} all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     *      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *      the values from selectionArgs, in order that they appear in the selection.
     *      The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     *      If {@code null} then the provider is free to define the sort order.
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // query builder as this give other applications greater flexibility to query/read data
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        // specify the table name
        builder.setTables(Student.TABLE_NAME);

        // build the query using columns (projection), filters (selection) and other optional parameters
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        // once data is read using the query method below
        // it is returned as a Cursor
        final Cursor cursor = db
                .getOpenHelper()
                .getReadableDatabase()
                .query(query);

        return cursor;
    }

    /**
     * This method allow update of one or more student records. New values are provided using ContentValues
     *
     * @param uri The URI to query. This can potentially have a record ID if
     *            this is an update request for a specific record.
     * @param values A set of column_name/value pairs to update in the database.
     * @param selection An optional filter to match rows to update.
     * @param selectionArgs
     * @return Number of records updated
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(Student.TABLE_NAME, 0, values, selection, selectionArgs);

        return updateCount;
    }
}