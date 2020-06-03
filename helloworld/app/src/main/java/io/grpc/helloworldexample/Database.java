package io.grpc.helloworldexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {



    public Database(Context context) {
        super(context, "mobildb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE table_users (tc TEXT(20) PRIMARY KEY, password TEXT(20) NOT NULL, email TEXT(30) NOT NULL, adsoy TEXT(30) NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table  if exists table_users");
        onCreate(db);
    }
    public String VeriEkle(String nick,String passwd,String e_mail,String adsoyad)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("tc",nick);
        cv.put("password",passwd);
        cv.put("email",e_mail);
        cv.put("adsoy",adsoyad);
        db.insert("table_users",null,cv);
        db.close();
        return nick;
    }
    public String UsersCheck(String name, String password)
    {
        String query="Select username,password From table_users";
        SQLiteDatabase db=this.getReadableDatabase();
        Users user=null;
        String Donecekdeger=null;
        Cursor cursor=db.rawQuery(query,null);
        while(cursor.moveToNext()){
            if (name.equalsIgnoreCase(cursor.getString(0))&&password.equalsIgnoreCase(cursor.getString(1)))
            {
                Donecekdeger=name;
                break;
            }
            else {
                Donecekdeger="null";
            }
        }
        db.close();
        return Donecekdeger;
    }
    public String UserRegisterCheck(String email, String tcno)
    {
        String query="Select * From table_users";
        SQLiteDatabase db=this.getReadableDatabase();
        Users user=null;
        String Donecekdeger=null;
        Cursor cursor=db.rawQuery(query,null);
        while(cursor.moveToNext()){
            if (tcno.equalsIgnoreCase(cursor.getString(3))&&email.equalsIgnoreCase(cursor.getString(2)))
            {
                Donecekdeger="false";
                break;
            }
            else if(tcno.equalsIgnoreCase(cursor.getString(0))){
                Donecekdeger="usernamealinmis";
                break;
            }
            else if(email.equalsIgnoreCase(cursor.getString(2))){
                Donecekdeger="emailalinmis";
                break;
            }
            else {
                Donecekdeger="true";
            }
        }
        db.close();
        return Donecekdeger;
    }
    public List<String> listele()
    {
        List<String >veriler= new ArrayList<String>();
        SQLiteDatabase db=this.getWritableDatabase();
        String[] sutünlar={"tc,password,email,adsoy"};
        Cursor cursor=db.query("table_users",sutünlar,null,null,null,null,null);
        while(cursor.moveToNext()){
            veriler.add(cursor.getString(0)+" - "+ cursor.getString(1)+" - "+ cursor.getString(3));
        }
        return veriler;
    }
    public void Silme()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("table_users",null,null);
        db.close();
    }

}
