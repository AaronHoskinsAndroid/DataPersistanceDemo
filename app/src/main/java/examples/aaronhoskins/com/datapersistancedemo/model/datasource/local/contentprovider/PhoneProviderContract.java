package examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.contentprovider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PhoneProviderContract {
    //Uri Information
    public static final String CONTENT_AUTHORITY = "examples.aaronhoskins.com.datapersistancedemo";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PHONE = "phone";

    public static final class PhoneEntry implements BaseColumns{
        public static final Uri PHONE_CONTENT_URI = CONTENT_URI.buildUpon().appendPath("phone").build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir" + PHONE_CONTENT_URI + "/phone";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item"+ PHONE_CONTENT_URI + "/phone";

        public static Uri buildPhoneUri(long id) {
            return ContentUris.withAppendedId(PHONE_CONTENT_URI, id);
        }

    }

}
