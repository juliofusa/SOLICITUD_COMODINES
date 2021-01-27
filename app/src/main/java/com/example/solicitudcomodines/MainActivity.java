package com.example.solicitudcomodines;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // Insertar_Gestor("SIN GESTOR");
        //requestMultiplePermissions();
        CREAR_CARPETAS();

    }
    public void Insertar_Gestor(String gestor){
        BasedbHelper  usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();

       //Cursor cursor = db.rawQuery("SELECT * FROM GESTOR ", null);

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("GESTOR", gestor);
            nuevoRegistro.put("ID_ANDROID","-");
            //Insertamos el registro en la base de datos
            db.insert("GESTOR", null, nuevoRegistro);
            db.close();


    }
    private void  requestMultiplePermissions(){
        Dexter.withContext(this)
                .withPermissions(
                        //Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "Todos permisos han sido otorgados por el Usuario!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error al dar permisos! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread();}
    private void CREAR_CARPETAS(){
        // listado de carpetas a crear
        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+ADAPTADORES.R_RUTA_EXPORTACIONES);



        // comprobamoms si existen los directorios "fotopuesto" y "ORDEN DE PUESTO" y creamos carpetas y subcarpetas
        if (!DIR.exists()){
            DIR.mkdirs();
            Insertar_Gestor("SIN GESTOR");
            Toast.makeText(getApplicationContext(), "CARPETA CREADA "+Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();


        }

    }
    public void importar_COMODINES(){

        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+ADAPTADORES.R_RUTA);

        File f = new File(DIR, ADAPTADORES.A_COMODINES);

        if (f.exists()) {

            String texto;

            int N = 0;

            BasedbHelper usdbh = new BasedbHelper(this);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            if (db != null) {
                db.execSQL("DELETE FROM COMODINES");
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name = '" + "COMODINES" + "'");
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("COMODIN", "");
                db.insert("COMODINES", null, nuevoRegistro);


        }

            try
            {
                Toast.makeText(getApplicationContext(), "IMPORTANDO COMODINES... ", Toast.LENGTH_SHORT).show();

                BufferedReader COMODINES =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(f)));
                while((texto = COMODINES.readLine())!=null){
                    String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
                    String [] registro=CODIFICACION.split(";");
                    if (N==0){N += 1;}else{

                        ContentValues nuevoRegistro = new ContentValues();

                        nuevoRegistro.put("COMODIN", registro[0]);

                        db.insert("COMODINES", null, nuevoRegistro);

                        N += 1;

                    }

                }
                COMODINES.close();

                db.close();

                f.delete();

                Toast.makeText(getBaseContext(), "ARCHIVO COMODINES IMPORTADO... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

                Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    public void importar_CLIENTES(){

        File DIR = new File(Environment.getExternalStorageDirectory().getPath()+ADAPTADORES.R_RUTA);

        File f = new File(DIR, ADAPTADORES.A_CLIENTES);

        if (f.exists()) {

            String texto;

            int N = 0;

            BasedbHelper usdbh = new BasedbHelper(this);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            if (db != null) {
                db.execSQL("DELETE FROM CLIENTES");
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name = '" + "CLIENTES" + "'");
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("CLIENTE", "");
                db.insert("CLIENTES", null, nuevoRegistro);


            }

            try
            {
                Toast.makeText(getApplicationContext(), "IMPORTANDO CLIENTES... ", Toast.LENGTH_SHORT).show();

                BufferedReader COMODINES =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(f)));
                while((texto = COMODINES.readLine())!=null){
                    String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
                    String [] registro=CODIFICACION.split(";");
                    if (N==0){N += 1;}else{

                        ContentValues nuevoRegistro = new ContentValues();

                        nuevoRegistro.put("CLIENTE", registro[0]);

                        db.insert("CLIENTES", null, nuevoRegistro);

                        N += 1;

                    }

                }
                COMODINES.close();

                db.close();

                f.delete();

                Toast.makeText(getBaseContext(), "ARCHIVO CLIENTES IMPORTADO... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

                Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    private void ALERT_GESTOR(){
        final AlertDialog.Builder login= new AlertDialog.Builder(this);
        final EditText input= new EditText(this);
        input.setTextColor(Color.MAGENTA);
        login.setView(input);
        login.setTitle(R.string.ELIGIR);
        login.setMessage("GESTOR:");
        login.setIcon(R.drawable.log_eulen);
        login.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getApplicationContext(), "CANCELADO", Toast.LENGTH_SHORT).show();

            }
        });
        login.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String GESTOR = input.getText().toString().trim();

                Insertar_Gestor(GESTOR.toUpperCase());

                    Toast.makeText(getApplicationContext(), "El gestor "+GESTOR+"\n ha sido Establecido como gestor por defecto", Toast.LENGTH_SHORT).show();


            }
        });

        AlertDialog alertDialog= login.create();
        alertDialog.show();
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ALERT_GESTOR();
            return true;
        }
        if (id == R.id.CLIENTES) {
            Toast.makeText(getApplicationContext(), "HAS PULSADO: clientes", Toast.LENGTH_SHORT).show();
            importar_CLIENTES();
            return true;

        }
        if (id == R.id.COMODIN) {
            Toast.makeText(getApplicationContext(), "HAS PULSADO: COMODIN", Toast.LENGTH_SHORT).show();
            importar_COMODINES();
            return true;

        }
        if (id == R.id.ACTUALIZAR) {
            Toast.makeText(getApplicationContext(), "HAS PULSADO: ACTUALIZAR", Toast.LENGTH_SHORT).show();
            return true;}

        if (id == R.id.AYUDA) {
            Toast.makeText(getApplicationContext(), "HAS PULSADO: AYUDA", Toast.LENGTH_SHORT).show();
            return true;}

        return super.onOptionsItemSelected(item);
    }
}