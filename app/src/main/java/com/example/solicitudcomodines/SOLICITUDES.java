package com.example.solicitudcomodines;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SOLICITUDES extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private Spinner fecha,comodin;
    private String COMODIN,FECHA="";
    private TextView fechafiltro;
    private TimePickerDialog hora_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_l_i_c_i_t_u_d_e_s);

        fechafiltro=findViewById(R.id.Txt_F_FECHA);
         comodin=findViewById(R.id.sp_comodin);
         fecha=findViewById(R.id.Txt_fecha_filtro);
        LLenar();
        recycler = findViewById(R.id.recliclado_CARD);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

       // lManager = new ConstraintLayout(this);
        recycler.setLayoutManager(mLayoutManager);

// Crear un nuevo adaptador
        List listaItemsCursos = getCursorList(1);
        //adapter = new CartaCursoAdapter(listaItemsCursos, this);
        adapter = new AnimeAdapter(listaItemsCursos);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }
    private void LLenar(){
        BasedbHelper  usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        Cursor C_comodines= db.rawQuery("SELECT * FROM COMODINES ", null);
        SimpleCursorAdapter adapterCOMODINES = new SimpleCursorAdapter(this,R.layout.custom_spinner_item1,C_comodines,(new String[] {"COMODIN"}), new int[] {R.id.Spiner_text},0);
        comodin.setAdapter(adapterCOMODINES);
        comodin.setOnItemSelectedListener(this);
    }
    public  List<SOLICITUD> getCursorList( Integer T){

        List<SOLICITUD>  list= new ArrayList<>();

        String QUERY="";

        if (T==1){
                       QUERY="SELECT * FROM SOLICITUD ";
            }else{
            if (T==2){ QUERY="SELECT * FROM SOLICITUD WHERE COMODIN='"+COMODIN+"'";
            }else{
                if(T==3){
                      QUERY="SELECT * FROM SOLICITUD WHERE FECHA='"+FECHA+"'";
                }else{
                    if(T==4){QUERY="SELECT * FROM SOLICITUD WHERE COMODIN='"+COMODIN+"' AND FECHA='"+FECHA+"'";}
                }
            }
        }
        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c=db.rawQuery(QUERY,null);



        while (c.moveToNext()){
            SOLICITUD Anime = new SOLICITUD();

            Anime.setFECHA(c.getString(3));
            Anime.setCLIENTE(c.getString(4));
            Anime.setGESTOR(c.getString(1));
            Anime.setCOMODIN(c.getString(7));
            Anime.setHORA_ENTRADA(c.getString(5));
            Anime.setHORA_SALIDA(c.getString(6));
            Anime.setOBSERVACION(c.getString(8));
            //Toast.makeText(getApplicationContext(), "FECHA... "+c.getString(3), Toast.LENGTH_SHORT).show();
            list.add(Anime);
        }

        return list;}
    public void setdateDialog(View V){
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 2);
        int year = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int diasemana= c.get(Calendar.DAY_OF_WEEK);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker View, int year, int mes, int dia) {

                FECHA=Cadena_fecha(dia)+"/"+Cadena_fecha(mes+1)+"/"+year;

                fechafiltro.setText(FECHA);

                Integer T=3;
                if(COMODIN.equals("")){T=3;}else{T=4;}
                List listaItemsCursos = getCursorList(T);

                adapter = new AnimeAdapter(listaItemsCursos);
                recycler.setAdapter(adapter);
                recycler.setItemAnimator(new DefaultItemAnimator());


            }
        }
                ,dia, mes, year);
        datePickerDialog.updateDate(year,mes,dia);
        datePickerDialog.show();

    }
    private String Cadena_fecha(Integer fecha ){
        String f_MINUTO;
        switch (fecha){
            case 0:
                f_MINUTO="00";

                break;
            case 1:
                f_MINUTO="01";

                break;
            case 2:
                f_MINUTO="02";

                break;
            case 3:
                f_MINUTO="03";

                break;
            case 4:
                f_MINUTO="04";

                break;
            case 5:
                f_MINUTO="05";

                break;
            case 6:
                f_MINUTO="06";

                break;
            case 7:
                f_MINUTO="07";

                break;
            case 8:
                f_MINUTO="08";

                break;
            case 9:
                f_MINUTO="09";

                break;
            default:
                f_MINUTO= String.valueOf(fecha);

        }
        return f_MINUTO;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);

        switch (adapterView.getId()) {
            case R.id.sp_comodin:
                Cursor COM=(Cursor)adapterView.getItemAtPosition(i);
                 COMODIN=COM.getString(COM.getColumnIndex(ADAPTADORES.C_COLUMNA_COMODIN));
                //Toast.makeText(getApplicationContext(), "Comodin.. "+COMODIN, Toast.LENGTH_SHORT).show();
                Integer T=1;
                if (!COMODIN.equals("") && FECHA.equals("")){T=2;}else{
                    if (!COMODIN.equals("") && !FECHA.equals("")){T=4;}else{T=1;fechafiltro.setText("");FECHA="";}
               }
                List listaItemsCursos = getCursorList(T);

                adapter = new AnimeAdapter(listaItemsCursos);
                recycler.setAdapter(adapter);
                recycler.setItemAnimator(new DefaultItemAnimator());
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}