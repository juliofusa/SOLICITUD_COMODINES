package com.example.solicitudcomodines;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class SecondFragment extends Fragment implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener {
    public EditText GESTOR,HORA_ENTRADA,HORA_SALIDA,fecha,OBSERV;
    public Spinner CLIENTES,COMODINES;
    private ADAPTADORES DBADAPT;
    private TimePickerDialog hora_picker;
    private DatePickerDialog fecha_picker;
    private Integer HORA_TIPO=1;
    public Button ENTRADA,SALIDA,SOLICITUD_EXPORT;
    private String CLIENTE,COMODIN;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Inicializar();
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        view.findViewById(R.id.BT_ENTRADA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HORA_TIPO=1;
                 hora_picker.show();
            }
        });
        view.findViewById(R.id.BT_SALIDA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HORA_TIPO=2;
                hora_picker.show();
            }
        });
        view.findViewById(R.id.BT_FECHA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdateDialog();
            }
        });
        view.findViewById(R.id.BT_EXPORTAR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportar();
            }
        });
    }
    private void Inicializar(){

        final String[] CLIENTESARRAY = getResources().getStringArray(R.array.CLIENTES);
        final String[] COMODINSARRAY = getResources().getStringArray(R.array.COMODINES);

        OBSERV=getActivity().findViewById(R.id.ED_OBSERVACIONES);

        ArrayAdapter<String> adaptadorcliente = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_item1, CLIENTESARRAY);

        ArrayAdapter<String> adaptadorCOMODIN = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_item1, COMODINSARRAY);

         ENTRADA=getActivity().findViewById(R.id.BT_ENTRADA);

        fecha=getActivity().findViewById(R.id.ED_FECHA);

        fecha.setText(ADAPTADORES.FECHAconformato());

        GESTOR=getActivity().findViewById(R.id.ED_GESTOR);

        BasedbHelper  usdbh = new BasedbHelper(getActivity());
        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM GESTOR ", null);

        cursor.moveToLast();

        GESTOR.setText(cursor.getString(1));

        Cursor C_comodines= db.rawQuery("SELECT * FROM COMODINES ", null);

        Cursor C_clientes= db.rawQuery("SELECT * FROM CLIENTES ", null);

        SimpleCursorAdapter adapterCOMODINES = new SimpleCursorAdapter(getActivity(),R.layout.custom_spinner_item1,C_comodines,(new String[] {"COMODIN"}), new int[] {R.id.Spiner_text},0);

        SimpleCursorAdapter adapterCLIENTES = new SimpleCursorAdapter(getActivity(),R.layout.custom_spinner_item1,C_clientes,(new String[] {"CLIENTE"}), new int[] {R.id.Spiner_text},0);


        HORA_ENTRADA=getActivity().findViewById(R.id.ED_HORA_ENTRADA);

        HORA_SALIDA=getActivity().findViewById(R.id.ED_HORA_SALIDA);

        CLIENTES=getActivity().findViewById(R.id.SP_CLIENTES);

        COMODINES=getActivity().findViewById(R.id.SP_COMODIN);

        CLIENTES.setAdapter(adapterCLIENTES);

        COMODINES.setAdapter(adapterCOMODINES);

        CLIENTES.setOnItemSelectedListener(this);

        COMODINES.setOnItemSelectedListener(this);

        setTimeField();


    }

    private void setTimeField(){

        Calendar newCalendar= Calendar.getInstance();
        int hour=newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute=newCalendar.get(Calendar.MINUTE);


        hora_picker=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String f_MINUTO;
                switch (minute){
                    case 0:
                        f_MINUTO=":00";

                        break;
                    case 1:
                        f_MINUTO=":01";

                        break;
                    case 2:
                        f_MINUTO=":02";

                        break;
                    case 3:
                        f_MINUTO=":03";

                        break;
                    case 4:
                        f_MINUTO=":04";

                        break;
                    case 5:
                        f_MINUTO=":05";

                        break;
                    case 6:
                        f_MINUTO=":06";

                        break;
                    case 7:
                        f_MINUTO=":07";

                        break;
                    case 8:
                        f_MINUTO=":08";

                        break;
                    case 9:
                        f_MINUTO=":09";

                        break;
                    default:
                        f_MINUTO= ":"+String.valueOf(minute);

                }
                switch (HORA_TIPO){
                    case 1:
                        HORA_ENTRADA.setText(hourOfDay + f_MINUTO);

                        break;
                    case 2:
                        HORA_SALIDA.setText(hourOfDay + f_MINUTO);

                        break;

                }
            }



        },hour,minute,true);

    }
    public void setdateDialog(){
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 2);
        int year = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int diasemana= c.get(Calendar.DAY_OF_WEEK);

        mensaje(String.valueOf(diasemana)+"-"+String.valueOf(dia));
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker View, int year, int mes, int dia) {

                fecha.setText(dia+"/"+(mes+1)+"/"+year);

                //if (diasemana==1 || diasemana==7){mensaje("Es un fin de semana");}
                mensaje(String.valueOf(diasemana));

            }
        }
                ,dia, mes, year);
        datePickerDialog.updateDate(year,mes,dia);
        datePickerDialog.show();

    }
    private void mensaje(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public String FECHA_FICHERO(String f) {
        String fecha_reunion=f;
        String F_REUNION_FORMAT="_"+fecha_reunion.substring(6,10)+"_"+fecha_reunion.substring(3,5)+"_"+fecha_reunion.substring(0,2)+"_";
        return F_REUNION_FORMAT;}

    public void exportar(){


        String NOMBREFICHERO=GESTOR.getText().toString()+" PRUEBA"+".txt";

        try {

            File DIR = new File(Environment.getExternalStorageDirectory().getPath()+ADAPTADORES.R_RUTA_EXPORTACIONES);
            if (!DIR.exists()){DIR.mkdir();}
            File file= new File(DIR,NOMBREFICHERO);

            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));

            String linea=System.getProperty("line.separator");

            fout.write("COMODIN"+ ";" + "CLIENTE" + ";" + "HORARIO ENTRADA" + ";" + "HORARIO SALIDA" + ";" + "HORARIO_REAL_ENTRADA" + ";" + "HORARIO_REAL_SALIDA" + ";" +  "GPS_ENTRADA" + ";" + "GPS_SALIDA" + ";" + "NOTA" + ";" + "FECHA" + ";" + "GESTOR" + ";" + "ID_ANDROID" + linea);

                String registro= "\""+ COMODIN +"\"" + ";" +"\""+ CLIENTE +"\""+ ";" +HORA_ENTRADA.getText().toString()+ ";" +HORA_ENTRADA.getText().toString()+ ";" +""+ ";" +""+ ";" +""+ ";" +""+ ";" +"\""+ OBSERV.getText().toString() + "\""+";" +fecha.getText().toString()+ ";" +"\""+GESTOR.getText().toString()+"\""+ ";" +"asd"+ ";" ;

                fout.write(registro+linea);

            fout.close();

            mensaje("DATOS EXPORTADOS");

            NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
        }



    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);

        switch (adapterView.getId()) {
            case R.id.SP_CLIENTES:
                String CLI=adapterView.getItemAtPosition(i).toString();
                //mensaje("CLIENTE: "+CLI);
                CLIENTE=CLI;
                break;
            case R.id.SP_COMODIN:
                String COMO=adapterView.getItemAtPosition(i).toString();
                //mensaje(COMO);
                COMODIN=COMO;
                break;

    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}