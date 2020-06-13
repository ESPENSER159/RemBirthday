package com.example.rembirthday;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.rembirthday.entidades.Usuario;
import com.example.rembirthday.utilidades.Utilidades;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "Simplificando codigo";

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        SQLiteDatabase readableDatabase = new ConexionSQLiteHelper(context, "usuario", null, 1).getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT nombre, dia, mes, ano  FROM ");
        sb.append(Utilidades.TABLA_USUARIO);
        Cursor rawQuery = readableDatabase.rawQuery(sb.toString(), null);
        while (rawQuery.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setNombre(rawQuery.getString(rawQuery.getColumnIndex("nombre")));
            usuario.setDia(Integer.valueOf(rawQuery.getInt(rawQuery.getColumnIndex("dia"))));
            usuario.setMes(rawQuery.getString(rawQuery.getColumnIndex("mes")));
            usuario.setAno(Integer.valueOf(rawQuery.getInt(rawQuery.getColumnIndex("ano"))));
            arrayList.add(usuario);
        }
        rawQuery.close();
        readableDatabase.close();
        int canalNotifi = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Calendar instance = Calendar.getInstance();
            char c = 11;
            int i3 = instance.get(Calendar.HOUR);
            int i4 = instance.get(Calendar.DAY_OF_MONTH);
            int i5 = instance.get(Calendar.MONTH);
            int i6 = Calendar.getInstance().get(Calendar.YEAR);
            String valueOf = String.valueOf(i5);
            int hashCode = valueOf.hashCode();
            switch (hashCode) {
                case 48:
                    if (valueOf.equals("0")) {
                        c = 0;
                        break;
                    }
                case 49:
                    if (valueOf.equals("1")) {
                        c = 1;
                        break;
                    }
                case 50:
                    if (valueOf.equals("2")) {
                        c = 2;
                        break;
                    }
                case 51:
                    if (valueOf.equals("3")) {
                        c = 3;
                        break;
                    }
                case 52:
                    if (valueOf.equals("4")) {
                        c = 4;
                        break;
                    }
                case 53:
                    if (valueOf.equals("5")) {
                        c = 5;
                        break;
                    }
                case 54:
                    if (valueOf.equals("6")) {
                        c = 6;
                        break;
                    }
                case 55:
                    if (valueOf.equals("7")) {
                        c = 7;
                        break;
                    }
                case 56:
                    if (valueOf.equals("8")) {
                        c = 8;
                        break;
                    }
                case 57:
                    if (valueOf.equals("9")) {
                        c = 9;
                        break;
                    }
                default:
                    switch (hashCode) {
                        case 1567:
                            if (valueOf.equals("10")) {
                                c = 10;
                                break;
                            }
                        case 1568:
                            break;
                    }
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    valueOf = "Enero";
                    break;
                case 1:
                    valueOf = "Febrero";
                    break;
                case 2:
                    valueOf = "Marzo";
                    break;
                case 3:
                    valueOf = "Abril";
                    break;
                case 4:
                    valueOf = "Mayo";
                    break;
                case 5:
                    valueOf = "Junio";
                    break;
                case 6:
                    valueOf = "Julio";
                    break;
                case 7:
                    valueOf = "Agosto";
                    break;
                case 8:
                    valueOf = "Septiembre";
                    break;
                case 9:
                    valueOf = "Octubre";
                    break;
                case 10:
                    valueOf = "Noviembre";
                    break;
                case 11:
                    valueOf = "Diciembre";
                    break;
            }

            if (((Usuario) arrayList.get(i2)).getDia().intValue() == i4 && ((Usuario) arrayList.get(i2)).getMes().equals(valueOf) && i3 >= 6 && i3 <= 8) {
                String edad = "";
                String espace = "";
                if (((Usuario) arrayList.get(i2)).getAno().intValue() >= 1940) {
                    edad = " " + Integer.toString(+i6 - ((Usuario) arrayList.get(i2)).getAno().intValue());
                    espace = " ";
                }
                PrintStream printStream = System.out;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Hoy Cumple ");
                sb2.append(((Usuario) arrayList.get(i2)).getNombre());
                sb2.append(espace);
                sb2.append(edad);
                sb2.append(" años");
                printStream.println(sb2.toString());

                // Contador para el canal de la notificacion.
                canalNotifi++;


                // Mostrar Notificacion
                NotificationCompat.Builder contentTitle = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.cake)
                        .setContentTitle("¡Cumpleaños!")
                        .setContentText(((Usuario) arrayList.get(i2)).getNombre() + " esta cumpliendo" + edad + " años");

                NotificationManagerCompat managerNotifi = NotificationManagerCompat.from(context);

                managerNotifi.notify(canalNotifi, contentTitle.build());

                System.out.println("\n\n\n\tNotificando...\n\n\n");
            }
        }
    }
}
