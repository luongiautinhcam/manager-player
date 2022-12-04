package vn.edu.stu.quanlycauthu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DBPlayer12345";
    private static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String clubSql = "CREATE TABLE club(id text primary key, " +
                " name text not null)";
        String playerSql = "CREATE TABLE player(id integer primary key autoincrement, " +
                " name text not null, clubid text, dob text, clubname text, postion text, image blob, value integer, " +
                " FOREIGN KEY (clubid) REFERENCES club(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        sqLiteDatabase.execSQL(clubSql);
        sqLiteDatabase.execSQL(playerSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String clubSql = "DROP TABLE IF EXISTS club";
        String playerSql = "DROP TABLE IF EXISTS player";

        sqLiteDatabase.execSQL(clubSql);
        sqLiteDatabase.execSQL(playerSql);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
