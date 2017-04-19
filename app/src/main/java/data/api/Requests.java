package data.api;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wilsoncastiblanco on 8/6/16.
 */
public class Requests extends AsyncTask<String, Void, JSONObject> {

    String requestMethod; // POST, GET, PUT, UPDATE, DELETE ....
    String endpoint; // Por ejemplo obtener_restaurantes
    String params; // Si aplica

    @Override
    protected JSONObject doInBackground(String... strings) {
        endpoint = strings[0];
        requestMethod = strings[1];
        params = strings.length == 3 ? strings[2] : "";
        return callToEndpoint();
    }

    private JSONObject callToEndpoint() {
        try {
            Uri.Builder builderUrl = new Uri.Builder();

            builderUrl.scheme(RestConstants.PROTOCOL)
                    .authority(RestConstants.BASE_URL)
                    .appendPath(RestConstants.API_VERSION)
                    .appendPath(endpoint);

            String foodUrl = builderUrl.build().toString();
            URL url = new URL(foodUrl);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setReadTimeout(10000);
            client.setConnectTimeout(15000);
            client.setRequestMethod(requestMethod);
            client.setDoInput(true);
            client.setDoOutput(true);


            client.connect();

            InputStream inputStream = client.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }


            String response = stringBuilder.toString();

            return new JSONObject(response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (listener != null) {
            listener.OnRequestSuccess(jsonObject);
        }
    }

    //Events

    private Listener listener;

    public interface Listener {
        void OnRequestSuccess(JSONObject jsonObject);
    }

    public void setOnRequestSuccess(Listener listener) {
        this.listener = listener;
    }
}
