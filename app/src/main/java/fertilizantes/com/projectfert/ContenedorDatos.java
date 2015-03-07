package fertilizantes.com.projectfert;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ContenedorDatos extends AsyncTask<Void, Void, CustomListAdapter> {
    WeakReference<MainActivity> context;

    public ContenedorDatos(MainActivity activity) {
        context = new WeakReference<>(activity);
    }


    @Override
    protected void onPreExecute(){
        //pb.setVisibility(View.VISIBLE);
        context.get().botonbuscar.setEnabled(false);
    }

    @Override
    protected void onPostExecute(CustomListAdapter result) {
        super.onPostExecute(result);
        switch (context.get().pestañas.getCurrentTab()){
            case 0:
                context.get().lnombre.setAdapter(result);
                break;
            case 1:
                context.get().lsustancias.setAdapter(result);
                break;
            case 2:
                context.get().llaboratorio.setAdapter(result);
                break;
            case 3:
                context.get().lusos.setAdapter(result);
                break;
        }
        context.get().botonbuscar.setEnabled(true);
    }

    @Override
    protected CustomListAdapter doInBackground(Void... params) {
        ArrayList<String> productos = new ArrayList<>();
        ArrayList<String> otros = new ArrayList<>();
        ArrayList<Integer> estados = new ArrayList<>();
        if(context.get().pestañas.getCurrentTab()==0){
            Cursor c = context.get().fsql.getProductos(context.get().buscador.getText().toString());
            context.get().pf.clear();
            while (c.moveToNext()) {
                context.get().pf.add(new Fertilizante(c.getInt(0), c.getString(1), c.getString(4), c.getString(2), c.getInt(3)));
                productos.add(c.getString(1));
                otros.add(c.getString(4));
                estados.add(c.getInt(3));
            }
            context.get().cla = new CustomListAdapter(context.get(), productos, otros, estados);
        } else if(context.get().pestañas.getCurrentTab()==1){
            Cursor c = context.get().fsql.getXSustancias(context.get().buscador.getText().toString());
            String[] sustancias = context.get().buscador.getText().toString().split("[+]");
            String s1 ="-", s2 = "-", s3 = "-";
            if(sustancias[0] != null){
                s1 = sustancias[0];
            }
            if(sustancias.length > 1 && sustancias[1] != null){
                s2 = sustancias[1];
            }
            if(sustancias.length > 2 && sustancias[2] != null){
                s3 = sustancias[2];
            }

            context.get().sf.clear();
            while (c.moveToNext()) {
                context.get().sf.add(new Fertilizante(c.getInt(0), c.getString(1), c.getString(4), c.getString(2), c.getInt(3)));
                String subr = "";
                Cursor c2 = context.get().fsql.getSust(c.getInt(0));
                boolean esPrimero = true;
                while (c2.moveToNext()) {
                    if(c2.getString(0).indexOf(s1.toUpperCase()) == 0 || c2.getString(0).indexOf(s2.toUpperCase()) == 0
                            || c2.getString(0).indexOf(s3.toUpperCase()) == 0){
                        if(esPrimero) {
                            subr = subr + c2.getString(0);
                        } else {
                            subr = subr + ", " + c2.getString(0);
                        }
                        esPrimero = false;
                    }
                }
                productos.add(c.getString(1));
                otros.add(subr);
                estados.add(c.getInt(3));
                //Log.e("PRODUCTOS", String.valueOf(c.getInt(0)) +" ----- " + String.valueOf(c.getString(1)));
                c2.close();

            }
            context.get().cla = new CustomListAdapter(context.get(), productos, otros, estados);
            Log.e("-----------", ".....................");

            c.close();
        } else if(context.get().pestañas.getCurrentTab()==2){
            Cursor c = context.get().fsql.getXLaboratorios(context.get().buscador.getText().toString());
            context.get().lf.clear();

            while (c.moveToNext()) {
                context.get().lf.add(new Fertilizante(c.getInt(0), c.getString(1), c.getString(4), c.getString(2), c.getInt(3)));

                String subr = "";
                Cursor c2 = context.get().fsql.getLabs(c.getInt(0));
                boolean esPrimero = true;
                while (c2.moveToNext()) {
                    if(c2.getString(0).indexOf(context.get().buscador.getText().toString().toUpperCase()) == 0){
                        if(esPrimero) {
                            subr = subr + c2.getString(0);
                        } else {
                            subr = subr + ", " + c2.getString(0);
                        }
                        esPrimero = false;
                    }

                }
                productos.add(c.getString(1));
                otros.add(subr);
                estados.add(c.getInt(3));
                c2.close();

            }

            context.get().cla = new CustomListAdapter(context.get(), productos, otros, estados);
            c.close();
        } else if(context.get().pestañas.getCurrentTab()==3){
            Cursor c = context.get().fsql.getXUsos(context.get().buscador.getText().toString());
            context.get().uf.clear();
            while (c.moveToNext()) {
                String subr = "";
                context.get().uf.add(new Fertilizante(c.getInt(0), c.getString(1), c.getString(4), c.getString(2), c.getInt(3)));
                Cursor c2 = context.get().fsql.getUsos(c.getInt(0));
                boolean esPrimero = true;
                while (c2.moveToNext()) {
                    if(c2.getString(0).indexOf(context.get().buscador.getText().toString().toUpperCase()) == 0){
                        if(esPrimero) {
                            subr = subr + c2.getString(0);
                        } else {
                            subr = subr + ", " + c2.getString(0);
                        }
                        esPrimero = false;
                    }
                }

                productos.add(c.getString(1));
                otros.add(subr);
                estados.add(c.getInt(3));
                c2.close();
            }

            context.get().cla = new CustomListAdapter(context.get(), productos, otros, estados);
            c.close();
        }
        return context.get().cla;
    }
}
