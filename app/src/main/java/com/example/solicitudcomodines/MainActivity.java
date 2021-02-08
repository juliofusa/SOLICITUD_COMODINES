package com.example.solicitudcomodines;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity  {
    private static final int CODIGO_PERMISOS_write = 1;
    // Banderas que indicarán si tenemos permisos
    private boolean   tienePermisoAlmacenamiento = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int estadoDePermiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // Aquí el usuario dio permisos para acceder
        } else {
            // Si no, entonces pedimos permisos...
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_write);
            Insertar_Gestor("SIN GESTOR");
        }
       // Insertar_Gestor("SIN GESTOR");


        CREAR_CARPETAS();

    }



    public void Insertar_Gestor(String gestor ){
        BasedbHelper  usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();

       //Cursor cursor = db.rawQuery("SELECT * FROM GESTOR ", null);
        String Id_android;

        Integer l=gestor.length();

            Id_android=String.valueOf(l)+gestor.substring(l-1,l)+gestor.substring(2,3)+String.valueOf(l-2)+gestor.substring(0,1)+gestor.substring(3,4);

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("GESTOR", gestor);
            nuevoRegistro.put("ID_ANDROID",Id_android);
            //Insertamos el registro en la base de datos
            db.insert("GESTOR", null, nuevoRegistro);
            db.close();


    }

    private void CREAR_CARPETAS(){
        // listado de carpetas a crear
        File DIR = new File(this.getExternalFilesDir(null)+ADAPTADORES.R_RUTA_EXPORTACIONES);



        // comprobamoms si existen los directorios "fotopuesto" y "ORDEN DE PUESTO" y creamos carpetas y subcarpetas
        if (!DIR.exists()){
            DIR.mkdirs();
            Insertar_Gestor("SIN GESTOR");
            //Toast.makeText(getApplicationContext(), "CARPETA CREADA "+Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();


        }

    }
    public void importar_COMODINES(){

        File DIR = new File(this.getExternalFilesDir(null)+ADAPTADORES.R_RUTA);

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
                nuevoRegistro.put("DNI", "");
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

                        nuevoRegistro.put("DNI", registro[1]);

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

        File DIR = new File(this.getExternalFilesDir(null)+ADAPTADORES.R_RUTA);

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
        login.setMessage("GESTOR (4 min):");
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
    private void verificarYPedirPermisosDeAlmacenamiento() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoDeAlmacenamientoConcedido();
        } else {
            // Si no, entonces pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_write);
        }
    }
    private void permisoDeAlmacenamientoConcedido() {
        // Aquí establece las banderas o haz lo que
        // ibas a hacer cuando el permiso se concediera. Por
        // ejemplo puedes poner la bandera en true y más
        // tarde en otra función comprobar esa bandera
        Toast.makeText(MainActivity.this, "El permiso para el almacenamiento está concedido", Toast.LENGTH_SHORT).show();
        tienePermisoAlmacenamiento = true;
    }

    private void permisoDeAlmacenamientoDenegado() {
        // Esto se llama cuando el usuario hace click en "Denegar" o
        // cuando lo denegó anteriormente
        Toast.makeText(MainActivity.this, "El permiso para el almacenamiento está denegado", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CODIGO_PERMISOS_write:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permisoDeAlmacenamientoConcedido();
                } else {
                    permisoDeAlmacenamientoDenegado();
                }
                break;
            // Aquí más casos dependiendo de los permisos
            // case OTRO_CODIGO_DE_PERMISOS...
        }
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
            importar_CLIENTES();
            importar_COMODINES();
            return true;}

        if (id == R.id.AYUDA) {
            Toast.makeText(getApplicationContext(), "HAS PULSADO: AYUDA", Toast.LENGTH_SHORT).show();
            return true;}

        return super.onOptionsItemSelected(item);
    }
}