package com.example.rajeevkumar.parkingsolution;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajeev Kumar on 14-07-2016.
 */
public class DatabaseHelper {

    DatabaseAdapter databaseAdapter;


    public DatabaseHelper(Context context) {
        databaseAdapter = new DatabaseAdapter(context);
    }


    public void insertBookingData(String usrPhone, String date, int basement, int slotNo) {
        SQLiteDatabase sqLiteDatabase = databaseAdapter.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseAdapter.KEY_USERPHONE, usrPhone);
        contentValues.put(DatabaseAdapter.KEY_DATE, date);
        contentValues.put(DatabaseAdapter.KEY_BASEMENT_ID, basement);
        contentValues.put(DatabaseAdapter.KEY_SLOT_NUMBER, slotNo);

        sqLiteDatabase.insert(DatabaseAdapter.TABLE_SLOT_BOOKING, null, contentValues);
    }

    public void deleteRow(String userPhone, String date, int basement, int position) {
        //DELETE from usertable where Name="ABC"
        Log.d("TAG", "deleteRow: database ");
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();
        db.delete(DatabaseAdapter.TABLE_SLOT_BOOKING, DatabaseAdapter.KEY_USERPHONE + "='" + userPhone + "'" + " AND " + DatabaseAdapter.KEY_DATE + "='" + date + "'" + " AND " + DatabaseAdapter.KEY_BASEMENT_ID + "='" + basement + "'" + " AND " + DatabaseAdapter.KEY_SLOT_NUMBER + "='" + position + "'", null);
/*        String[] whereArgs={"ABC"};
        int count=db.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.NAME+" =? ",whereArgs);
        return count;*/

    }

    public void deleteAll() {
        //DELETE from usertable where Name="ABC"
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();
        db.delete(DatabaseAdapter.TABLE_SLOT_BOOKING, null, null);
/*        String[] whereArgs={"ABC"};
        int count=db.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.NAME+" =? ",whereArgs);
        return count;*/

    }
     /* public void insertBookingsData(BookingModel model){
        SQLiteDatabase sqLiteDatabase=databaseAdapter.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DatabaseAdapter.KEY_DATE,model.getDate());
        values.put(DatabaseAdapter.KEY_BASEMENT_ID,model.getBasement());
        values.put(DatabaseAdapter.KEY_SLOT_NUMBER,model.getSlot());
        sqLiteDatabase.insert(DatabaseAdapter.CREATE_BOOKING_TABLE,null,values);


    }*/

   /* public void insertData(BookingModel model){
        SQLiteDatabase sqLiteDatabase=databaseAdapter.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(DatabaseAdapter.KEY_DATE,model.getDate());
        contentValues.put(DatabaseAdapter.KEY_BASEMENT_ID,model.getBasement());
        contentValues.put(DatabaseAdapter.KEY_SLOT_NUMBER,model.getSlot());

        sqLiteDatabase.insert(DatabaseAdapter.TABLE_SLOT_BOOKING,null,contentValues);
    }*/

    public String getAllData() {
        SQLiteDatabase db = databaseAdapter.getWritableDatabase();
        String[] columns = {DatabaseAdapter.KEY_USERPHONE, DatabaseAdapter.KEY_DATE, DatabaseAdapter.KEY_BASEMENT_ID, DatabaseAdapter.KEY_SLOT_NUMBER};
        Cursor cursor = db.query(DatabaseAdapter.TABLE_SLOT_BOOKING, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
            String phone = cursor.getString(0);
            String date = cursor.getString(1);
            int basement = cursor.getInt(2);
            int slotNo = cursor.getInt(3) + 1;

            buffer.append(phone + "  " + date + "   " + "Basement: " + basement + "   " + "Slot: " + slotNo + "\n" + "\n");

        }
        return buffer.toString();
    }

    public ArrayList<Integer> getBookedSlotNumber(String date, int basement) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        SQLiteDatabase sqLiteOpenHelper = databaseAdapter.getWritableDatabase();
        String column[] = {DatabaseAdapter.KEY_SLOT_NUMBER};
        Cursor cursor = sqLiteOpenHelper.query(DatabaseAdapter.TABLE_SLOT_BOOKING, column, DatabaseAdapter.KEY_DATE + "='" + date + "'" + " AND " + DatabaseAdapter.KEY_BASEMENT_ID + "='" + basement + "'", null, null, null, null);
        while (cursor.moveToNext()) {
            int slotNumber = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_SLOT_NUMBER));
            array.add(slotNumber);

        }
       /* cursor.moveToFirst();
        int slotNumber = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_SLOT_NUMBER));
       // cursor.close();
        //return slotNumber ;*/

        cursor.close();
        return array;
    }


    public long insertLoginData(String name, String phone, String email, String password) {
        SQLiteDatabase sqLiteOpenHelper = databaseAdapter.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        //contentValues.put(DatabaseAdapter.KEY_ID, id);
        contentValues.put(DatabaseAdapter.KEY_USER_NAME, name);
        contentValues.put(DatabaseAdapter.KEY_PHONE, phone);
        contentValues.put(DatabaseAdapter.KEY_EMAIL, email);
        contentValues.put(DatabaseAdapter.KEY_PASSWORD, password);
        long id = sqLiteOpenHelper.insert(DatabaseAdapter.TABLE_LOGIN, null, contentValues);
        return id;
    }

    public String getSingleRow(String phone) {
        SQLiteDatabase sqLiteOpenHelper = databaseAdapter.getWritableDatabase();
        String column[] = {DatabaseAdapter.KEY_PASSWORD};
        Cursor cursor = sqLiteOpenHelper.query(DatabaseAdapter.TABLE_LOGIN, column, DatabaseAdapter.KEY_PHONE + "='" + phone + "'", null, null, null, null);
        // Cursor cursor=sqLiteOpenHelper.query("TABLE_LOGIN", column, "phone_number=?", new String[]{phone}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_PASSWORD));
        cursor.close();
        return password;

    }
    public ArrayList<String> checkPhoneExist() {
        ArrayList<String> array = new ArrayList<String>();
        SQLiteDatabase sqLiteOpenHelper = databaseAdapter.getWritableDatabase();
        String column[] = {DatabaseAdapter.KEY_PHONE};
        Cursor cursor = sqLiteOpenHelper.query(DatabaseAdapter.TABLE_LOGIN, column, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String phoneExist = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_PHONE));
            array.add(phoneExist);

        }
       /* cursor.moveToFirst();
        int slotNumber = cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.KEY_SLOT_NUMBER));
       // cursor.close();
        //return slotNumber ;*/

        cursor.close();
        return array;
    }

    public class DatabaseAdapter extends SQLiteOpenHelper {

        Context ctx;
        public static final String DATABASE_NAME = "parking.db";
        public static final String TABLE_LOGIN = "login";
        public static final int DATABASE_VERSION = 2;

        public static final String KEY_ID = "id";

        public static final String KEY_PHONE = "phone_number";
        public static final String KEY_USER_NAME = "name";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";


        public static final String ID_BOOKING = "id_booking";
        public static final String TABLE_SLOT_BOOKING = "bokking_table";
        public static final String KEY_BASEMENT_ID = "basement_ID";
        public static final String KEY_SLOT_NUMBER = "slot_number";
        public static final String KEY_USERPHONE = "userphone_number";
        public static final String KEY_DATE = "date";
        public static final String KEY_IS_BOOKED = "boolean";


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_LOGIN + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USER_NAME + " VARCHAR(255), "
                + KEY_PHONE + " VARCHAR(255),"
                + KEY_EMAIL + " VARCHAR(255),"
                + KEY_PASSWORD + " VARCHAR(255));";

        public static final String DROP_LOGIN_TABLE = "DROP TABLE IF EXISTS " + TABLE_LOGIN;

        public static final String CREATE_BOOKING_TABLE = " CREATE TABLE " + TABLE_SLOT_BOOKING + " (" + ID_BOOKING + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USERPHONE + " VARCHAR(255), "
                + KEY_DATE + " VARCHAR(255), "
                + KEY_BASEMENT_ID + " VARCHAR(255), "
                + KEY_SLOT_NUMBER + " VARCHAR(255)) ; ";

        public static final String DROP_BOOKING_TABLE = " DROP TABLE IF EXISTS " + TABLE_SLOT_BOOKING;


        public DatabaseAdapter(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.ctx = context;
            Log.d("TAG", "DatabaseAdapter: " + CREATE_TABLE);
            Log.d("TAG", "DatabaseAdapter: " + CREATE_BOOKING_TABLE);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_BOOKING_TABLE);
                Log.d("TAG", "onCreate:database is called ");
            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("TAG", "onCreate:exception " + e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_LOGIN_TABLE);
                db.execSQL(DROP_BOOKING_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


}

