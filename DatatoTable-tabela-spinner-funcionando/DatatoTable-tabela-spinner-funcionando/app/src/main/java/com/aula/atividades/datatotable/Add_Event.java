package com.aula.atividades.datatotable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_Event extends Activity {

    private Calendar cal;
    private int day;
    private int month;
    private int year, hour,minute;
    private EditText data_evento, hora_inicio,hora_fim;
    private CheckBox dia_inteiro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__event);

        final SQLController sqlcon;
        final Button BTnovoTipo = (Button) findViewById(R.id.BT_add_add_Tipo);
        data_evento = (EditText)findViewById(R.id.ET_add_Date);
        hora_inicio = (EditText)findViewById(R.id.ET_add_Time_Ini);
        hora_fim = (EditText)findViewById(R.id.ET_add_Time_Fim);
        dia_inteiro=(CheckBox)findViewById(R.id.CKB_add_Dia_interio);
        final Spinner tipos_e = (Spinner)findViewById(R.id.SP_add_tipo);
        final List<String> categories = new ArrayList<String>();

        sqlcon = new SQLController(this);


        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);



        data_evento.setText(day + "/" + (month + 1) + "/" + year);

        //----------------------Abrir DataPicker quando clickar------------------------------
        data_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });

        //------------------Abrir o TimePicker Quando Clickar na hora Inicio

        hora_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TimeDialog(hora_inicio);
            }
        });

        //------------------Abrir o TimePicker Quando Clickar na Hora fim

        hora_fim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog(hora_fim);
            }
        });

        //---------------------Dia inteiro: desabilita os ET ----------//

        dia_inteiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dia_inteiro.isChecked()) {
                    hora_inicio.setEnabled(false);
                    hora_inicio.setClickable(false);
                    hora_fim.setEnabled(false);
                    hora_fim.setClickable(false);
                }else{
                    hora_inicio.setEnabled(true);
                    hora_inicio.setClickable(true);
                    hora_fim.setEnabled(true);
                    hora_fim.setClickable(true);
                }
            }
        });

        //-----------------------Criar novo tipo de evento ---------------------------------------//


        BTnovoTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------Novo intent para cadastrar Tipo
                Intent novotipo = new Intent(Add_Event.this, add_Tipo.class);
                startActivity(novotipo);

            }
        });

   AtualizaSp();

    }

    public void AtualizaSp(){
        final SQLController sqlcon;
        sqlcon = new SQLController(this);
        final Spinner tipos_e = (Spinner)findViewById(R.id.SP_add_tipo);
        final List<String> categories = new ArrayList<String>();
        sqlcon.open();
        Cursor c = sqlcon.readEntry();
        if(c.moveToFirst()){
            do{
                categories.add(c.getString(1));
            }while (c.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tipos_e.setAdapter(dataAdapter);
    }
    public void DateDialog(){

        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
            {

                data_evento.setText(dayOfMonth + "/" + monthOfYear + "/" + year);

            }
        };

        DatePickerDialog dpDialog=new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();

    }

    public void TimeDialog(final EditText hora){

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                if(hourOfDay<10 && minute<10) {
                    hora.setText("0"+hourOfDay + ":0" + minute);
                }else if(hourOfDay<10){
                    hora.setText("0"+hourOfDay + ":" + minute);
                }else if(minute<10){
                    hora.setText( hourOfDay + ":0" + minute);
                }else{
                hora.setText(hourOfDay+":"+minute);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,listener,hour,minute,true);
        timePickerDialog.show();

    }
    @Override
    public void onResume(){
        AtualizaSp();
        super.onResume();
    }
}
