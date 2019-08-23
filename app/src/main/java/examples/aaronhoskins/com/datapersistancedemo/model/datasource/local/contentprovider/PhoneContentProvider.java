package examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database.PhoneDatabaseContract;
import examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database.PhoneDatabaseHelper;
import examples.aaronhoskins.com.datapersistancedemo.model.phone.Phone;

import static examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.contentprovider.PhoneProviderContract.CONTENT_AUTHORITY;
import static examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.contentprovider.PhoneProviderContract.PATH_PHONE;
import static examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database.PhoneDatabaseContract.COL_ID;
import static examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database.PhoneDatabaseContract.TABLE_NAME;

public class PhoneContentProvider extends ContentProvider {
    public static final int PHONE = 100;
    public static final int PHONE_ID = 101;
    private PhoneDatabaseHelper phoneDatabaseHelper;
    private UriMatcher uriMatcher = buildUriMatcher();


    @Override
    public boolean onCreate() {
        phoneDatabaseHelper = new PhoneDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection , String selection, String[] selectionArgs, String sortby) {
        SQLiteDatabase sqLiteDatabase = phoneDatabaseHelper.getReadableDatabase();
        Cursor returnCursor = null;

        switch(uriMatcher.match(uri)) {
            case PHONE:
                returnCursor = sqLiteDatabase.query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortby);
                break;
            case PHONE_ID:
                returnCursor = sqLiteDatabase.query(
                        TABLE_NAME,
                        projection,
                        PhoneProviderContract.PhoneEntry._ID + " = ?",
                        new String []{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortby);
                break;
        }

        return returnCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)) {
            case PHONE:
                return PhoneProviderContract.PhoneEntry.CONTENT_TYPE;
            case PHONE_ID:
                return PhoneProviderContract.PhoneEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = phoneDatabaseHelper.getWritableDatabase();
        long id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Uri returnUri = PhoneProviderContract.PhoneEntry.buildPhoneUri(id);
        getContext().getContentResolver().notifyChange(returnUri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase sqLiteDatabase = phoneDatabaseHelper.getWritableDatabase();
        int id = sqLiteDatabase.delete(TABLE_NAME, COL_ID + " = ?", strings);
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase sqLiteDatabase = phoneDatabaseHelper.getWritableDatabase();
        int id = sqLiteDatabase.update(TABLE_NAME, contentValues, COL_ID + " = ?", strings);
        return id;
    }

    public UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PHONE, PHONE);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PHONE + "/#", PHONE_ID);
        return uriMatcher;
    }
}
