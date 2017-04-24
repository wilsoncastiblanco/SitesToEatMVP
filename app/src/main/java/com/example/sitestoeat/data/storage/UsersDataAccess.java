package com.example.sitestoeat.data.storage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.sitestoeat.Infrastructure.DataAccessManager;
import com.example.sitestoeat.Infrastructure.contentProviders.UsuariosContentProvider;
import com.example.sitestoeat.presentation.model.User;

/**
 * Created by wilsoncastiblanco on 10/13/15.
 */
public class UsersDataAccess {

    public static UsersDataAccess INSTANCE;
    private ContentResolver contentResolver;

    private Uri usuariosUri = UsuariosContentProvider.CONTENT_URI;

    public UsersDataAccess() {
        this.contentResolver = DataAccessManager.getInstance().getContentResolver();
    }

    public static UsersDataAccess getInstance() {
        return INSTANCE != null ? INSTANCE : new UsersDataAccess();
    }

    /**
     * Valida si existe el usuario en la base de datos local
     *
     * @param userName : usuario
     * @return un objeto de tipo boolean
     */
    public boolean userExists(String userName) {
        String[] columnasATraer = new String[]{
                UsuariosContentProvider.Usuarios._ID};

        String[] args = new String[]{userName};

        Cursor cursor = contentResolver.query(usuariosUri, columnasATraer,
                "users.login = ?", args, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    /**
     * Valida usuario y contraseña en nuestra base de datos
     * usando el content provider {@link UsuariosContentProvider}
     *
     * @param userName : usuario
     * @param password : contraseña
     * @return un objeto de tipo {@link User}
     */
    public User checkLogin(String userName, String password) {
        String[] columnasATraer = new String[]{
                UsuariosContentProvider.Usuarios.COL_USUARIO,
                UsuariosContentProvider.Usuarios.COL_PASSWORD};

        String[] args = new String[]{password, userName};

        Cursor cursor = contentResolver.query(usuariosUri, columnasATraer,
                "users.password = ? and users.login = ?", args, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            String userPassword = cursor.getString(cursor.getColumnIndex(UsuariosContentProvider.Usuarios.COL_PASSWORD));
            String username = cursor.getString(cursor.getColumnIndex(UsuariosContentProvider.Usuarios.COL_USUARIO));
            user = new User(userName, userPassword);
            cursor.close();
        }
        return user;
    }

    /**
     * Inserta en la base de datos un nuevo usuario
     * usando el content provider {@link UsuariosContentProvider}
     *
     * @param user : nuevo usuario
     * @return un boolean
     */
    public boolean newUser(User user) {
        /** En este punto estamos creando nuestros valores para guardar**/
        /** RECUERDEN: contentValues.put(nombre de la columna, valor a guardar) **/
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsuariosContentProvider.Usuarios.COL_USUARIO, user.getUserName());
        contentValues.put(UsuariosContentProvider.Usuarios.COL_PASSWORD, user.getPassword());
        contentValues.put(UsuariosContentProvider.Usuarios.COL_NAMES, user.getNames());

        /** Guardamos en la base de datos usando insert**/
        Uri uriUsuarioNuevo = contentResolver.insert(usuariosUri, contentValues);
        /** Obtenemos el id del nuevo URI devuelto por nuestro proveedor de contenido de Usuarios*/
        long _id = ContentUris.parseId(uriUsuarioNuevo);
        return _id > 0;
    }
}