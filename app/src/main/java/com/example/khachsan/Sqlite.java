package com.example.khachsan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqlite extends SQLiteOpenHelper {
    private static final String DBName="SqliteDB_1501";
    private static final int  VERSION=1;
    private static final String TABLENAME="Taxi";
    private  static  String Id="_id";
    private static String HoTen="hoten";
    private static  String SoPhong="sophong";
    private static  String DonGia="dongia";
    private static  String SoNgay="songay";
    private SQLiteDatabase myDB;

    public Sqlite(@Nullable Context context) {
        super(context, DBName,null, VERSION);
    }

    public static String getId() {
        return Id;
    }

    public static String getHoTen() {
        return HoTen;
    }

    public static String getSoPhong() {
        return SoPhong;
    }

    public static String getDonGia() {
        return DonGia;
    }

    public static String getSoNgay() {
        return SoNgay;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLENAME +
                "( " + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HoTen + " TEXT NOT NULL, " +
                SoPhong + " INTEGER NOT NULL, " +
                DonGia + " REAL NOT NULL, " +
                SoNgay + " INTEGER NOT NULL)";
        db.execSQL(queryTable);
        ArrayList<HoaDon> list = new ArrayList<>();
        list.add(new HoaDon(0, "Nguyen Tien Tung", 123,3,1000000));
        list.add(new HoaDon(0, "Thang Nguye", 213,2,1500000));
        list.add(new HoaDon(0, "Duc My", 231,4,2000000));
        list.add(new HoaDon(0, "Van Bach", 321,2,1500000));
        list.add(new HoaDon(0, "The Trung", 312,3,1000000));
        list.add(new HoaDon(0, "Hoang Quan", 456,5,1500000));

        for (HoaDon hoaDon : list) {
            ContentValues values = new ContentValues();
            values.clear();
            values.put(HoTen,hoaDon.getHoTen());
            values.put(SoPhong,hoaDon.getSoPhong());
            values.put(DonGia,hoaDon.getDonGia());
            values.put(SoNgay,hoaDon.getSoNgay());
            db.insert(TABLENAME, null, values);
        }



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDb(){
        myDB= getWritableDatabase();
    }
    public void closeDb(){
        if(myDB !=null && myDB.isOpen()){
            myDB.close();
        }
    }
    public long Insert(String hoten, int sophong,double dongia,int songay){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(HoTen,hoten);
        values.put(SoPhong,sophong);
        values.put(DonGia,dongia);
        values.put(SoNgay,songay);
        return db.insert(TABLENAME,null,values);
    }
    public Cursor DisplayAll(){
        SQLiteDatabase db = getReadableDatabase(); // Use getReadableDatabase() instead of myDB
        String query = "SELECT * FROM " + TABLENAME;
        return db.rawQuery(query, null);
    }
    public long Update(int id,String hoten, int sophong,double dongia,int songay){
        ContentValues values=new ContentValues();
        values.put(HoTen,hoten);
        values.put(SoPhong,sophong);
        values.put(DonGia,dongia);
        values.put(SoNgay,songay);
        String where=Id+" = "+id;
        return  myDB.update(TABLENAME,values,where,null);
    }
    public long Delete(int id){
        String where=Id+ " = "+ id;
        return  myDB.delete(TABLENAME,where,null);
    }
}
