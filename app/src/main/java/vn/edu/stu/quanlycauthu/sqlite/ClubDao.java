package vn.edu.stu.quanlycauthu.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.quanlycauthu.model.Club;

public class ClubDao {
    private SQLiteDatabase db;

    public ClubDao(Context context) {
        DbHelper helper = new DbHelper(context);

        this.db = helper.getWritableDatabase();
    }
    public long insert(Club emp){
        ContentValues values = new ContentValues();
        values.put("id", emp.getId());
        values.put("name", emp.getName());

        return  db.insert("club", null, values);
    }

    @SuppressLint("Range")
    public List<Club> get(String sql, String ... selectArgs){
        List<Club> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, selectArgs);

        while (cursor.moveToNext()) {
            Club club = new Club();
            club.setId(cursor.getString(cursor.getColumnIndex("id")));
            club.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(club);
        }
        return list;
    }

    public List<Club> getAll(){
        String sql = "SELECT * FROM club";
        return get(sql);
    }

    public int delete(String id) {
        return db.delete("club", "id=?", new String[]{id});
    }

    public long updateClub(Club club) {
        SQLiteDatabase db = this.db;
        ContentValues values = new ContentValues();
        values.put("id", club.getId());
        values.put("name", club.getName());
        long result = db.update("club", values, "id" + "=?", new String[]{String.valueOf(club.getId())});
        return result;
    }
}
