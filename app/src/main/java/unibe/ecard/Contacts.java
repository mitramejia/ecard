package unibe.ecard;

import android.provider.BaseColumns;

/**
 * Created by mitramejia on 4/9/16.
 */
public final class Contacts {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public Contacts() {}

    /* Inner class that defines the table contents */
    public static abstract class ContactColumns implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_FULL_NAME = "full_name";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_DIRECTION = "direction";
        public static final String COLUMN_NAME_HABILITY_1 = "hability1";
        public static final String COLUMN_NAME_HABILITY_2 = "hability2";
        public static final String COLUMN_NAME_NULLABLE = "nullable";

    }
}
