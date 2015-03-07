package fertilizantes.com.projectfert;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fertilizantes.fertilizantes.sqldb.FertilizanteSQL;

/**
 * Created by Christian Msc on 31/12/2014.
 */
public class DetalleActivity extends Activity {

    TextView nombre;
    TextView sustancias;
    TextView laboratorios;
    TextView usos;
    TextView cultivos;
    TextView presentacion;
    Button fichatecnica;

    FertilizanteSQL fsql;
    Fertilizante fertilizante;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        fsql = new FertilizanteSQL();
        nombre = (TextView) findViewById(R.id.nombre);
        sustancias = (TextView) findViewById(R.id.sustancias);
        laboratorios = (TextView) findViewById(R.id.laboratorios);
        usos = (TextView) findViewById(R.id.usos);
        cultivos = (TextView) findViewById(R.id.cultivos);
        presentacion = (TextView) findViewById(R.id.presentacion);
        fichatecnica = (Button) findViewById(R.id.fichatecnica);

        fertilizante = (Fertilizante) getIntent().getExtras().getSerializable("parametro");
        id = fertilizante.getId();
        nombre.setText(fertilizante.getNombre());
        presentacion.setText((fertilizante.getPresentacion()=="")?" --- ":fertilizante.getPresentacion());



        Cursor s = fsql.getSust(id);
        Cursor c = fsql.getCult(id);
        Cursor u = fsql.getUsos(id);
        Cursor l = fsql.getLabs(id);
        String ss = "";
        while (s.moveToNext()) {
                if(s.isFirst()) {
                    ss = ss + s.getString(0);
                } else {
                    ss = ss + ", " + s.getString(0);
                }
         }

        String sc = "";
        while (c.moveToNext()) {
            if(c.isFirst()) {
                sc = sc + c.getString(0);
            } else {
                sc = sc + ", " + c.getString(0);
            }
        }

        String su = "";
        while (u.moveToNext()) {
            if(u.isFirst()) {
                su = su + u.getString(0);
            } else {
                su = su + ", " + u.getString(0);
            }
        }

        String sl = "";
        while (l.moveToNext()) {
            if(l.isFirst()) {
                sl = sl + l.getString(0);
            } else {
                sl = sl + ", " + l.getString(0);
            }
        }


        laboratorios.setText(sl);
        usos.setText(su);
        cultivos.setText(sc);
        sustancias.setText(ss);

        fichatecnica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityWebView.class);
                intent.putExtra("link", fertilizante.getLink());
                startActivity(intent);
            }
        });



    }
}
