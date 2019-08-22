package examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import examples.aaronhoskins.com.datapersistancedemo.model.phone.Phone;

public class PhoneDatabaseHelper extends SQLiteOpenHelper {
    public PhoneDatabaseHelper(Context context) {
        super(context, PhoneDatabaseContract.DATABASE_NAME, null, PhoneDatabaseContract.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PhoneDatabaseContract.QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(PhoneDatabaseContract.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    //Select All Method
    public ArrayList<Phone> getAllPhones() {
        ArrayList<Phone> returnPhoneList = new ArrayList<>();//Return list for all found phones in DB
        SQLiteDatabase readableDatabase = this.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(PhoneDatabaseContract.QUERY_SELECT_ALL, null);

        if(cursor.moveToFirst()) {
            do {
                Phone currentPhone = new Phone();
                currentPhone.setPhoneBrand(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_BRAND)));
                currentPhone.setPhoneModel(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_MODEL)));
                currentPhone.setOsType(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_OS)));
                currentPhone.setPhoneSize(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_SIZE)));
                currentPhone.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(PhoneDatabaseContract.COL_ID))));
                returnPhoneList.add(currentPhone);
            } while(cursor.moveToNext());
        }

        return returnPhoneList;
    }

    //Select All Method
    public Phone getPhoneById(String id) {
        Phone currentPhone = new Phone();
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(PhoneDatabaseContract.QUERY_SELECT_BY_ID(id), null);

        if(cursor.moveToFirst()) {
                currentPhone.setPhoneBrand(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_BRAND)));
                currentPhone.setPhoneModel(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_MODEL)));
                currentPhone.setOsType(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_OS)));
                currentPhone.setPhoneSize(cursor.getString(cursor.getColumnIndex(PhoneDatabaseContract.COL_SIZE)));
                currentPhone.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(PhoneDatabaseContract.COL_ID))));
        }

        return currentPhone;
    }

    //Insert Phone into database
    public void insertPhoneIntoDB(Phone phoneToSave) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        //Container which is key value pairs, key being the Col of DB, value being
        // what to save in that place
        ContentValues contentValues = new ContentValues();
        //Put values in contentValues
        contentValues.put(PhoneDatabaseContract.COL_BRAND, phoneToSave.getPhoneBrand());
        contentValues.put(PhoneDatabaseContract.COL_MODEL, phoneToSave.getPhoneModel());
        contentValues.put(PhoneDatabaseContract.COL_OS, phoneToSave.getOsType());
        contentValues.put(PhoneDatabaseContract.COL_SIZE, phoneToSave.getPhoneSize());

        writableDatabase.insert(PhoneDatabaseContract.TABLE_NAME, null, contentValues);
    }

    //Update Phone in the database
    public void updatePhoneInDB(String id, Phone passedPhoneInfo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhoneDatabaseContract.COL_BRAND, passedPhoneInfo.getPhoneBrand());
        contentValues.put(PhoneDatabaseContract.COL_MODEL, passedPhoneInfo.getPhoneModel());
        contentValues.put(PhoneDatabaseContract.COL_OS, passedPhoneInfo.getOsType());
        contentValues.put(PhoneDatabaseContract.COL_SIZE, passedPhoneInfo.getPhoneSize());

        sqLiteDatabase.update(PhoneDatabaseContract.TABLE_NAME, contentValues, "ID = ?", new String[]{id});
    }

    //Delete a Phone
    public void deletePhoneInDB(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(PhoneDatabaseContract.TABLE_NAME, "ID = ?", new String[]{id});
    }
}
