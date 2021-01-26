package com.example.solicitudcomodines;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BasedbHelper  usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM GESTOR ", null);
        if (cursor.getCount()==0){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("GESTOR", "SIN GESTOR");
            nuevoRegistro.put("ID_ANDROID","-");
            //Insertamos el registro en la base de datos
            db.insert("GESTOR", null, nuevoRegistro);
            db.close();
        }

        //requestMultiplePermissions();
        CREAR_CARPETAS();

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
            Toast.makeText(getApplicationContext(), "CARPETA CREADA "+Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();


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
            return true;
        }
        if (id == R.id.CLIENTES) {
            Toast.makeText(getApplicationContext(), "HAS PULSADO: clientes", Toast.LENGTH_SHORT).show();
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