package com.example.rembirthday;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.rembirthday.entidades.Usuario;
import com.example.rembirthday.utilidades.Utilidades;

import java.util.ArrayList;

public class listView extends AppCompatActivity {

    ArrayAdapter adapter;
    ConexionSQLiteHelper conn;
    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuario;

    TextView textToast;

    /* access modifiers changed from: protected */
    @SuppressLint("ResourceType")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.list_view);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), "usuario", null, 1);
        this.listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);
        consultarListaPersonas();
        this.adapter = new ArrayAdapter(this, 17367043, this.listaInformacion);
        this.adapter.notifyDataSetChanged();
        this.listViewPersonas.setAdapter(this.adapter);
        this.listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                SQLiteDatabase writableDatabase = listView.this.conn.getWritableDatabase();
                String num = ((Usuario) listView.this.listaUsuario.get(i)).getId().toString();
                String str = Utilidades.TABLA_USUARIO;
                StringBuilder sb = new StringBuilder();
                sb.append(Utilidades.CAMPO_ID);
                sb.append("=?");
                writableDatabase.delete(str, sb.toString(), new String[]{num});

                listView.this.finish();
                listView.this.overridePendingTransition(0, 0);
                listView.this.startActivity(listView.this.getIntent());
                listView.this.overridePendingTransition(0, 0);

                notificaCustom();

            }
        });
    }


    public void notificaCustom(){
        ToastCustomer(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "Se Elimino", this);
    }


    private void consultarListaPersonas() {
        SQLiteDatabase readableDatabase = this.conn.getReadableDatabase();
        this.listaUsuario = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(Utilidades.TABLA_USUARIO);
        Cursor rawQuery = readableDatabase.rawQuery(sb.toString(), null);
        while (rawQuery.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(Integer.valueOf(rawQuery.getInt(0)));
            usuario.setNombre(rawQuery.getString(1));
            usuario.setDia(Integer.valueOf(rawQuery.getInt(2)));
            this.listaUsuario.add(usuario);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        this.listaInformacion = new ArrayList<>();
        for (int i = 0; i < this.listaUsuario.size(); i++) {
            ArrayList<String> arrayList = this.listaInformacion;
            StringBuilder sb = new StringBuilder();
            sb.append(((Usuario) this.listaUsuario.get(i)).getId());
            sb.append(" - ");
            sb.append(((Usuario) this.listaUsuario.get(i)).getNombre());
            arrayList.add(sb.toString());
        }
    }

    @SuppressLint("WrongConstant")
    public void ToastCustomer(int i, String str, Context context) {
        String str2 = "\t\t\t\t\t";
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast_custom, null);
        this.textToast = (TextView) inflate.findViewById(R.id.toastCustomText);
        TextView textView = this.textToast;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        sb.append(str2);
        textView.setText(sb.toString());
        Toast toast = new Toast(context);
        toast.setGravity(80, 0, i);
        toast.setDuration(0);
        toast.setView(inflate);
        toast.show();
    }

}
