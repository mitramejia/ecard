package unibe.ecard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import unibe.ecard.Contacts.ContactColumns;

/**
 * Created by mitramejia on 4/9/16.
 */
public class ContactsCrud {

    private static final String tableName = ContactColumns.TABLE_NAME;
    private static final String columnId = ContactColumns.COLUMN_NAME_ENTRY_ID;
    private static final String columnFullName = ContactColumns.COLUMN_NAME_FULL_NAME;
    private static final String columnPhone = ContactColumns.COLUMN_NAME_PHONE;
    private static final String columnEmail = ContactColumns.COLUMN_NAME_EMAIL;
    private static final String columnDirection = ContactColumns.COLUMN_NAME_DIRECTION;
    private static final String columnHability1 = ContactColumns.COLUMN_NAME_HABILITY_1;
    private static final String columnHability2 = ContactColumns.COLUMN_NAME_HABILITY_2;
    private static final String columnNullable = ContactColumns.COLUMN_NAME_NULLABLE;

    ContactsDbHelper contactsDbHelper = null;
    SQLiteDatabase db = null;


    public ContactsCrud(Context context) {
        this.contactsDbHelper = new ContactsDbHelper(context);
        this.db = contactsDbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void deleteEntries() {
        getDb().execSQL( "DROP TABLE IF EXISTS " + ContactColumns.TABLE_NAME);
    }

    /**
     * Creates a contact row.
     *
     * @param insertFullName  the new contact full name
     * @param insertPhone     the new contact phone
     * @param insertEmail     the new contact email
     * @param insertDirection the new contact direction
     * @param insertHability1 the new contact hability 1
     * @param insertHability2 the new contact hability 2
     * @return the boolean
     *
     */
    public boolean createContact(
            String insertFullName,
            String insertPhone,
            String insertEmail,
            String insertDirection,
            String insertHability1,
            String insertHability2
    ) {

        long result = 100;

        try {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(columnFullName, insertFullName);
            values.put(columnPhone, insertPhone);
            values.put(columnEmail, insertEmail);
            values.put(columnDirection, insertDirection);
            values.put(columnHability1, insertHability1);
            values.put(columnHability2, insertHability2);

            // Insert the new row, returning the primary key value of the new row
            result = getDb().insert(tableName, columnNullable, values);

        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getMessage());
            e.printStackTrace();
        }

        if (result == -1)
            return false;
        else
            return true;

    }


    /**
     * Updates a contact field.
     *
     * @param contactId  the contact id
     * @param columnName the column name of the Contacts table to edit
     * @param newValue   the new value to updte
     * @return the boolean
     */
    public boolean updateContactField(String contactId, String columnName, String newValue) {

        long result = 100;
        try {
            // Create a new map of values, where column names are the keys
            ContentValues newValues = new ContentValues();
            newValues.put(columnName, newValue);

            // Update the new row, returning the primary key value of the new row
            String selection = columnId + "=?";
            String[] selectionArgs = {String.valueOf(contactId)};

            result = getDb().update(tableName, newValues, selection, selectionArgs);

        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getMessage());
            e.printStackTrace();
        }

        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean updateContact(String contactId,
                                 String newFullName,
                                 String newPhone,
                                 String newEmail,
                                 String newDirection,
                                 String newHability1,
                                 String newHability2) {

        long result = 100;
        try {
            // Create a new map of values, where column names are the keys
            ContentValues newValues = new ContentValues();
            newValues.put(columnFullName, newFullName);
            newValues.put(columnPhone, newPhone);
            newValues.put(columnEmail, newEmail);
            newValues.put(columnDirection, newDirection);
            newValues.put(columnHability1, newHability1);
            newValues.put(columnHability2, newHability2);

            // Update the new row, returning the primary key value of the new row
            String selection = columnId + "=?";
            String[] selectionArgs = {String.valueOf(contactId)};

            result = getDb().update(tableName, newValues, selection, selectionArgs);

        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getMessage());
            e.printStackTrace();
        }

        if (result == -1)
            return false;
        else
            return true;
    }

    /**
     * Gets all contacts.
     */
    public void getAllContacts() {
        String[] projection = {
                columnId,
                columnFullName,
                columnPhone,
                columnEmail,
                columnDirection,
                columnHability1,
                columnHability2
        };

        Cursor c = db.query(tableName,      // The table to query
                projection,                 // The columns to return
                null,                       // The columns for the WHERE clause
                null,                       // The values for the WHERE clause
                null,                       // don't group the rows
                null,                       // don't filter by row groups
                columnFullName + " DESC"    // The sort order
        );

        try {

            DatabaseUtils.dumpCursor(c);

        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getMessage());
            e.printStackTrace();
        } finally {
            c.close();
        }

    }

    /**
     * Gets a contact by id.
     *
     * @param contactId the contact id
     * @return ArrayList<String> containing all columns from the Contact row
     */
    public ArrayList<String> getContactById(String contactId) {
        String[] projection = {
                columnId,
                columnFullName,
                columnPhone,
                columnEmail,
                columnDirection,
                columnHability1,
                columnHability2
        };

        //get the cursor you're going to use
        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor c = db.query(
                tableName,                  // The table to query
                projection,                 // The columns to return
                columnId + "=?",            // The columns for the WHERE clause
                selectionArgs,              // The values for the WHERE clause
                null,                       // don't group the rows
                null,                       // don't filter by row groups
                null                        // The sort order
        );

        //this is optional - if you want to return one object
        //you don't need a list
        ArrayList<String> fields = new ArrayList<>();

        //you should always use the try catch statement incase
        //something goes wrong when trying to read the data
        try {
            Log.d("getContactById", "Ran");
            DatabaseUtils.dumpCursor(c);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                fields.add(c.getString(0));
                fields.add(c.getString(1));
                fields.add(c.getString(2));
                fields.add(c.getString(3));
                fields.add(c.getString(4));
                fields.add(c.getString(5));
                fields.add(c.getString(6));
            }
        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            //release all your resources
            c.close();
            getDb().close();
        }
        return fields;
    }


    /**
     * Gets a contact by id.
     *
     * @param contactId the contact id
     * @param contactId the contact id
     * @return String containing all columns from the Contact row
     */
    public String getContactField(String contactId, String columnName) {

        String field = null;
        String[] projection = {
            columnName
        };
        //get the cursor you're going to use
        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor c = db.query(
                tableName,                  // The table to query
                projection,                 // The columns to return
                columnId + "=?",            // The columns for the WHERE clause
                selectionArgs,              // The values for the WHERE clause
                null,                       // don't group the rows
                null,                       // don't filter by row groups
                null                        // The sort order
        );

        //you should always use the try catch statement in case
        //something goes wrong when trying to read the data
        try {
            Log.d("getContactField", "Ran");
            DatabaseUtils.dumpCursor(c);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                field = c.getString(0);
            }
        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            //release all your resources
            c.close();
            getDb().close();
        }
        return field;
    }

}

