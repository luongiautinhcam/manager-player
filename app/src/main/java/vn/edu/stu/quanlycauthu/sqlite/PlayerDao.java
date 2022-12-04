package vn.edu.stu.quanlycauthu.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.quanlycauthu.model.Club;
import vn.edu.stu.quanlycauthu.model.Player;
import vn.edu.stu.quanlycauthu.util.FormatUtil;

public class PlayerDao {
    private SQLiteDatabase db;

    public PlayerDao(Context context) {
        DbHelper helper = new DbHelper(context);

        this.db = helper.getWritableDatabase();
    }

    public long insert(Player emp){
        ContentValues values = new ContentValues();
        values.put("id", emp.getId());
        values.put("name", emp.getName());
        values.put("dob", FormatUtil.toString(emp.getDob()));
        values.put("value", emp.getValue());
        values.put("postion", emp.getPostion());
        values.put("clubid", emp.getIdClub());
        values.put("clubname", emp.getNameClub());
        values.put("image", emp.getImage());


        return  db.insert("player", null, values);
    }

    @SuppressLint("Range")
    public List<Player> get(String sql, String ... selectArgs) throws ParseException {
        List<Player> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, selectArgs);

        while (cursor.moveToNext()) {
            Player player = new Player();
            player.setId(cursor.getInt(cursor.getColumnIndex("id")));
            player.setName(cursor.getString(cursor.getColumnIndex("name")));
            player.setDob(FormatUtil.toDate(cursor.getString(cursor.getColumnIndex("dob"))));
            player.setValue(cursor.getInt(cursor.getColumnIndex("value")));
            player.setPostion(cursor.getString(cursor.getColumnIndex("postion")));
            player.setIdClub(cursor.getString(cursor.getColumnIndex("clubid")));
            player.setNameClub(cursor.getString(cursor.getColumnIndex("clubname")));
            player.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
            list.add(player);
        }
        return list;
    }

    public List<Player> getAll() throws ParseException {
        String sql = "SELECT * FROM player";
        return get(sql);
    }

    public List<Player> getAllByClub(String clubId) throws ParseException {
        String sql = "SELECT * FROM player WHERE clubid = ?";
        return get(sql,"" + clubId);
    }


    public int delete(String id) {
        return db.delete("player", "id=?", new String[]{id});
    }

    public long updatePlayer(Player player) {
        SQLiteDatabase db = this.db;
        ContentValues values = new ContentValues();
        values.put("id", player.getId());
        values.put("name", player.getName());
        values.put("dob", FormatUtil.toString(player.getDob()));
        values.put("value", player.getValue());
        values.put("image", player.getImage());
        values.put("clubid", player.getIdClub());
        values.put("clubname", player.getNameClub());
        long result = db.update("player", values, "id" + "=?", new String[]{String.valueOf(player.getId())});
        return result;
    }
}
