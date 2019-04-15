package com.example.ahsan.manyconnects.Database;

import android.provider.BaseColumns;

public final class ContractClass {
    private ContractClass() {}

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                    TableEntry._ID + " INTEGER PRIMARY KEY," +
                    TableEntry.COLUMN_NAME_RECEIVER + " TEXT," +
                    TableEntry.COLUMN_NAME_DATE + " TEXT," +
                    TableEntry.COLUMN_NAME_PLATFORM + " TEXT," +
                    TableEntry.COLUMN_NAME_MESSAGE_HEADER + " TEXT," +
                    TableEntry.COLUMN_NAME_MESSAGE_BODY + " TEXT," +
                    TableEntry.COLUMN_NAME_MESSAGE_FOOTER + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "nascon_hackathon";
        public static final String COLUMN_NAME_RECEIVER = "receiver";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_PLATFORM = "platform";
        public static final String COLUMN_NAME_MESSAGE_BODY = "message_body";
        public static final String COLUMN_NAME_MESSAGE_HEADER = "message_header";
        public static final String COLUMN_NAME_MESSAGE_FOOTER = "message_footer";
    }
}
