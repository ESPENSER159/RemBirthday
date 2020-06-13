package com.example.rembirthday.Adaptadores;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rembirthday.ConexionSQLiteHelper;
import com.example.rembirthday.MainPrincipal;
import com.example.rembirthday.R;
import com.example.rembirthday.entidades.Usuario;
import com.example.rembirthday.utilidades.Utilidades;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class ListaPersonasAdapter extends RecyclerView.Adapter<ListaPersonasAdapter.ViewHolderDatos> {

    ArrayList<Usuario> listaUsuario;
    Context mContext;
    int pos;
    /* access modifiers changed from: private */
    public String quienId;
    /* access modifiers changed from: private */
    public String quienName;

    public int quienDia;

    public int quienAno;

    public String quienMes;


    /* renamed from: s */
    MainPrincipal clasePrin = new MainPrincipal();

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView ano;
        TextView dia;

        /* renamed from: id */
        TextView id;
        TextView menu;
        TextView mes;
        TextView nombre;

        public ViewHolderDatos(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textid);
            this.nombre = (TextView) view.findViewById(R.id.textNombre);
            this.dia = (TextView) view.findViewById(R.id.textDay);
            this.mes = (TextView) view.findViewById(R.id.textMonth);
            this.ano = (TextView) view.findViewById(R.id.textYear);
            this.menu = (TextView) view.findViewById(R.id.idMenu);
        }
    }

    @SuppressLint({"Registered"})
    public class deleteDialog extends AppCompatActivity {
        Button buttonNo;
        Button buttonSi;
        Spinner comboDays;
        Spinner comboMonths;
        Spinner comboanos;
        public String dayEcho;
        public TextView dia;

        /* renamed from: id */
        public TextView id;
        public boolean key0;
        public boolean key1;
        public boolean key2;
        public boolean key3;
        public TextView mes;
        public String monthEcho;
        public TextView name;
        public String nameEcho;
        public int notiSize = 14;
        public TextView notifiday;
        public TextView notifimonth;
        public TextView notifiname;
        public TextView notifiyear;
        public TextView year;
        public String yearEcho;



        public void dialogDelete() {
            final Dialog dialog = new Dialog(ListaPersonasAdapter.this.mContext);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_delete);
            this.buttonSi = (Button) dialog.findViewById(R.id.deleteSi);
            this.buttonNo = (Button) dialog.findViewById(R.id.deleteNo);
            this.buttonSi.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ListaPersonasAdapter.this.listaUsuario.remove(ListaPersonasAdapter.this.pos);
                    ListaPersonasAdapter.this.notifyDataSetChanged();
                    dialog.dismiss();
                    SQLiteDatabase writableDatabase = new ConexionSQLiteHelper(ListaPersonasAdapter.this.mContext, "usuario", null, 1).getWritableDatabase();
                    String str = Utilidades.TABLA_USUARIO;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Utilidades.CAMPO_ID);
                    sb.append("=?");
                    writableDatabase.delete(str, sb.toString(), new String[]{ListaPersonasAdapter.this.quienId});
                    MainPrincipal mainPrincipal = ListaPersonasAdapter.this.clasePrin;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Se Elimino: ");
                    sb2.append(ListaPersonasAdapter.this.quienName);
                    mainPrincipal.ToastCustomer(120, sb2.toString(), ListaPersonasAdapter.this.mContext);
                }
            });
            this.buttonNo.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }


        @SuppressLint("WrongConstant")
        public void dialogModify() {

            Calendar.getInstance();

            // Obtener Año actual
            int anoActual = Calendar.getInstance().get(1) + 1;

            SQLiteDatabase writableDatabase = new ConexionSQLiteHelper(ListaPersonasAdapter.this.mContext, "usuario", null, 1).getWritableDatabase();

            Cursor cursor = writableDatabase.rawQuery("SELECT nombre, dia, mes, ano FROM usuario WHERE id="+ListaPersonasAdapter.this.quienId, null);
            cursor.moveToFirst();

            String nombre = cursor.getString(0);
            String dia = cursor.getString(1);
            String mes = cursor.getString(2);
            String ano = cursor.getString(3);

            cursor.close();

            int anoConvert = 0;

            if (Objects.equals(ano, " ? ")){
                anoConvert = 0;
            }else if(ano != null){
                anoConvert = Integer.parseInt(ano) - anoActual;

                anoConvert *= -1;
            }


            System.out.println("ID: " + ListaPersonasAdapter.this.quienId + "\n"
                    + "Nombre: " + nombre + "\n"
                    + "Día: " + dia + " Tipo: " + dia.getClass().getSimpleName() + "\n"
                    + "Mes: " + mes + " Tipo: " + mes.getClass().getSimpleName() + "\n"
                    + "Año:"+ano+"Tipo: " + mes.getClass().getSimpleName() + " InSpinner: " + anoConvert + "\n");


            int mesConvert = 0;

            switch (mes){
                case "Enero":
                    mesConvert = 1;
                    break;
                case "Febrero":
                    mesConvert = 2;
                    break;
                case "Marzo":
                    mesConvert = 3;
                    break;
                case "Abril":
                    mesConvert = 4;
                    break;
                case "Mayo":
                    mesConvert = 5;
                    break;
                case "Junio":
                    mesConvert = 6;
                    break;
                case "Julio":
                    mesConvert = 7;
                    break;
                case "Agosto":
                    mesConvert = 8;
                    break;
                case "Septiembre":
                    mesConvert = 9;
                    break;
                case "Octubre":
                    mesConvert = 10;
                    break;
                case "Noviembre":
                    mesConvert = 11;
                    break;
                case "Diciembre":
                    mesConvert = 12;
                    break;
            }




            final Dialog dialog = new Dialog(ListaPersonasAdapter.this.mContext);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_modify);
            ArrayList arrayList = new ArrayList();
            arrayList.add("Día");
            for (int i = 1; i <= 31; i++) {
                arrayList.add(String.valueOf(i));
            }
            int i2 = Calendar.getInstance().get(1);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add("Año");
            for (int i3 = i2; i3 >= 1940; i3--) {
                arrayList2.add(String.valueOf(i3));
            }
            this.id = (TextView) dialog.findViewById(R.id.textid);
            this.name = (TextView) dialog.findViewById(R.id.nombre_modify);
            this.notifiname = (TextView) dialog.findViewById(R.id.notiname_modify);
            this.notifiday = (TextView) dialog.findViewById(R.id.notidia_modify);
            this.notifimonth = (TextView) dialog.findViewById(R.id.notimes_modify);
            this.notifiyear = (TextView) dialog.findViewById(R.id.notiano_modify);
            this.name.setText(ListaPersonasAdapter.this.quienName);
            this.comboDays = (Spinner) dialog.findViewById(R.id.daySpinner_modify);
            this.comboDays.setAdapter(new ArrayAdapter(ListaPersonasAdapter.this.mContext, R.layout.spinner_item_personalicer, arrayList));
            this.comboDays.setSelection(Integer.parseInt(dia));
            this.comboMonths = (Spinner) dialog.findViewById(R.id.monthSpinner_modify);
            this.comboMonths.setAdapter(ArrayAdapter.createFromResource(ListaPersonasAdapter.this.mContext, R.array.combo_months, R.layout.spinner_item_personalicer));
            this.comboMonths.setSelection(mesConvert);
            this.comboanos = (Spinner) dialog.findViewById(R.id.yearSpinner_modify);
            this.comboanos.setAdapter(new ArrayAdapter(ListaPersonasAdapter.this.mContext, R.layout.spinner_item_personalicer, arrayList2));
            this.comboanos.setSelection(anoConvert);
            this.comboDays.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != 0) {
                        deleteDialog.this.key1 = true;
                        deleteDialog.this.dayEcho = adapterView.getItemAtPosition(i).toString();
                        deleteDialog.this.notifiday.setText("");
                        deleteDialog.this.notifiday.setTextSize(0.0f);
                    }
                    if (i == 0) {
                        deleteDialog.this.key1 = false;
                    }
                }
            });
            this.comboMonths.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != 0) {
                        deleteDialog.this.key2 = true;
                        deleteDialog.this.monthEcho = adapterView.getItemAtPosition(i).toString();
                        deleteDialog.this.notifimonth.setText("");
                        deleteDialog.this.notifimonth.setTextSize(0.0f);
                    }
                    if (i == 0) {
                        deleteDialog.this.key2 = false;
                    }
                }
            });
            this.comboanos.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (i != 0) {
                        deleteDialog.this.key3 = true;
                        deleteDialog.this.yearEcho = adapterView.getItemAtPosition(i).toString();
                        deleteDialog.this.notifiyear.setText("");
                        deleteDialog.this.notifiyear.setTextSize(0.0f);
                    }
                    if (i == 0) {
                        deleteDialog.this.key3 = false;
                    }
                }
            });
            ((Button) dialog.findViewById(R.id.ok_modify)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (deleteDialog.this.name.getText().toString().isEmpty()) {
                        deleteDialog.this.notifiname.setText("falta el Nombre");
                        deleteDialog.this.notifiname.setTextSize((float) deleteDialog.this.notiSize);
                        deleteDialog.this.key0 = false;
                    }
                    if (!deleteDialog.this.name.getText().toString().isEmpty()) {
                        deleteDialog.this.notifiname.setText("");
                        deleteDialog.this.notifiname.setTextSize(0.0f);
                        deleteDialog.this.key0 = true;
                    }
                    if (!deleteDialog.this.key1) {
                        deleteDialog.this.notifiday.setText("falta el Dia");
                        deleteDialog.this.notifiday.setTextSize((float) deleteDialog.this.notiSize);
                    }
                    if (!deleteDialog.this.key2) {
                        deleteDialog.this.notifimonth.setText("falta el Mes");
                        deleteDialog.this.notifimonth.setTextSize((float) deleteDialog.this.notiSize);
                    }
                    if (deleteDialog.this.key0 && deleteDialog.this.key1 && deleteDialog.this.key2) {
                        deleteDialog.this.nameEcho = deleteDialog.this.name.getText().toString();
                        SQLiteDatabase writableDatabase = new ConexionSQLiteHelper(ListaPersonasAdapter.this.mContext, "usuario", null, 1).getWritableDatabase();
                        String[] strArr = {ListaPersonasAdapter.this.quienId};
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Utilidades.CAMPO_NOMBRE, deleteDialog.this.nameEcho);
                        contentValues.put(Utilidades.CAMPO_DIA, deleteDialog.this.dayEcho);
                        contentValues.put(Utilidades.CAMPO_MES, deleteDialog.this.monthEcho);
                        contentValues.put(Utilidades.CAMPO_ANO, deleteDialog.this.yearEcho);
                        String str = Utilidades.TABLA_USUARIO;
                        StringBuilder sb = new StringBuilder();
                        sb.append(Utilidades.CAMPO_ID);
                        sb.append("=?");
                        writableDatabase.update(str, contentValues, sb.toString(), strArr);
                        ListaPersonasAdapter.this.clasePrin.ToastCustomer(120, "Se Actualizo", ListaPersonasAdapter.this.mContext);
                        Toast.makeText(mContext, "Desliza hacia abajo para actualizar", Toast.LENGTH_LONG).show();
                        writableDatabase.close();
                        dialog.dismiss();
                        deleteDialog.this.key0 = false;
                        deleteDialog.this.key1 = false;
                        deleteDialog.this.key2 = false;
                        deleteDialog.this.key3 = false;
                        ListaPersonasAdapter.this.clasePrin.desliza().setVisibility(0);
                    }
                    System.out.println("Boton Ok");
                    PrintStream printStream = System.out;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Key 0: ");
                    sb2.append(deleteDialog.this.key0);
                    printStream.println(sb2.toString());
                    PrintStream printStream2 = System.out;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Key 1: ");
                    sb3.append(deleteDialog.this.key1);
                    printStream2.println(sb3.toString());
                    PrintStream printStream3 = System.out;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Key 2: ");
                    sb4.append(deleteDialog.this.key2);
                    printStream3.println(sb4.toString());
                    PrintStream printStream4 = System.out;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Key 3: ");
                    sb5.append(deleteDialog.this.key3);
                    printStream4.println(sb5.toString());
                }
            });
            ((Button) dialog.findViewById(R.id.cancel_modify)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    System.out.println("\tBoton Cancelar");
                    deleteDialog.this.key0 = false;
                    deleteDialog.this.key1 = false;
                    deleteDialog.this.key2 = false;
                    deleteDialog.this.key3 = false;
                    System.out.println("Boton CANCEL");
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Key 0: ");
                    sb.append(deleteDialog.this.key0);
                    printStream.println(sb.toString());
                    PrintStream printStream2 = System.out;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Key 1: ");
                    sb2.append(deleteDialog.this.key1);
                    printStream2.println(sb2.toString());
                    PrintStream printStream3 = System.out;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Key 2: ");
                    sb3.append(deleteDialog.this.key2);
                    printStream3.println(sb3.toString());
                    PrintStream printStream4 = System.out;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Key 3: ");
                    sb4.append(deleteDialog.this.key3);
                    printStream4.println(sb4.toString());
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public ListaPersonasAdapter(ArrayList<Usuario> arrayList, Context context) {
        this.listaUsuario = arrayList;
        this.mContext = context;
    }

    public ViewHolderDatos onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolderDatos(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_datos, null, false));
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(final ViewHolderDatos viewHolderDatos, final int i) {
        Calendar.getInstance();
        int i2 = Calendar.getInstance().get(1);
        String str = " ? ";
        if (((Usuario) this.listaUsuario.get(i)).getAno().intValue() >= 1940) {
            str = Integer.toString(i2 - ((Usuario) this.listaUsuario.get(i)).getAno().intValue());
        }
        viewHolderDatos.id.setText(((Usuario) this.listaUsuario.get(i)).getId().toString());
        viewHolderDatos.nombre.setText(((Usuario) this.listaUsuario.get(i)).getNombre());
        viewHolderDatos.dia.setText(((Usuario) this.listaUsuario.get(i)).getDia().toString());
        viewHolderDatos.mes.setText(((Usuario) this.listaUsuario.get(i)).getMes());
        viewHolderDatos.ano.setText(str);
        viewHolderDatos.menu.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ListaPersonasAdapter.this.mContext, viewHolderDatos.menu);
                popupMenu.inflate(R.menu.menu);
                ListaPersonasAdapter.this.quienId = ((Usuario) ListaPersonasAdapter.this.listaUsuario.get(i)).getId().toString();
                ListaPersonasAdapter.this.quienName = ((Usuario) ListaPersonasAdapter.this.listaUsuario.get(i)).getNombre();
                ListaPersonasAdapter.this.pos = i;
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        deleteDialog deletedialog = new deleteDialog();
                        switch (menuItem.getItemId()) {
                            case R.id.mnu_item_delete /*2131361909*/:
                                deletedialog.dialogDelete();
                                break;
                            case R.id.mnu_item_save /*2131361910*/:
                                deletedialog.dialogModify();
                                break;
                            default:
                                Toast.makeText(ListaPersonasAdapter.this.mContext, "Noting", 0).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public int getItemCount() {
        return this.listaUsuario.size();
    }

}
