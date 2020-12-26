package com.sadam.ui4.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sadam.ui4.Note.Note;

import java.util.ArrayList;

public class MySqLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UI4_DATABASE";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME_Note = "Note";
    private static final String TABLE_NAME_User = "User";
    private static final String CREATE_Note = "CREATE TABLE \"" + TABLE_NAME_Note + "\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"content\"\tINTEGER,\n" +
            "\t\"user_id\"\tINTEGER NOT NULL,\n" +
            "\t\"changedTime\"\tINTEGER NOT NULL,\n" +
            "\t\"isDeleted\"\tINTEGER NOT NULL\n" +
            ");";
    private static final String CREATE_User = "CREATE TABLE \"" + TABLE_NAME_User + "\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"name\"\tTEXT NOT NULL UNIQUE,\n" +
            "\t\"password\"\tTEXT NOT NULL,\n" +
            "\t\"isDeleted\"\tINTEGER NOT NULL\n" +
            ");";
    private static final String DROP_Note = "drop table if exists Note";
    private static final String DROP_User = "drop table if exists User";
    private static final int HAS_DELETED = 1;
    private static final int UN_DELETED = 0;

    private Context mContext;
    private SQLiteDatabase db;
    private ContentValues values = new ContentValues();

    public MySqLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("CREATE_Note", CREATE_Note);
        db.execSQL(CREATE_Note);
        Toast.makeText(mContext, "CREATE_Note success!", Toast.LENGTH_SHORT).show();
        Log.e("CREATE_User", CREATE_User);
        db.execSQL(CREATE_User);
        Toast.makeText(mContext, "CREATE_User success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_Note);
        Toast.makeText(mContext, "Drop Table Note success!", Toast.LENGTH_SHORT).show();
        db.execSQL(DROP_User);
        Toast.makeText(mContext, "Drop Table User success!", Toast.LENGTH_SHORT).show();
        onCreate(db);
        Toast.makeText(mContext, "Update Database success!", Toast.LENGTH_SHORT).show();
    }

    public Long insertUser(String username, String password) {
        values.put("name", username);
        values.put("password", password);
        values.put("isDeleted", UN_DELETED);
        Long id = this.db.insert(TABLE_NAME_User, null, values);
        Toast.makeText(mContext, "新增了一个User!id=" + id, Toast.LENGTH_SHORT).show();
        values.clear();
        return id;
    }

    public void updateUser(Long id, String username, String password) {
        values.put("name", username);
        values.put("password", password);
        values.put("isDeleted", UN_DELETED);
        int res = db.update(TABLE_NAME_User, values, "id=?", new String[]{String.valueOf(id)});
        Toast.makeText(mContext, "更新了一User信息!？res=" + res + "id=" + String.valueOf(id), Toast.LENGTH_SHORT).show();
        values.clear();
    }

    public void deleteUser(int id) {
        db.delete(TABLE_NAME_User, "id=?", new String[]{String.valueOf(id)});
        Toast.makeText(mContext, "硬删除一个用户success!", Toast.LENGTH_LONG).show();
    }

    public void pretendToDelete(User user) {
        values.put("name", user.getName());
        values.put("password", user.getPassword());
        values.put("isDeleted", HAS_DELETED);
        int res = db.update(TABLE_NAME_User, values, "id=?", new String[]{String.valueOf(user.getId())});
        Toast.makeText(mContext, "软删除了一个User信息!？res=" + res + "id=" + String.valueOf(user.getId()), Toast.LENGTH_SHORT).show();
        values.clear();
    }

    public Long insertNote(Note note) {
        values.put("content", note.getContent());
        values.put("user_id", note.getUser().getId());
        values.put("changedTime", note.getChangedTime());
        values.put("isDeleted", UN_DELETED);
        Long id = this.db.insert(TABLE_NAME_Note, null, values);
        Toast.makeText(mContext, "新增了一条content!id=" + String.valueOf(id), Toast.LENGTH_SHORT).show();
        values.clear();
        return id;
    }

    /**
     * @param note
     * @return the time of the Note has been updated.
     */
    public Long updateNote(Note note) {
        Long now = Note.getNow();
        values.put("content", note.getContent());
        values.put("user_id", note.getUser().getId());
        values.put("changedTime", now);
        values.put("isDeleted", UN_DELETED);
        int res = db.update(TABLE_NAME_Note, values, "id=?", new String[]{String.valueOf(note.getId())});
        values.clear();
        if (res == 0) {
            Toast.makeText(mContext, "更新Note失败！?res=" + res + "?id=" + String.valueOf(note.getId()), Toast.LENGTH_SHORT).show();
            return note.getChangedTime();
        } else {
            Toast.makeText(mContext, "更新了一条Note!?res=" + res + "?id=" + String.valueOf(note.getId()), Toast.LENGTH_SHORT).show();
            return now;
        }
    }

    public void deleteNote(Long id) {
        db.delete(TABLE_NAME_Note, "id=?", new String[]{String.valueOf(id)});
        Toast.makeText(mContext, "软删除一个Note success!", Toast.LENGTH_LONG).show();
    }

    public void pretendToDelete(Note note) {
        Long now = Note.getNow();
        values.put("content", note.getContent());
        values.put("user_id", note.getUser().getId());
        values.put("changedTime", now);
        values.put("isDeleted", HAS_DELETED);
        int res = db.update(TABLE_NAME_Note, values, "id=?", new String[]{String.valueOf(note.getId())});
        values.clear();
    }

    public User getUserByName(String username) {
        String SQL_CMD = "SELECT * FROM \"" + TABLE_NAME_User + "\" WHERE name=? ;";
        Cursor cursor = db.rawQuery(SQL_CMD, new String[]{username});
        if (cursor.getCount() == 0) {
            return null;
        } else {
            if (cursor.moveToFirst()) {
                do {
                    Long id = cursor.getLong(cursor.getColumnIndex("id"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    User user = new User(id, username, password);
                    return user;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return null;
    }

    public ArrayList<Note> getNoteByUser(User user) {
        ArrayList<Note> noteArrayList = new ArrayList<>();
        Long userId = user.getId();
        String SQL_CMD = "SELECT * FROM \"" + TABLE_NAME_Note + "\" WHERE user_id=? ORDER BY changedTime;";
        Cursor cursor = db.rawQuery(SQL_CMD, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Long changedTime = cursor.getLong(cursor.getColumnIndex("changedTime"));
                Note note = new Note(user, id, content, changedTime);
                noteArrayList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return noteArrayList;
    }


    public Context getContext() {
        return mContext;
    }

}
