package com.aula.atividades.datatotable;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_Tipo extends Activity {
SQLController sqlcon;
    final Add_Event ligar_tela_evento = new Add_Event();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__tipo);

        sqlcon = new SQLController(this);

        final EditText ET_tipo_desc = (EditText)findViewById(R.id.ET_tipo_tipo);
        final Button BT_add = (Button)findViewById(R.id.BT_tipo_add);



        BT_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sqlcon.open();
                String tipo=ET_tipo_desc.getText().toString();
                ET_tipo_desc.setText("");
                sqlcon.insertData(tipo, "teste");
                sqlcon.close();

            }
        });
    }

}
