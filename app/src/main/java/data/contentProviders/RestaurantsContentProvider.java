package data.contentProviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import data.bd.DataHelper;

/**
 * Created by wilsoncastiblanco on 7/30/16.
 */
public class RestaurantsContentProvider extends ContentProvider{
    public static final String AUTHORITY = "data.contentProviders.restaurants";
    public static final String URI = "content://"+AUTHORITY+"/"+ DataHelper.RESTAURANTS_TABLE;
    public static final Uri CONTENT_URI = Uri.parse(URI);

    private DataHelper dataHelper;

    public static final class Restaurants implements BaseColumns{
        public static final String ID = DataHelper.RESTAURANT_ID_COLUMN;
        public static final String NAME = DataHelper.RESTAURANT_NAME_COLUMN;
        public static final String SPECIALITY = DataHelper.RESTAURANT_SPECIALITY_COLUMN;
        public static final String LATITUDE = DataHelper.RESTAURANT_LATITUDE_COLUMN;
        public static final String LONGITUDE = DataHelper.RESTAURANT_LONGITUDE_COLUMN;
    }

    private static final int GENERIC_ACCESS = 1;
    private static final int DIRECT_ACCESS = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,DataHelper.RESTAURANTS_TABLE, GENERIC_ACCESS);
        uriMatcher.addURI(AUTHORITY,DataHelper.RESTAURANTS_TABLE + "/#", DIRECT_ACCESS);
    }

    @Override
    public boolean onCreate() {
        dataHelper = new DataHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String where = selection;

        if(uriMatcher.match(uri) == DIRECT_ACCESS){
            where = "_id = "+uri.getLastPathSegment();
        }
        // _id = 2

        SQLiteDatabase sqLiteDatabase = dataHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(DataHelper.RESTAURANTS_TABLE, projection, where,
                selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match){
            case DIRECT_ACCESS:
                return "vnd.android.cursor.item/vnd.restaurants";
            case GENERIC_ACCESS:
                return "vnd.android.cursor.dir/vnd.restaurants";
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();

        long restaurantCreatedId = sqLiteDatabase.insert(DataHelper.RESTAURANTS_TABLE, null, contentValues);

        Uri restaurantUri = ContentUris.withAppendedId(CONTENT_URI, restaurantCreatedId);

        return restaurantUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        String where = selection;

        if(uriMatcher.match(uri) == DIRECT_ACCESS){
            where = "_id = "+uri.getLastPathSegment();
        }

        SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();

        int resultDelete = sqLiteDatabase.delete(DataHelper.RESTAURANTS_TABLE, where, selectionArgs);

        return resultDelete;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        String where = selection;

        if(uriMatcher.match(uri) == DIRECT_ACCESS){
            where = "_id = "+uri.getLastPathSegment();
        }

        SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();

        int resultDelete = sqLiteDatabase.update(DataHelper.RESTAURANTS_TABLE, contentValues, where, selectionArgs);

        return resultDelete;
    }
}
