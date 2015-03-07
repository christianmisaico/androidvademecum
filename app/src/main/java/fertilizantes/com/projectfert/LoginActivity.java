package fertilizantes.com.projectfert;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import fertilizantes.fertilizantes.sqldb.FertilizanteDbHelper;
import fertilizantes.fertilizantes.sqldb.FertilizanteSQL;
import fertilizantes.request.LoginRequest;

/**
 * Created by Christian Msc on 10/01/2015.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    TextView texto, msjfacebook, titulo;
    Button crear, validad, olvido;
    EditText usuario, clave;
    Button vade, sug, ayuda;
    ProgressBar circuloprogreso;
    ImageView facebook;

    FertilizanteSQL fsql;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        crear = (Button) findViewById(R.id.crearcuenta);
        olvido = (Button) findViewById(R.id.olvido);
        validad = (Button) findViewById(R.id.validar);
        usuario = (EditText) findViewById(R.id.logincorreo);
        clave = (EditText) findViewById(R.id.logincontraseña);
        msjfacebook = (TextView) findViewById(R.id.mensajefacebook);
        facebook = (ImageView) findViewById(R.id.facebook);
        texto = (TextView) findViewById(R.id.texto);
        titulo = (TextView) findViewById(R.id.titulo);

        Typeface font = Typeface.createFromAsset(getAssets(), "fuente.ttf");
        titulo.setTypeface(font);

        vade = (Button) findViewById(R.id.vademecunboton);
        sug = (Button) findViewById(R.id.sugerenciaboton);

        ayuda = (Button) findViewById(R.id.ayudaboton);

        circuloprogreso = (ProgressBar) findViewById(R.id.circleProgreso);

        FertilizanteDbHelper myDbHelper;
        myDbHelper = new FertilizanteDbHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            Log.e("ERROR", "No se ha podido crear ...........");

        }

        fsql = new FertilizanteSQL();

        vade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });

        sug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), SugerenciaActivity.class);
                startActivity(main);
            }
        });

        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ayuda = new Intent(getApplicationContext(), AcitivityAyuda.class);
                startActivity(ayuda);
            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://insuagro.com.pe/web/index.php?option=com_comprofiler&view=registers";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://insuagro.com.pe/web/index.php?option=com_comprofiler&view=lostpassword";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/insuagroperu";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        validad.setOnClickListener(this);

        habilitar();

    }

    public void cargandoVista(){
        crear.setVisibility(View.GONE);
        olvido.setVisibility(View.GONE);
        usuario.setVisibility(View.GONE);
        clave.setVisibility(View.GONE);
        texto.setVisibility(View.GONE);
        validad.setVisibility(View.GONE);
        circuloprogreso.setVisibility(View.VISIBLE);
    }

    public void defaultVista(){
        crear.setVisibility(View.VISIBLE);
        olvido.setVisibility(View.VISIBLE);
        usuario.setVisibility(View.VISIBLE);
        clave.setVisibility(View.VISIBLE);
        texto.setVisibility(View.VISIBLE);
        validad.setVisibility(View.VISIBLE);
        circuloprogreso.setVisibility(View.GONE);
    }


    public void habilitar(){
        if(fsql.getEstado()) {
            crear.setVisibility(View.GONE);
            olvido.setVisibility(View.GONE);
            usuario.setVisibility(View.GONE);
            clave.setVisibility(View.GONE);
            vade.setVisibility(View.VISIBLE);
            sug.setVisibility(View.VISIBLE);
            ayuda.setVisibility(View.VISIBLE);
            texto.setVisibility(View.GONE);
            validad.setVisibility(View.GONE);
            circuloprogreso.setVisibility(View.GONE);
            Log.e("-----", "True");

        } else {
            Log.e("-----", "Else");
            crear.setVisibility(View.VISIBLE);
            olvido.setVisibility(View.VISIBLE);
            usuario.setVisibility(View.VISIBLE);
            clave.setVisibility(View.VISIBLE);
            sug.setVisibility(View.GONE);
            ayuda.setVisibility(View.GONE);
            texto.setVisibility(View.VISIBLE);
            vade.setVisibility(View.GONE);
            validad.setVisibility(View.VISIBLE);
            circuloprogreso.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
            Log.e(usuario.getText().toString(), clave.getText().toString());
            new LoginRequest(this).execute(usuario.getText().toString(), clave.getText().toString());
    }
}
