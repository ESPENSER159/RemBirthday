package com.example.rembirthday;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rembirthday.Adaptadores.ListaPersonasAdapter;
import com.example.rembirthday.entidades.Usuario;
import com.example.rembirthday.utilidades.Utilidades;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainPrincipal extends AppCompatActivity {

    public static TextView deslizar;
    RoundKornerLinearLayout add_datos;
    ImageView anima;
    ImageView anima2;
    ImageView anima3;
    ImageView anima4;
    Spinner comboDias;
    Spinner comboMeses;
    Spinner comboYears;
    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "usuario", null, 1);
    int contAcceso = 0;
    int contTouch = 0;
    public String dayEcho;
    Animation fromBottom;
    Animation fromTop;
    public boolean key0;
    public boolean key1;
    public boolean key2;
    public boolean key3;
    LinearLayout layout;
    public ArrayList<Usuario> listUsuario = new ArrayList<>();
    Context mContext;
    public String monthEcho;
    EditText name;
    public String nameEcho;
    public int notiSize = 14;
    TextView notifiday;
    TextView notifimonth;
    TextView notifiname;
    TextView notifiyear;
    SharedPreferences prefs;
    RecyclerView recyclerViewUsuarios;
    SwipeRefreshLayout swipe;
    TextView textToast;
    public String yearEcho;
    ImageView imageView;


    // Notificacion
    private static final String CHANNEL_ID = "Simplificando codigo";
    private static final String CHANNEL_NAME = "Simplicando codigo";
    private static final String CHANNEL_DESC = "Simplificando notificacion";


    @SuppressLint("ClickableViewAccessibility")
    public void onCreate(Bundle bundle) {

        // Exepcion de version de SDK para las notificaciones.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            canal.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal);
        }


        super.onCreate(bundle);
        setContentView(R.layout.main_principal);
        this.prefs = getSharedPreferences("com.mapogames.first", 0);
        imageView = findViewById(R.id.add);
        deslizar = findViewById(R.id.notifyDeslizar);


        llenarRecycler();


        this.swipe = findViewById(R.id.swipeRefresh);

        this.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("WrongConstant")
            public void onRefresh() {
                MainPrincipal.this.listUsuario.clear();
                MainPrincipal.this.llenarRecycler();
                MainPrincipal.deslizar.setVisibility(8);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MainPrincipal.this.swipe.setRefreshing(false);
                    }
                }, 1500);
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainPrincipal.this);
                dialog.requestWindowFeature(1);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog);
                ArrayList arrayList = new ArrayList();
                arrayList.add("Día");
                for (int i = 1; i <= 31; i++) {
                    arrayList.add(String.valueOf(i));
                }
                @SuppressLint("WrongConstant")
                int i2 = Calendar.getInstance().get(1);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add("Año");
                for (int i3 = i2; i3 >= 1940; i3--) {
                    arrayList2.add(String.valueOf(i3));
                }
                MainPrincipal.this.name = (EditText) dialog.findViewById(R.id.nombre);
                MainPrincipal.this.notifiname = (TextView) dialog.findViewById(R.id.notiname);
                MainPrincipal.this.notifiday = (TextView) dialog.findViewById(R.id.notidia);
                MainPrincipal.this.notifimonth = (TextView) dialog.findViewById(R.id.notimes);
                MainPrincipal.this.notifiyear = (TextView) dialog.findViewById(R.id.notiano);
                MainPrincipal.this.comboDias = (Spinner) dialog.findViewById(R.id.daySpinner);
                final ArrayAdapter arrayAdapter = new ArrayAdapter(MainPrincipal.this, R.layout.spinner_item_personalicer, arrayList);
                MainPrincipal.this.comboDias.setAdapter(arrayAdapter);
                MainPrincipal.this.comboMeses = (Spinner) dialog.findViewById(R.id.monthSpinner);
                MainPrincipal.this.comboMeses.setAdapter(ArrayAdapter.createFromResource(MainPrincipal.this, R.array.combo_months, R.layout.spinner_item_personalicer));
                MainPrincipal.this.comboYears = (Spinner) dialog.findViewById(R.id.yearSpinner);
                MainPrincipal.this.comboYears.setAdapter(new ArrayAdapter(MainPrincipal.this, R.layout.spinner_item_personalicer, arrayList2));
                MainPrincipal.this.comboDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @SuppressLint("WrongConstant")
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {

                        System.out.println("\t Reacciona");

                        ((InputMethodManager) MainPrincipal.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        if (i != 0) {
                            MainPrincipal.this.key1 = true;
                            MainPrincipal.this.dayEcho = adapterView.getItemAtPosition(i).toString();
                            MainPrincipal.this.notifiday.setText("");
                            MainPrincipal.this.notifiday.setTextSize(0.0f);
                        }
                        if (i == 0) {
                            MainPrincipal.this.key1 = false;
                        }
                    }
                });


                MainPrincipal.this.comboMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @SuppressLint("WrongConstant")
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        ((InputMethodManager) MainPrincipal.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        if (i != 0) {
                            MainPrincipal.this.key2 = true;
                            MainPrincipal.this.monthEcho = adapterView.getItemAtPosition(i).toString();
                            MainPrincipal.this.notifimonth.setText("");
                            MainPrincipal.this.notifimonth.setTextSize(0.0f);
                        }
                        if (i == 0) {
                            MainPrincipal.this.key2 = false;
                        }
                    }
                });
                MainPrincipal.this.comboYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @SuppressLint("WrongConstant")
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        ((InputMethodManager) MainPrincipal.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                        if (i != 0) {
                            MainPrincipal.this.key3 = true;
                            MainPrincipal.this.yearEcho = adapterView.getItemAtPosition(i).toString();
                            MainPrincipal.this.notifiyear.setText("");
                            MainPrincipal.this.notifiyear.setTextSize(0.0f);
                        }
                        if (i == 0) {
                            MainPrincipal.this.yearEcho = " ? ";
                            MainPrincipal.this.key3 = false;
                        }
                    }
                });
                ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (MainPrincipal.this.name.getText().toString().isEmpty()) {
                            MainPrincipal.this.notifiname.setText("Falta el Nombre");
                            MainPrincipal.this.notifiname.setTextSize((float) MainPrincipal.this.notiSize);
                            MainPrincipal.this.key0 = false;
                        }
                        if (!MainPrincipal.this.name.getText().toString().isEmpty()) {
                            MainPrincipal.this.notifiname.setText("");
                            MainPrincipal.this.notifiname.setTextSize(0.0f);
                            MainPrincipal.this.key0 = true;
                        }
                        if (!MainPrincipal.this.key1) {
                            MainPrincipal.this.notifiday.setText("Falta el Día");
                            MainPrincipal.this.notifiday.setTextSize((float) MainPrincipal.this.notiSize);
                        }
                        if (!MainPrincipal.this.key2) {
                            MainPrincipal.this.notifimonth.setText("Falta el Mes");
                            MainPrincipal.this.notifimonth.setTextSize((float) MainPrincipal.this.notiSize);
                        }
                        if (MainPrincipal.this.key0 && MainPrincipal.this.key1 && MainPrincipal.this.key2) {
                            if (!MainPrincipal.this.key3) {
                                @SuppressLint("ResourceType") final Dialog dialog = new Dialog(MainPrincipal.this, 16974126);
                                dialog.requestWindowFeature(1);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialogexception);
                                ((Button) dialog.findViewById(R.id.okexception)).setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        MainPrincipal.this.animationGlobos();
                                    }
                                });
                                dialog.show();
                            }
                            if (MainPrincipal.this.key3) {
                                MainPrincipal.this.animationGlobos();
                            }
                            MainPrincipal.this.nameEcho = MainPrincipal.this.name.getText().toString();
                            MainPrincipal.this.registrarUsuario();
                            dialog.dismiss();
                            arrayAdapter.notifyDataSetChanged();
                            MainPrincipal.this.key0 = false;
                            MainPrincipal.this.key1 = false;
                            MainPrincipal.this.key2 = false;
                            MainPrincipal.this.key3 = false;
                        }
                        System.out.println("Boton Ok");
                        PrintStream printStream = System.out;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Key 0: ");
                        sb.append(MainPrincipal.this.key0);
                        printStream.println(sb.toString());
                        PrintStream printStream2 = System.out;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Key 1: ");
                        sb2.append(MainPrincipal.this.key1);
                        printStream2.println(sb2.toString());
                        PrintStream printStream3 = System.out;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Key 2: ");
                        sb3.append(MainPrincipal.this.key2);
                        printStream3.println(sb3.toString());
                        PrintStream printStream4 = System.out;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Key 3: ");
                        sb4.append(MainPrincipal.this.key3);
                        printStream4.println(sb4.toString());
                    }
                });
                ((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        System.out.println("\tBoton Cancelar");
                        MainPrincipal.this.key0 = false;
                        MainPrincipal.this.key1 = false;
                        MainPrincipal.this.key2 = false;
                        MainPrincipal.this.key3 = false;
                        System.out.println("Boton CANCEL");
                        PrintStream printStream = System.out;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Key 0: ");
                        sb.append(MainPrincipal.this.key0);
                        printStream.println(sb.toString());
                        PrintStream printStream2 = System.out;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Key 1: ");
                        sb2.append(MainPrincipal.this.key1);
                        printStream2.println(sb2.toString());
                        PrintStream printStream3 = System.out;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Key 2: ");
                        sb3.append(MainPrincipal.this.key2);
                        printStream3.println(sb3.toString());
                        PrintStream printStream4 = System.out;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Key 3: ");
                        sb4.append(MainPrincipal.this.key3);
                        printStream4.println(sb4.toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }


    public void animationGlobos() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainPrincipal.this.Animation();
            }
        }, 300);
    }



    public void llenarRecycler() {
        this.recyclerViewUsuarios = findViewById(R.id.recyclerView);
        this.recyclerViewUsuarios.setHasFixedSize(true);
        this.recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        consultarDatos();
        this.recyclerViewUsuarios.setAdapter(new ListaPersonasAdapter(this.listUsuario, this));
    }

    /* access modifiers changed from: private */
    public void registrarUsuario() {
        SQLiteDatabase writableDatabase = this.conn.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utilidades.CAMPO_NOMBRE, this.nameEcho);
        contentValues.put(Utilidades.CAMPO_DIA, this.dayEcho);
        contentValues.put(Utilidades.CAMPO_MES, this.monthEcho);
        contentValues.put(Utilidades.CAMPO_ANO, this.yearEcho);
        writableDatabase.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_NOMBRE, contentValues);
        this.listUsuario.clear();
        llenarRecycler();
    }

    private void consultarDatos() {
        SQLiteDatabase readableDatabase = this.conn.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(Utilidades.TABLA_USUARIO);
        Cursor rawQuery = readableDatabase.rawQuery(sb.toString(), null);
        while (rawQuery.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(Integer.valueOf(rawQuery.getInt(0)));
            usuario.setNombre(rawQuery.getString(1));
            usuario.setDia(Integer.valueOf(rawQuery.getInt(2)));
            usuario.setMes(rawQuery.getString(3));
            usuario.setAno(Integer.valueOf(rawQuery.getInt(4)));
            this.listUsuario.add(usuario);
        }
    }


    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.cumple) {
            switch (id) {
                case R.id.confi1:
                    switch (this.contTouch) {
                        case 0:
                        case 1:
                            this.contTouch++;
                            return;
                        case 2:
                            this.contTouch = 0;
                            ToastCustomer(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "No me toques -_-", this);
                            return;
                        default:
                            return;
                    }
                case R.id.confi2:
                    switch (this.contAcceso) {
                        case 0:
                            this.contAcceso++;
                            return;
                        case 1:
                            ToastCustomer(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "Acceso con dos toques mas", this);
                            this.contAcceso++;
                            return;
                        case 2:
                            ToastCustomer(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "Un toque mas", this);
                            this.contAcceso++;
                            return;
                        case 3:
                            this.contAcceso = 0;
                            ToastCustomer(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "Acceso concedido", this);
                            startActivity(new Intent(this, listView.class));
                            return;
                        default:
                            return;
                    }
                default:
                    return;
            }
        } else {

            ///////////////////////////////
            ///     Campo de pruebas    ///
            ///////////////////////////////

            /*
            @SuppressLint("WrongConstant")
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.toast_welcome, null);
            Toast toast = new Toast(this);
            toast.setGravity(17, 0, 0);
            toast.setDuration(0);
            toast.setView(inflate);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    @SuppressLint("ResourceType") final Dialog dialog = new Dialog(MainPrincipal.this, 16973839);
                    dialog.requestWindowFeature(1);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_first);
                    ((Button) dialog.findViewById(R.id.okexception)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }, 2200);
            */

        }
    }


    public TextView desliza() {
        return deslizar;
    }


    @SuppressLint("WrongConstant")
    public void notificacion() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 6);
        instance.set(12, 0);
        instance.set(13, 0);

        // Cada 24 Horas (Milisegundos: 86400000) se valida si hay algun cumpleaños.
        ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(this, 1, new Intent(this, AlarmReceiver.class), 0));
    }


    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onResume() {
        super.onResume();
        if (this.prefs.getBoolean("firstrun", true)) {
            this.prefs.edit().putBoolean("firstrun", false).commit();
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.toast_welcome, null);
            Toast toast = new Toast(this);
            toast.setGravity(17, 0, 0);
            toast.setDuration(0);
            toast.setView(inflate);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    @SuppressLint("ResourceType") final Dialog dialog = new Dialog(MainPrincipal.this, 16974140);
                    dialog.requestWindowFeature(1);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_first);
                    ((Button) dialog.findViewById(R.id.okexception)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }, 2200);


            notificacion();
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

    @SuppressLint("WrongConstant")
    public void Animation() {
        this.anima = (ImageView) findViewById(R.id.globos);
        this.anima.setVisibility(0);
        this.anima2 = (ImageView) findViewById(R.id.globos2);
        this.anima2.setVisibility(0);
        this.anima3 = (ImageView) findViewById(R.id.confetti);
        this.anima3.setVisibility(0);
        this.anima4 = (ImageView) findViewById(R.id.confetti2);
        this.anima4.setVisibility(0);
        this.fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        this.fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        try {
            this.anima.startAnimation(this.fromBottom);
            this.anima2.startAnimation(this.fromBottom);
            this.anima3.startAnimation(this.fromTop);
            this.anima4.startAnimation(this.fromTop);
            this.fromBottom.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    MainPrincipal.this.anima.setVisibility(4);
                    MainPrincipal.this.anima2.setVisibility(4);
                    MainPrincipal.this.anima3.setVisibility(4);
                    MainPrincipal.this.anima4.setVisibility(4);
                }
            });
        } catch (Exception unused) {
            ToastCustomer(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "No funciona", this);
        }
    }
}
