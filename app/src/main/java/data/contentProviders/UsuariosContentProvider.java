package data.contentProviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import data.bd.DataHelper;

/**
 * Created by wilsoncastiblanco on 10/3/15.
 */
public class UsuariosContentProvider extends ContentProvider {

  public static final String URI = "content://org.android.food.modelo.contentProviders.UsuariosContentProvider/Usuarios";
  public static final Uri CONTENT_URI = Uri.parse(URI);

  //Base de datos
  private DataHelper dataHelper;
  private static final String BD_NOMBRE = "DBAndroidCourse";
  private static final int BD_VERSION = 1;


  //Clase interna para declarar las constantes de columna
  public static final class Usuarios implements BaseColumns
  {
    private Usuarios() {}

    //Nombres de columnas
    public static final String COL_USUARIO = DataHelper.USER_LOGIN_COLUMN;
    public static final String COL_PASSWORD = DataHelper.USER_PASSWORD_COLUMN;
    public static final String COL_NAMES = DataHelper.USER_NAMES_COLUMN;
  }


  //UriMatcher
  private static final int ACCESSO_GENERICO_USUARIOS = 1;
  private static final int ACCESO_DIRECTO_USUARIOS_ID = 2;
  private static final UriMatcher uriMatcher;

  //Inicializamos el UriMatcher
  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI("org.android.food.modelo.contentProviders.UsuariosContentProvider", "Usuarios", ACCESSO_GENERICO_USUARIOS);
    uriMatcher.addURI("org.android.food.modelo.contentProviders.UsuariosContentProvider", "Usuarios/#", ACCESO_DIRECTO_USUARIOS_ID);
  }




  @Override
  public boolean onCreate() {
    dataHelper = new DataHelper(getContext());
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    //Si es una consulta a un ID concreto construimos el WHERE

    // selection = "usuario = 'android' and password = '123'"
    String where = selection;

    if(uriMatcher.match(uri) == ACCESO_DIRECTO_USUARIOS_ID){
      where = "_id=" + uri.getLastPathSegment();
    }

    // where = "_id='2'"

    SQLiteDatabase db = dataHelper.getWritableDatabase();

    Cursor c = db.query(DataHelper.USERS_TABLE, projection, where,
        selectionArgs, null, null, sortOrder);

    return c;
  }

  @Override
  public String getType(Uri uri) {
    int match = uriMatcher.match(uri);

    switch (match)
    {
      case ACCESSO_GENERICO_USUARIOS:
        return "vnd.android.cursor.dir/vnd.cursoandroid.usuario";
      case ACCESO_DIRECTO_USUARIOS_ID:
        return "vnd.android.cursor.item/vnd.cursoandroid.usuario";
      default:
        return null;
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    long regId = -1;

    SQLiteDatabase db = dataHelper.getWritableDatabase();

    regId = db.insert(DataHelper.USERS_TABLE, null, values);

    Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

    return newUri;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    int cont;

    //Si es una consulta a un ID concreto construimos el WHERE
    String where = selection;
    if(uriMatcher.match(uri) == ACCESO_DIRECTO_USUARIOS_ID){
      where = "_id=" + uri.getLastPathSegment();
    }

    SQLiteDatabase db = dataHelper.getWritableDatabase();

    cont = db.delete(DataHelper.USERS_TABLE, where, selectionArgs);

    return cont;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    int cont;

    //Si es una consulta a un ID concreto construimos el WHERE
    String where = selection;
    if(uriMatcher.match(uri) == ACCESO_DIRECTO_USUARIOS_ID){
      where = "_id=" + uri.getLastPathSegment();
    }

    SQLiteDatabase db = dataHelper.getWritableDatabase();

    cont = db.update(DataHelper.USERS_TABLE, values, where, selectionArgs);


    return cont;
  }
}
