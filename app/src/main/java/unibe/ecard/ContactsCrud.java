package unibe.ecard;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import unibe.ecard.Contacts.ContactColumns;

/**
 * Created by mitramejia on 4/9/16.
 */
public class ContactsCrud {

    String columnId = ContactColumns.COLUMN_NAME_ENTRY_ID;
    String columnFullName = ContactColumns.COLUMN_NAME_FULL_NAME;
    String columnPhone = ContactColumns.COLUMN_NAME_PHONE;
    String columnDirection = ContactColumns.COLUMN_NAME_DIRECTION;
    String columnHability1 = ContactColumns.COLUMN_NAME_HABILITY_1;
    String columnHability2 = ContactColumns.COLUMN_NAME_HABILITY_2;

    ContactsDbHelper contactsDbHelper = null;
    SQLiteDatabase db = null;

    public ContactsCrud(Context context) {
        this.contactsDbHelper = new ContactsDbHelper(context);
        this.db = contactsDbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public ContactsDbHelper getContactsDbHelper() {
        return contactsDbHelper;
    }

    public void insert(
            String insertFullName,
            String insertPhone,
            String insertDirection,
            String insertHability1,
            String insertHability2
    ) {

        try {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(columnFullName, insertFullName);
            values.put(columnPhone, insertPhone);
            values.put(columnDirection, insertDirection);
            values.put(columnHability1, insertHability1);
            values.put(columnHability2, insertHability2);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = getDb().insert(
                    getContactsDbHelper().TABLE_NAME,
                    getContactsDbHelper().COLUMN_NAME_NULLABLE,
                    values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println(newRowId);
        }

    }



}

