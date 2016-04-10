package unibe.ecard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import unibe.ecard.Contacts.ContactColumns;

/**
 * Created by mitramejia on 4/9/16.
 */
public class ContactsCrud {

    private static final String tableName = ContactColumns.TABLE_NAME;
    private static final String columnId = ContactColumns.COLUMN_NAME_ENTRY_ID;
    private static final String columnFullName = ContactColumns.COLUMN_NAME_FULL_NAME;
    private static final String columnPhone = ContactColumns.COLUMN_NAME_PHONE;
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


    /**
     * Creates a contact row.
     *
     * @param insertFullName  the new contact full name
     * @param insertPhone     the new contact phone
     * @param insertDirection the new contact direction
     * @param insertHability1 the new contact hability 1
     * @param insertHability2 the new contact hability 2
     * @return the boolean
     *
     */
    public boolean createContact(
            String insertFullName,
            String insertPhone,
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
            values.put(columnDirection, insertDirection);
            values.put(columnHability1, insertHability1);
            values.put(columnHability2, insertHability2);

            // Insert the new row, returning the primary key value of the new row
            result = getDb().insert(tableName, columnNullable, values);

        } catch (SQLException e) {
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

        } catch (SQLException e) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

    }


}

