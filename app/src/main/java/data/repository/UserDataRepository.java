package data.repository;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.sitestoeat.interactor.UserInteractorListener;
import com.example.sitestoeat.model.User;

import android.content.Context;
import android.net.Uri;

import data.api.Requests;
import data.api.RestConstants;
import data.bd.dataAccess.UsuariosDataAccess;
import domain.repository.UserRepository;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class UserDataRepository implements UserRepository {

    private UserInteractorListener userInteractorListener;
    private Context context;

    public UserDataRepository(UserInteractorListener userInteractorListener) {
        this.userInteractorListener = userInteractorListener;
    }

    @Override
    public void loginUser(User user, Context context) {
        this.context = context.getApplicationContext();
        if (UsuariosDataAccess.getInstance().validarSiUsuarioExiste(context, user.getUserName())) {
            User userDB = UsuariosDataAccess.getInstance().validarLogin(context, user.getUserName(),
                    user.getPassword());
            if (userDB != null) {
                userInteractorListener.onSuccess();
            } else {
                userInteractorListener.onError();
            }
        } else {
            Requests baseRequest = new Requests();
            baseRequest.setOnRequestSuccess(new Requests.Listener() {
                @Override
                public void OnRequestSuccess(JSONObject jsonObject) {
                    validarRespuesta(jsonObject);
                }
            });
            /**
             * 1er parámetro: endpoint
             * 2do parámetro: método (POST, GET, PUT etc )
             * 3er parámetro: Si aplica, parametros a enviar al endpoint
             * */
            baseRequest.execute(RestConstants.LOGIN_ENDPOINT, RestConstants.POST, getParams(user));
        }
    }

    private String getParams(User user) {
        Uri.Builder paramsBuilder = new Uri.Builder().appendQueryParameter(RestConstants.USER, user.getUserName())
                .appendQueryParameter(RestConstants.PASSWORD, user.getPassword());
        return paramsBuilder.build().getEncodedQuery();
    }

    private void validarRespuesta(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {

                if (jsonObject.getString(RestConstants.STATUS).equals(RestConstants.SUCCESS)) {
                    userInteractorListener.onSuccess();
                } else {
                    userInteractorListener.onError();
                }
            } else {
                userInteractorListener.onError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUser(User user, Context context) {
        if (UsuariosDataAccess.getInstance().registrarNuevoUsuario(context, user)) {
            userInteractorListener.onSuccess();
        } else {
            userInteractorListener.onError();
        }
    }

}
