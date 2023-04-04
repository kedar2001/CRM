package com.example.crm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kedar";

    //table one
    public static final String TABLE_NAME= "oneee";
    public static final String KEY_name= "name";
    public static final String KEY_email= "emailid";
    public static final String KEY_phone = "phone";
    public static final String KEY_password = "password";

    public static final String KEY_image = "image";
    public static final int VERSION = 1;

    //Table two
    public static final String TABLE_NAME2= "number_of_task";
    public static final String NO_TASK_DONE = "completed_tasks";
    public static final String TOTAL_NO_OF_TASK = "total_tasks";
    public static final String KEY_email_2= "emailid";

    //Table three
    public static final String TABLE_NAME3= "task_data";
    public static final String TASK_NO = "current_task_number";
    public static final String TASK_DATA_NAME = "name_field";
    public static final String TASK_DATA_PHONE = "number_field";
    public static final String KEY_email_3= "emailid2";
    public static final String TASK_DATA_IMG = "data_img";
    public static final String TASK_REMARKS = "remarks";


    private ByteArrayOutputStream byteArrayOutputStream;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       //sqLiteDatabase.execSQL("CREATE TABLE one(name TEXT, phone_no TEXT, email TEXT Primary key, password TEXT )");
        // sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+ " ( " + KEY_name + " TEXT, " + KEY_email + " TEXT, "+ KEY_phone + " TEXT, " + KEY_password+ " TEXT "+ ")" );
        // CREATE TABLE "" ( name Text, email TEXT, phone TEXT, password )
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME+"(" +KEY_name+" TEXT, "+ KEY_phone+" TEXT, "+ KEY_email+" TEXT, "+KEY_password+" TEXT, " +KEY_image+" BLOB " +")");
        //sqLiteDatabase.execSQL("Create Table "+ TABLE_NAME2+" ( "+ KEY_email_2 +" TEXT, "+ NO_TASK_DONE+" INTEGER ," + TOTAL_NO_OF_TASK+" INTEGER"+")" );
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME3+" ( "+ KEY_email_3 +" TEXT, "+TASK_NO+" INTEGER, "+ TASK_DATA_NAME+" TEXT, "+TASK_DATA_PHONE+ " TEXT, "+TASK_DATA_IMG+" BLOB, "+TASK_REMARKS+" TEXT"+")" );
    }

    //TASK_NO+" INTEGER, "+

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
       // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME3);
        onCreate(sqLiteDatabase);

    }


    public void Updateimage(byte[] image,String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_image,image);
        sq.update(TABLE_NAME,cv,KEY_email+ " = ? ",new String[]{email} );
    }

    public void datapass(String nameee, String phonee, String emaillll, String passworddd,byte[] img){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_name,nameee);
        values.put(KEY_phone,phonee);
        values.put(KEY_password,passworddd);
        values.put(KEY_email,emaillll);
        values.put(KEY_image,img);

        sq.insert(TABLE_NAME,null,values);
    }

    public ArrayList<Constructor> fetchData(){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        ArrayList<Constructor> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            Constructor c = new Constructor();
            c.name = cursor.getString(0);
            c.phone_no = cursor.getString(1);
            c.email = cursor.getString(2);
            c.password = cursor.getString(3);

            arrayList.add(c);
        }
        return arrayList;
    }

    public boolean loginDataFetch(String email,String password){
        SQLiteDatabase sq= this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_email+" = ?"+" AND "+ KEY_password +" = ? ",new String[]{email,password});

        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean alreadyRegisteredEmail(String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT * FROM "+TABLE_NAME+ " WHERE "+KEY_email+" = ? ",new String[]{email});

        if (cursor.getCount() == 0){
            return true;
        }
        else return false;
    }

    //get name of user
    public String getNameOfCurrentUser(String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT "+ KEY_name +" FROM "+TABLE_NAME+" WHERE "+KEY_email+" = ? ",new String[]{email});

        String nam=null;
        while (cursor.moveToNext()){
            nam = cursor.getString(0);
            return nam;
        }
        return nam;
    }

    //phone number
    public String getPhoneOfCurrentUser(String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT "+ KEY_phone +" FROM "+TABLE_NAME+" WHERE "+KEY_email+" = ? ",new String[]{email});

        String nam=null;
        while (cursor.moveToNext()){
            nam = cursor.getString(0);
            return nam;
        }
        return nam;
    }

    //password
    public String getPasswordOfCurrentUser(String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT "+ KEY_password +" FROM "+TABLE_NAME+" WHERE "+KEY_email+" = ? ",new String[]{email});

        String nam=null;
        while (cursor.moveToNext()){
            nam = cursor.getString(0);
            return nam;
        }
        return nam;
    }

    //image
    public byte[] getImageOfCurrentUser(String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor cursor = sq.rawQuery("SELECT "+ KEY_image +" FROM "+TABLE_NAME+" WHERE "+KEY_email+" = ? ",new String[]{email});

        byte[] imageInBytes= {0,0};
        while (cursor.moveToNext()){
            imageInBytes = cursor.getBlob(0);
            return imageInBytes;
        }
        Log.d("getImgInBytes","done");
        return imageInBytes;
    }



    public void updatePassword(String newPassword,String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_password,newPassword);
        sq.update(TABLE_NAME,values,KEY_email + " = ? ",new String[]{email});
    }

    public void updateEmail(String newEmail,String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_email,newEmail);
        sq.update(TABLE_NAME,values,KEY_email + " = ? ",new String[]{email});
    }

    public void updatePhone(String newPhone,String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_phone,newPhone);
        sq.update(TABLE_NAME,values,KEY_email + " = ? ",new String[]{email});
    }

    public void  updateName(String newName,String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_name,newName);
        sq.update(TABLE_NAME,values,KEY_email + " = ? ",new String[]{email});
    }
    //table3
        //join methods(table_3)
    public Cursor dataPhone(String email) {
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TABLE_NAME3+"."+TASK_DATA_PHONE+" From "+TABLE_NAME3 +" left join "+TABLE_NAME + " on "+ KEY_email+ " = "+KEY_email_3 + " where "+ KEY_email_3 + " = ? ",new String[]{email});
        return c;
    }

    public Cursor dataImg(String email) {
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TABLE_NAME3+"."+TASK_DATA_IMG+" From "+TABLE_NAME3 +" left join "+TABLE_NAME + " on "+ KEY_email+ " = "+KEY_email_3 + " where "+ KEY_email_3 + " = ? ",new String[]{email});
        return c;
    }
    public Cursor data_task_no(String email) {
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TABLE_NAME3+"."+TASK_NO+" From "+TABLE_NAME3 +" left join "+TABLE_NAME + " on "+ KEY_email+ " = "+KEY_email_3 + " where "+ KEY_email_3 + " = ? ",new String[]{email});
        return c;
    }

        //update methods(table_3)
    public void update_data_name(String new_data_name,String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_DATA_NAME,new_data_name);
        sq.update(TABLE_NAME3,values,KEY_email_3 + " = ? "+"and "+ TASK_NO +" = ? ",new String[]{email,task_no});
    }
    public void update_data_Phone(String new_data_phone,String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_DATA_PHONE,new_data_phone);
        sq.update(TABLE_NAME3,values,KEY_email_3 + " = ? "+"and "+ TASK_NO +" = ? ",new String[]{email,task_no});

    }
    public void update_data_task_no(String new_data_task_no,String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_NO,new_data_task_no);
        sq.update(TABLE_NAME3,values,KEY_email_3 + " = ? "+"and "+ TASK_NO +" = ? ",new String[]{email,task_no});

    }
    public void update_data_image(byte[] new_data_image,String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_DATA_IMG,new_data_image);
        sq.update(TABLE_NAME3,values,KEY_email_3 + " = ? "+"and "+ TASK_NO +" = ? ",new String[]{email,task_no});

    }
    public void update_data_remarks(String new_data_remark,String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_REMARKS,new_data_remark);
        sq.update(TABLE_NAME3,values,KEY_email_3 + " = ? "+"and "+ TASK_NO +" = ? ",new String[]{email,task_no});

    }

        //get data from table_3

    public Cursor get_data_image(String email,String task){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("SELECT "+ TASK_DATA_IMG+ " FROM " + TABLE_NAME3+ " where "+ KEY_email_3 + " = ? "+"and "+TASK_NO+ " = ?",new String[]{email,task});
        return c;
    }

    public String get_data_name(String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TASK_DATA_NAME+ " FROM "+ TABLE_NAME3 + " where " + KEY_email_3 + " = ? "+"and "+ TASK_NO+ " = ?",new String[]{email, task_no});
        String name = null;

        while (c.moveToNext()){
            name = c.getString(0);
            return name;
        }
        return name;
    }

    public String get_data_num(String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TASK_DATA_PHONE+ " FROM "+ TABLE_NAME3 + " where " + KEY_email_3 + " = ? "+"and "+ TASK_NO+ " = ?",new String[]{email, task_no});
        String num = null;

        while (c.moveToNext()){
            num = c.getString(0);
        }
        return num;
    }

    public String get_data_remarks(String email,String task_no){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TASK_REMARKS+ " FROM "+ TABLE_NAME3 + " where " + KEY_email_3 + " = ? "+"and "+ TASK_NO+ " = ?",new String[]{email, task_no});
        String remarks = null;

        while (c.moveToNext()){
            remarks = c.getString(0);
        }
        return remarks;
    }



        //join with table 1 and 3
    public Cursor dataName(String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        Cursor c = sq.rawQuery("Select "+ TABLE_NAME3+"."+TASK_DATA_NAME+" From "+TABLE_NAME3 +" left join "+TABLE_NAME + " on "+ KEY_email+ " = "+KEY_email_3 + " where "+ KEY_email_3 + " = ? ",new String[]{email});
        return c;
    }
        //insert data in table_3
    public void insert_t3(String email,String task_no, String data_name, String data_phone, byte[] img, String remarks){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_email_3,email);
        cv.put(TASK_DATA_NAME,data_name);
        cv.put(TASK_DATA_PHONE,data_phone);
        cv.put(TASK_DATA_IMG,img);
        cv.put(TASK_REMARKS,remarks);
        cv.put(TASK_NO,task_no);

        sq.insert(TABLE_NAME3,null,cv);

    }

   /* public void addImage(Bitmap image,String email){
        SQLiteDatabase sq = this.getWritableDatabase();
        byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageInBytes;
        imageInBytes = byteArrayOutputStream.toByteArray();
       // Constructor c = new Constructor();
        ContentValues cv = new ContentValues();

//        Cursor cursor = sq.rawQuery("SELECT "+ KEY_image +" FROM "+TABLE_NAME+ " Where "+ KEY_email+" = ? ",new String[]{email});
//        while(cursor.moveToNext()){
//            imageInBytes = cursor.getBlob(0);
//            cv.put(KEY_image,imageInBytes);
//        }

        long id=  sq.insert(TABLE_NAME,null,cv);
        Log.e("image insert", String.valueOf(id));
    }*/
}
