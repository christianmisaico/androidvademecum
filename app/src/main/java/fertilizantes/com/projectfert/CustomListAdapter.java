package fertilizantes.com.projectfert;

import android.app.Activity;
import android.content.Context;
import android.support.v7.internal.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian Msc on 29/12/2014.
 */
public class CustomListAdapter extends  ArrayAdapter<String> {

    private final ArrayList<String> pro;
    private final ArrayList<String> otro;
    private final ArrayList<Integer> estado;
    Activity c;
    public CustomListAdapter(Activity c, ArrayList<String> pro, ArrayList<String> otro, ArrayList<Integer> estado) {
        super(c,R.layout.childrenitem, pro);
        this.c = c;
        this.pro = pro;
        this.otro = otro;
        this.estado = estado;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        LayoutInflater inflater = c.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.childrenitem, null, true);
        TextView txtpro = (TextView) rowView.findViewById(R.id.producto);
        TextView txtotr = (TextView) rowView.findViewById(R.id.otros);
        ImageView imagen = (ImageView) rowView.findViewById(R.id.estadoproducto);
        txtpro.setText(pro.get(position));
        txtotr.setText(otro.get(position));
        if(estado.get(position)==0){
            imagen.setImageResource(R.drawable.ll);
        } else {
            imagen.setImageResource(R.drawable.oo);
        }
        return rowView;
    }
}
