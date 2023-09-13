package com.example.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Chitieu.db";
    private static int  DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable_item = "CREATE TABLE items("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT, category TEXT, price TEXT, date TEXT)";
        db.execSQL(createtable_item);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    //get all dua tren ngay them
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("items", null, null, null,
                null, null,order);
        while(rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }
    //add
    public long addItem(@NonNull Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("items", null, values);
    }
    //update
    public int update(Item i){
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        SQLiteDatabase st = getWritableDatabase();
        String whereClause = "id=?";
        String[] args = {Integer.toString(i.getId())};
        return st.update("items", values, whereClause, args);
    }
    //delete
    public int delete(int id){
        String whereClause ="id=?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("items", whereClause, args);
    }
    public List<Item> getByDate(String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date like?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null,whereClause, whereArgs,null,null,null );
        while(rs!=null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item(id, title, category, price, date));
        }
        return list;
    }

}
