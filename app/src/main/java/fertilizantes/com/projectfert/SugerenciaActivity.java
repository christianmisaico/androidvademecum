package fertilizantes.com.projectfert;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Christian Msc on 10/01/2015.
 */
public class SugerenciaActivity extends Activity implements View.OnClickListener{

    Button enviar;
    EditText textosugerencia;
    ProgressBar pb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencia);

        enviar = (Button) findViewById(R.id.enviarsug);
        textosugerencia = (EditText) findViewById(R.id.textsug);
        pb = (ProgressBar) findViewById(R.id.progresosugerencia);

        enviar.setOnClickListener(this);

    }

    public void setCargando(){
        pb.setVisibility(View.VISIBLE);
        textosugerencia.setVisibility(View.GONE);
        enviar.setVisibility(View.GONE);
    }

    public void setDefault(){
        pb.setVisibility(View.GONE);
        textosugerencia.setVisibility(View.VISIBLE);
        enviar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if(textosugerencia.getText().toString().length() > 10){
            new SugerenciaRequest(this).execute(textosugerencia.getText().toString());
        } else {
            Toast.makeText(this, "EL texto es muy corto", Toast.LENGTH_LONG).show();
        }

    }
}
