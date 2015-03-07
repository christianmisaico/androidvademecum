package fertilizantes.com.projectfert;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian Msc on 11/01/2015.
 */

public class SugerenciaRequest extends AsyncTask<String, Integer, Boolean> {
    WeakReference<SugerenciaActivity> context;

    public SugerenciaRequest(SugerenciaActivity activity) {
        context = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute(){
        Toast.makeText(context.get(), "Preparandose.", Toast.LENGTH_LONG).show();
        context.get().enviar.setEnabled(false);
        context.get().setCargando();
    }

    @Override
    protected Boolean doInBackground(String... p) {
        boolean t = enviarDatos(p[0]);
        return t;
    }
    protected void onProgressUpdate(Integer... progress) {
    }
    public boolean enviarDatos(String sugerencia) {

        HttpClient httpclient = new DefaultHttpClient();
        Log.e("INFO", "**esponse");
        HttpPost httppost = new HttpPost("http://insuagro.com.pe/vademecum/public/enviarsugerencia");
        try {
            List<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("sugerencia", sugerencia));
            httppost.setEntity(new UrlEncodedFormEntity(valores));
            HttpResponse response = httpclient.execute(httppost);
            String encoding = EntityUtils.getContentCharSet(response.getEntity());
            encoding = encoding == null ? "UTF-8" : encoding;
            InputStream stream = AndroidHttpClient.getUngzippedContent(response.getEntity());
            InputStreamEntity unzEntity = new InputStreamEntity(stream,-1);
            String strResponse = EntityUtils.toString(unzEntity, encoding);
            Log.e("Informaciion ", "**** Esta es la respuesta:" + strResponse);

                return true;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            Log.e("Informacion", e.getMessage());
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("Informaciion2 ", e.getMessage());
            return false;
        }
    }
    protected void onPostExecute(Boolean result) {
        if(result){
            Toast.makeText(context.get(), "La sugerencia ha sido enviada.", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(context.get(), "Error en el envio. No llego a enviarse", Toast.LENGTH_LONG).show();
        }
        context.get().enviar.setEnabled(true);
        context.get().setDefault();
    }
}
