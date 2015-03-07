package fertilizantes.com.projectfert;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fertilizantes.request.HttpHandler;

/**
 * Created by Christian Msc on 04/01/2015.
 */
public class ActvityFormulario extends Activity implements View.OnClickListener {
    Button enviar;
    EditText dni, nombres, telefonoper, email, emailcorporativo;
    TextView  fechanacimiento;
    CheckBox acepta;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        acepta = (CheckBox) findViewById(R.id.condicion);
        nombres = (EditText) findViewById(R.id.nombres);
        dni = (EditText) findViewById(R.id.dni);
        telefonoper = (EditText) findViewById(R.id.telefonopersonal);
        email = (EditText) findViewById(R.id.correo);
        fechanacimiento = (TextView) findViewById(R.id.fechanacimiento);
        fechanacimiento.setInputType(InputType.TYPE_NULL);
        emailcorporativo = (EditText) findViewById(R.id.emailcorporativo);
        enviar = (Button) findViewById(R.id.enviar);

        setDateTimeField();
        fechanacimiento.setOnClickListener(this);
        enviar.setOnClickListener(this);

    }



    private void setDateTimeField() {
        fechanacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "BIEN", Toast.LENGTH_LONG);
                Calendar newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        fechanacimiento.setText(dateFormatter.format(newDate.getTime()));

                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            }
        });


    }


    private int mYear, mMonth, mDay;



    @Override
    public void onClick(View v) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(telefonoper.getWindowToken(), 0);
        if(v == fechanacimiento) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            fechanacimiento.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
            } else if(v == enviar) {
                if(validarDatos()){
                    HttpHandler handler = new HttpHandler(this);
                    handler.execute(nombres.getText().toString(),
                            dni.getText().toString(), telefonoper.getText().toString()
                    , fechanacimiento.getText().toString(),
                            email.getText().toString());
                }
        }
    }

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }


    private boolean validarDatos(){
        boolean a = true,c = true,d = true,e = true,g = true,k = true,l= true,m= true;

        if(nombres.getText().toString().length()<1){
            Toast.makeText(getApplicationContext(), "Ingrese su nombre", Toast.LENGTH_LONG).show();
            a = false;
        } if(dni.getText().toString().length()!=8){
            Toast.makeText(getApplicationContext(), "El dni debe tener 8 digitos", Toast.LENGTH_LONG).show();
            c = false;
        } if(telefonoper.getText().toString().length()<6){
            Toast.makeText(getApplicationContext(), "Ingrese un numero telefonico valido", Toast.LENGTH_LONG).show();
            d = false;
        } if(fechanacimiento.getText().toString().length()<1){
            Toast.makeText(getApplicationContext(), "Ingrese su fecha de nacimiento", Toast.LENGTH_LONG).show();
            e = false;
        } if(validateEmail(email.getText().toString())==false){
            Toast.makeText(getApplicationContext(), "Ingrese un email valido", Toast.LENGTH_LONG).show();
            g = false;
        } if(validateEmail(emailcorporativo.getText().toString())==false){
            Toast.makeText(getApplicationContext(), "Ingrese el email de la empresa", Toast.LENGTH_LONG).show();
            k = false;
        } if(!acepta.isChecked()){
            Toast.makeText(getApplicationContext(), "Marque la casilla de terminos de uso", Toast.LENGTH_LONG).show();
            l = false;
        } if(k & l){
            if(!emailcorporativo.getText().toString().equals(email.getText().toString())){
                Toast.makeText(getApplicationContext(), "Los correos no coinciden", Toast.LENGTH_LONG).show();
                m = false;
            }
        }

        if(a && c && d && e && g && k && l && m ){
            return true;
        } else{
            return false;
        }


    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.e("NNN","START");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.e("NNN","RESTART");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.e("NNN","RESUME");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.e("NNN","PAUSE");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.e("NNN","STOP");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent databack = new Intent();
        databack.putExtra("ok","ok");
        setResult(RESULT_OK,databack);
        finish();
        Log.e("NNN","DESTROY");
    }

}
