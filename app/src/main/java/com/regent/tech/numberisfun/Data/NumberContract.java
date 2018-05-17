package com.regent.tech.numberisfun.Data;


import android.provider.BaseColumns;

public final class NumberContract {

    public static final class NumberEntry implements BaseColumns{
        public static final String TABLE_NAME = "numbers";
        public static final String COLUMN_RESULT = "result";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
