package fertilizantes.com.projectfert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import fertilizantes.fertilizantes.sqldb.FertilizanteSQL;

public class MainActivity extends Activity implements TextWatcher, View.OnClickListener {
    EditText buscador;
    ListView lnombre, llaboratorio, lusos, lsustancias;
    TabHost pestañas;
    CustomListAdapter cla;
    List<Fertilizante> lf = new LinkedList<>();
    List<Fertilizante> sf = new LinkedList<>();
    List<Fertilizante> uf = new LinkedList<>();
    List<Fertilizante> pf = new LinkedList<>();
    FertilizanteSQL fsql;
    TextView mensajebusqueda;
    ImageButton botonbuscar;
    ImageView img1, img2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mensajebusqueda = (TextView) findViewById(R.id.mensajebusqueda);

        buscador = (EditText) findViewById(R.id.buscador);

        lnombre = (ListView) findViewById(R.id.tabnombres);
        llaboratorio = (ListView) findViewById(R.id.tablaboratorios);
        lusos = (ListView) findViewById(R.id.tabusos);
        lsustancias = (ListView) findViewById(R.id.tabsustancias);

        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);


        botonbuscar = (ImageButton) findViewById(R.id.buscarboton);
        botonbuscar.setOnClickListener(this);

        fsql = new FertilizanteSQL();
        pestañas = (TabHost)findViewById(R.id.tabhost);
        llaboratorio.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(getApplicationContext(), DetalleActivity.class);
                inten.putExtra("parametro", lf.get(position));
                startActivity(inten);
            }
        });

        lnombre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(getApplicationContext(), DetalleActivity.class);
                inten.putExtra("parametro", pf.get(position));
                startActivity(inten);
            }
        });

        lusos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(getApplicationContext(), DetalleActivity.class);
                inten.putExtra("parametro", uf.get(position));
                startActivity(inten);
            }
        });

        lsustancias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sf.size()>0) {
                    Log.e("INDICE", String.valueOf(position));
                    Intent inten = new Intent(getApplicationContext(), DetalleActivity.class);
                    inten.putExtra("parametro", sf.get(position));
                    startActivity(inten);
                }
            }
        });

        buscador.addTextChangedListener(this);
        pestañas.setup();

        TabHost.TabSpec spec = pestañas.newTabSpec("tag1");
        spec.setContent(R.id.tabnombres);
        spec.setIndicator("NOMBRE COMERCIAL");
        pestañas.addTab(spec);

        spec=pestañas.newTabSpec("tag2");
        spec.setContent(R.id.tabsustancias);
        spec.setIndicator("INGREDIENTE ACTIVO");
        pestañas.addTab(spec);

        spec=pestañas.newTabSpec("tag3");
        spec.setContent(R.id.tablaboratorios);
        spec.setIndicator("LABORATORIO");
        pestañas.addTab(spec);

        spec=pestañas.newTabSpec("tag4");
        spec.setContent(R.id.tabusos);
        spec.setIndicator("CATEGORIA");
        pestañas.addTab(spec);

        pestañas.setCurrentTab(0);
        pestañas.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                buscador.setText("");
                if(pestañas.getCurrentTab()==1){
                    Toast.makeText(getApplicationContext(), "Use el simbolo (+) para agregar componente de bùsqueda, por ejemplo: potasio+magnesio", Toast.LENGTH_LONG).show();
                    botonbuscar.setVisibility(View.VISIBLE);
                    buscador.setHint("Presione el botón para  buscar");
                } else{
                    botonbuscar.setVisibility(View.GONE);
                    buscador.setHint("");
                }

                pestañas.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#6a3b0f"));
                pestañas.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#6a3b0f"));
                pestañas.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#6a3b0f"));
                //F49D20F07000
                pestañas.getTabWidget().getChildAt(3).setBackgroundColor(Color.parseColor("#6a3b0f"));
                ((TextView) pestañas.getTabWidget().getChildAt(0).findViewById(android.R.id.title)).setTextColor(Color.WHITE);
                ((TextView) pestañas.getTabWidget().getChildAt(1).findViewById(android.R.id.title)).setTextColor(Color.WHITE);
                ((TextView) pestañas.getTabWidget().getChildAt(2).findViewById(android.R.id.title)).setTextColor(Color.WHITE);
                ((TextView) pestañas.getTabWidget().getChildAt(3).findViewById(android.R.id.title)).setTextColor(Color.WHITE);

                pestañas.getTabWidget().getChildAt(pestañas.getCurrentTab()).setBackgroundColor(Color.TRANSPARENT); // selected
                TextView tv = (TextView) pestañas.getTabWidget().getChildAt(pestañas.getCurrentTab()).findViewById(android.R.id.title); //Unselected Tabs
                tv.setTextColor(Color.parseColor("#000000"));
            }
        });

        pestañas.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#6a3b0f"));
        pestañas.getTabWidget().setStripEnabled(false);


        pestañas.getTabWidget().setDividerDrawable(null);


        TextView ax = (TextView) pestañas.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        ax.setTextSize(8);
        ax.setSingleLine(true);

        TextView bx = (TextView) pestañas.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        bx.setTextSize(8);
        bx.setSingleLine(true);

        //bx.setGravity(Gravity.CENTER);

        TextView cx = (TextView) pestañas.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        cx.setTextSize(8);
        //cx.setGravity(Gravity.CENTER);
        cx.setSingleLine(true);


        TextView dx = (TextView) pestañas.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
        dx.setTextSize(8);
        dx.setGravity(Gravity.CENTER);
        dx.setSingleLine(true);


        pestañas.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#6a3b0f"));
        pestañas.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#6a3b0f"));
        pestañas.getTabWidget().getChildAt(3).setBackgroundColor(Color.parseColor("#6a3b0f"));
        pestañas.getTabWidget().getChildAt(pestañas.getCurrentTab()).setBackgroundColor(Color.TRANSPARENT);

        for (int i = 0; i < pestañas.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) pestañas.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
        TextView tv = (TextView) pestañas.getTabWidget().getChildAt(pestañas.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#000000"));

    }

    public final static int REQUEST_CODE= 1;





    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(pestañas.getCurrentTab()!=1) {
            if (buscador.getText().toString().length() > 0) {

                new ContenedorDatos(this).execute();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fert, menu);

        if(fsql.getEstado()){
            menu.getItem(0).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nuevousuario:
                Intent formulario = new Intent(this, ActvityFormulario.class);
                startActivityForResult(formulario, REQUEST_CODE);
                return true;
            case R.id.infoayuda:
                Intent ayuda = new Intent(this, AcitivityAyuda.class);
                startActivity(ayuda);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onClick(View v) {
        if (buscador.getText().toString().length() > 0) {
            new ContenedorDatos(this).execute();
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(buscador.getWindowToken(), 0);
        }
    }
}
