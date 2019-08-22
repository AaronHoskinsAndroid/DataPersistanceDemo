package examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database;

public class PhoneDatabaseContract {
    //psfs {enter}
    public static final String DATABASE_NAME = "phones_db";
    public static final String TABLE_NAME = "phones_table";
    //psfi {enter}
    public static final int DATABASE_VERSION = 1;
    public static final String COL_BRAND = "brand";
    public static final String COL_SIZE = "size";
    public static final String COL_MODEL = "model";
    public static final String COL_OS = "os_type";
    public static final String COL_ID = "id";

    //Create Table Query
    public static final String QUERY_CREATE_TABLE =
            String.format("CREATE TABLE %s(%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)",
                    TABLE_NAME, COL_BRAND, COL_MODEL, COL_SIZE, COL_OS, COL_ID);

    //Select All Query
    public static final String QUERY_SELECT_ALL = String.format("SELECT * FROM %s", TABLE_NAME);

    //Select by ID
    public static String QUERY_SELECT_BY_ID(String id) {
        return String.format("SELECT * FROM %s WHERE %s = \'%s\'", TABLE_NAME, COL_ID, id);
    }

    //Drop Table Query
    public static final String DROP_TABLE = String.format("DROP TABLE %s", TABLE_NAME);
}
