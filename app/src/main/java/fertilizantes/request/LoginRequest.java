package fertilizantes.request;

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

import fertilizantes.com.projectfert.LoginActivity;
import fertilizantes.fertilizantes.sqldb.FertilizanteSQL;

/**
 * Created by Christian Msc on 10/01/2015.
 */
public class LoginRequest extends AsyncTask<String, Integer, Boolean> {
    WeakReference<LoginActivity> context;

    public LoginRequest(LoginActivity activity) {
        context = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute(){
        Toast.makeText(context.get(), "Preparandose.", Toast.LENGTH_SHORT).show();
        context.get().validad.setEnabled(false);
        context.get().cargandoVista();
    }

    @Override
    protected Boolean doInBackground(String... p) {
        boolean t = enviarDatos(p[0],p[1]);
        return t;
    }
    protected void onProgressUpdate(Integer... progress) {
    }
    public boolean enviarDatos(String usuario, String clave) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost( "http://insuagro.com.pe/vademecum/public/validarusuario");
        try {
            List<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("usuario", usuario));
            valores.add(new BasicNameValuePair("clave", clave));
            Log.e("....."+usuario, clave);
            httppost.setEntity(new UrlEncodedFormEntity(valores));
            HttpResponse response = httpclient.execute(httppost);
            Log.e("INFO", "****strResponse");
            String encoding = EntityUtils.getContentCharSet(response.getEntity());
            encoding = encoding == null ? "UTF-8" : encoding;
            InputStream stream = AndroidHttpClient.getUngzippedContent(response.getEntity());
            InputStreamEntity unzEntity = new InputStreamEntity(stream,-1);
            String strResponse = EntityUtils.toString(unzEntity, encoding);
            Log.e("Informaciion ", "**** Esta es la respuesta:" + strResponse);

            if(strResponse == "1"){
                return true;
            } else{
                return false;
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            Log.e("Informaciion1 ", e.getMessage());
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("Informaciion2 ", e.getMessage());
            return false;
        }
    }
    protected void onPostExecute(Boolean result) {
        if(result){
            Toast.makeText(context.get(), "El usuario se ha validado correctamente.", Toast.LENGTH_LONG).show();
            FertilizanteSQL sql = new FertilizanteSQL();
            sql.actualizarr();
            context.get().habilitar();

        } else{
            Toast.makeText(context.get(), "El usuario o la clave es incorrecta.", Toast.LENGTH_LONG).show();
            context.get().validad.setEnabled(true);
            context.get().defaultVista();
        }



    }
}
