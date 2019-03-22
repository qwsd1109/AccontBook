package com.example.accontbook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {
    private static  String DB_NAME="MONEYBOOK.db";
    private static  String DB_PATH="";
    private final Context myContext;
    private SQLiteDatabase myDatabase;
    public SQLite(Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT>=15)
        {
            DB_PATH=context.getApplicationInfo().dataDir+"/database/";
        }else{
            DB_PATH = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/database//";
        }
        this.myContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MONEYBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT,  item TEXT,it TEXT, price TEXT, create_at TEXT);");
        db.execSQL("CREATE TABLE SETTING (_id INTEGER PRIMARY KEY AUTOINCREMENT,  salary TEXT, day INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void checkAndCopyData(){
        boolean dbExist=cheackDatabase();
        if(dbExist){
            Log.d("TAG","databae already exist");
        }else {
            this.getReadableDatabase();
        }
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("tag","error copydatabase");
        }
    }
    public boolean cheackDatabase(){
        SQLiteDatabase  checkDB=null;
        String myPath = DB_PATH+DB_NAME;
        checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        if(checkDB!= null){
            checkDB.close();
        }
        return checkDB!=null? true:false;
    }
    public void copyDatabase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH+DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer=new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public  void openDatabase(){
        String myPath=DB_PATH+DB_NAME;
        myDatabase=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public synchronized  void close(){
        if(myDatabase !=null){
            myDatabase.close();
        }
        super.close();
    }
    public Cursor QUeryData(String query){
        return myDatabase.rawQuery(query,null);
    }
    public void con_update(String item_set,String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MONEYBOOK SET item_set="+item_set+" SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }
    public void set_update(String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE SETTING SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }



    public void con_insert(String create_at, String item, String price, String it) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" + item + "','" + it + "','" + price + "', '" + create_at + "');");
        db.close();
    }
    public void set_insert(String salary, int day) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SETTING VALUES(null, '" + salary + "', " + day + ");");
        db.close();
    }



    public void con_delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM MONEYBOOK WHERE item='" + item + "';");
        db.close();
    }
    public void set_delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SETTING WHERE item='" + item + "';");
        db.close();
    }



//    public String con_getResult() {
//        // 읽기가 가능하게 DB 열기
//        SQLiteDatabase db = getReadableDatabase();
//        String item[] = new String[0];
//        String it = "";
//
//        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
//        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
//        while (cursor.moveToNext()) {
//            it = cursor.getString(0);
//            item[1]=cursor.getString(1);
//            item[2]= String.valueOf(cursor.getInt(2));
//            item[3]= cursor.getString(3);
//        }
//
//        return it;
//    }
//item TEXT,it TEXT, price TEXT
    public  List<MyData> con_print(){
        List<MyData> val = new ArrayList<>();
        String  selsct = "SELECT  * FROM MONEYBOOK";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selsct,null);
        if(cursor.moveToFirst()){
            do{
                MyData myData = new MyData();
                myData.settv_it(cursor.getString(2));
                myData.settv_item(cursor.getString(1));
                myData.settv_money(cursor.getString(3)+" 원");
                myData.settv_time(cursor.getString(4));
                val.add(myData);
            }while (cursor.moveToNext());
        }
        db.close();
        return val;
    }
    public long con_getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        long val =0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(2);
            if(result.equals("지출")) {
                val += Long.parseLong(cursor.getString(3).replace(",", ""));
            }
//            result +=cursor.getString(3)/*+cursor.getString(2)*/; //3번은 돈 2번은 지출
        }

        return val;
    }
    public long co_getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        long val =0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
        while (cursor.moveToNext()) {
            result = cursor.getString(2);
            if(result.equals("수입")) {
                val += Long.parseLong(cursor.getString(3).replace(",", ""));
            }
//            result +=cursor.getString(3)/*+cursor.getString(2)*/; //3번은 돈 2번은 지출
        }

        return val;
    }
    public void de(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("delete from MONEYBOOK" );
    }
    public String set_getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SETTING", null);
        while (cursor.moveToNext()) {
            result +=cursor.getString(1)/*+cursor.getString(2)*/;
        }

        return result;
    }
    public int setday_getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int result =0;

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SETTING", null);
        while (cursor.moveToNext()) {
            result = Integer.parseInt(cursor.getString(2))/*+cursor.getString(2)*/;
        }

        return result;
    }

}
