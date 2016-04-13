package unibe.ecard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import unibe.ecard.Contacts.ContactColumns;

/**
 * Created by mitramejia on 4/9/16.
 */
public class ContactsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    // Remember you also need to save a new Contact after incrementing the database version
    // Since the data its deleted
    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "Ecard.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactColumns.TABLE_NAME + " (" +
                    ContactColumns.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_FULL_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_DIRECTION + TEXT_TYPE + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_HABILITY_1 + TEXT_TYPE + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_HABILITY_2 + TEXT_TYPE + COMMA_SEP +
                    ContactColumns.COLUMN_NAME_NULLABLE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactColumns.TABLE_NAME;

    public ContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}