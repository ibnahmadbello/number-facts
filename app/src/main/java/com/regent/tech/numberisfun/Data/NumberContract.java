package com.regent.tech.numberisfun.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class NumberContract {

    private NumberContract(){}

    public static final String CONTENT_AUTHORITY = "com.regent.tech.numberisfun";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_NUMBERS = "numbers";

    /**
     * Inner class that defines constant values for the number database table.
     * Each entry in the table represents a single number.
     */
    public static final class NumberEntry implements BaseColumns{

        /** The content URI to access the number data in the provider  */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NUMBERS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of numbers
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_NUMBERS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single number
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_NUMBERS;

        /** Name of database table for numbers */
        public final static String TABLE_NAME = "numbers";

        /**
         * Unique ID number for the number (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PET_NAME ="name";
    }
}
