package com.example.sitestoeat.view.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sitestoeat.R;
import data.api.Requests;
import data.api.RestConstants;
import com.example.sitestoeat.view.adapters.RestaurantsAdapter;
import com.example.sitestoeat.model.Restaurant;
import com.example.sitestoeat.view.services.RequestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RestaurantsActivityFragment extends Fragment {

    ListView listViewRestaurants;

    private List<Restaurant> restaurants;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurants, container, false);
        listViewRestaurants = (ListView) rootView.findViewById(R.id.listViewRestaurants);
        //callRestaurantsEndpoint();
        registerAlarm();
        registerBroadcastFilter();
        callRequestService();
        return rootView;
    }

    private void registerAlarm() {
        Intent intent = new Intent(getActivity().getApplicationContext(), RequestService.class);
        intent.putExtra(RequestService.ENDPOINT_KEY, RestConstants.GET_RESTAURANTES);
        intent.putExtra(RequestService.REQUEST_METHOD_KEY, RestConstants.POST);

        PendingIntent pendingIntentService = PendingIntent.getService(getActivity().getApplicationContext(), 31213, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 5000, pendingIntentService);

    }

    /**
     * Con este método se registra el BroadcastReceiver que creamos
     * #RequestReceiver, primero se crea el intent filter
     * luego se le añade la acción de la cual estará pendiente el RequestReceiver (Broadcast)
     * y por último se registra el Broadcast en la actividad
     * */
    private void registerBroadcastFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RequestService.ACTION_SEND_RESTAURANTS);

        RequestReceiver requestReceiver = new RequestReceiver();
        getActivity().registerReceiver(requestReceiver, intentFilter);
    }

    private void callRequestService() {
        Intent intent = new Intent(getActivity().getApplicationContext(), RequestService.class);
        intent.putExtra(RequestService.ENDPOINT_KEY, RestConstants.GET_RESTAURANTES);
        intent.putExtra(RequestService.REQUEST_METHOD_KEY, RestConstants.POST);
        getActivity().startService(intent);
    }

    /**
     * Este método no se usará en esta versión del proyecto ya que estaremos usando un
     * IntentService en vez de AsyncTasks pero el llamado es el mismo
     */
    private void callRestaurantsEndpoint() {
        Requests requests = new Requests();
        requests.setOnRequestSuccess(new Requests.Listener() {
            @Override
            public void OnRequestSuccess(JSONObject jsonObject) {
                validateJsonResponse(jsonObject);
            }
        });
        requests.execute(RestConstants.GET_RESTAURANTES, RestConstants.POST);
    }

    private void validateJsonResponse(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                JSONArray jsonArrayRestaurants = jsonObject.getJSONArray("data");

                Type typeTokenListRestaurant = new TypeToken<List<Restaurant>>() {
                }.getType();

                restaurants = new Gson().fromJson(jsonArrayRestaurants.toString(), typeTokenListRestaurant);

                RestaurantsAdapter restaurantsAdapter = new RestaurantsAdapter(getActivity().getApplicationContext(), restaurants);

                listViewRestaurants.setAdapter(restaurantsAdapter);

            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.error_data_restaurants, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class RequestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(RequestService.ACTION_SEND_RESTAURANTS)) {
                try {
                    String response = intent.getStringExtra(RequestService.RESTAURANTS_KEY);
                    JSONObject jsonObject = new JSONObject(response);
                    validateJsonResponse(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
