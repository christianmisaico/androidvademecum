package fertilizantes.fertilizantes.sqldb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Christian Msc on 28/12/2014.
 */
public class FertilizanteSQL {
    String DB_PATH= "data/data/fertilizantes.com.projectfert/databases/";
    String DB_NAME = "fertilizantes";
    String myPath;
    SQLiteDatabase dbObj;

    public FertilizanteSQL(){
            myPath = DB_PATH + DB_NAME;
            dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    SQLiteDatabase db;
    public Cursor getProductos(String cadena){
        Cursor c = dbObj.rawQuery("select id, nombre, link, estado, presentacion from productos where nombre like '" + cadena +"%'", null);
        return c;
    }


    public Cursor getXLaboratorios(String cadena){
        Cursor c = dbObj.rawQuery("select productos.id, productos.nombre, productos.link, productos.estado, productos.presentacion from productos join dpl on productos.id = dpl.idProducto join laboratorios on laboratorios.id = dpl.idLaboratorio where laboratorios.nombre like '" + cadena +"%'", null);
        return c;
    }

    public Cursor getXUsos(String cadena){
        Cursor c = dbObj.rawQuery("select productos.id, productos.nombre, productos.link, productos.estado, productos.presentacion from productos join dpus on productos.id = dpus.idProducto join usos on usos.id = dpus.idUso where usos.nombre like '" + cadena + "%'", null);
        return c;
    }

    public Cursor getXSustancias(String cadena){
        String[] sustancias = cadena.split("[+]");
        String s1 ="", s2 = "", s3 = "";
        if(sustancias.length > 0 && sustancias[0] != null){
            s1 = sustancias[0];
        }
        if(sustancias.length > 1 && sustancias[1] != null){
            s2 = sustancias[1];
        }
        if(sustancias.length > 2 && sustancias[2] != null){
            s3 = sustancias[2];
        }

        Cursor c = dbObj.rawQuery("select productos.id, productos.nombre, productos.link, productos.estado, productos.presentacion" +
                " from productos where EXISTS (select s.nombre from productos p left join dps on p.id = dps.idProducto " +
                "left join sustancias s on dps.idSustancia=s.id where p.id = productos.id and s.nombre like '"+s1+"%') AND " +
                "EXISTS (select s.nombre from productos p left join dps on p.id = dps.idProducto " +
                "left join sustancias s on dps.idSustancia=s.id where p.id = productos.id and s.nombre like '"+s2+"%') AND " +
                "EXISTS (select s.nombre from productos p left " +
                "join dps on p.id = dps.idProducto " +
                "left join sustancias s on dps.idSustancia=s.id where p.id = productos.id and s.nombre like '"+s3+"%')", null);
        return c;
    }

    public Cursor getLabs(int id){
        Cursor c = dbObj.rawQuery("select laboratorios.nombre from laboratorios join dpl on laboratorios.id=dpl.idLaboratorio where dpl.idProducto = " + id, null);
        return c;
    }

    public Cursor getUsos(int id){
        Cursor c = dbObj.rawQuery("select usos.nombre from usos join dpus on usos.id=dpus.idUso where dpus.idProducto = " + id, null);
        return c;
    }

    public Cursor getSust(int id){
        Cursor c = dbObj.rawQuery("select sustancias.nombre from sustancias join dps on sustancias.id=dps.idSustancia where dps.idProducto = " + id, null);
        return c;
    }

    public Cursor getCult(int id){
        Cursor c = dbObj.rawQuery("select cultivos.nombre from cultivos join dpc on cultivos.id=dpc.idCultivo where dpc.idProducto = " + id, null);
        return c;
    }

    public void actualizarr(){
        dbObj.execSQL("update estado set estado=1");
    }

    public boolean getEstado(){
        Cursor c = dbObj.rawQuery("select estado from estado", null);
        int a = 0;
        while (c.moveToNext()) {
            a = c.getInt(0);
            Log.e("c", String.valueOf(a));
        }
        if(a==1){ return true; } else { return false; }
    }


}
