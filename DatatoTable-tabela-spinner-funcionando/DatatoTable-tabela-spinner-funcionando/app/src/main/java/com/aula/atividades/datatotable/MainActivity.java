package com.aula.atividades.datatotable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

    TableLayout table_layout;
    EditText firstname_et, lastname_et;
    Button addmem_btn;
    Button add_evento;

    SQLController sqlcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlcon = new SQLController(this);

        firstname_et = (EditText) findViewById(R.id.fistname_et_id);
        lastname_et = (EditText) findViewById(R.id.lastname_et_id);
        addmem_btn = (Button) findViewById(R.id.addmem_btn_id);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        add_evento = (Button)findViewById(R.id.BT_add_evento);

        BuildTable();

        addmem_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                table_layout.removeAllViews();
                String firstname = firstname_et.getText().toString();
                String lastname = lastname_et.getText().toString();

                firstname_et.setText("");
                lastname_et.setText("");

                // inserting data
                sqlcon.open();
                sqlcon.insertData(firstname, lastname);
                BuildTable();

            }
        });

        add_evento.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adicionar = new Intent(MainActivity.this, Add_Event.class);
                startActivity(adicionar);
            }
        });

    }

    public void BuildTable() {

        sqlcon.open();
        Cursor c = sqlcon.readEntry();
        int rows = c.getCount();
        int cols = c.getColumnCount();

        c.moveToFirst();

        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);

                tv.setText(c.getString(j));

                row.addView(tv);

            }

            c.moveToNext();

            table_layout.addView(row);

        }
        sqlcon.close();
    }

    @Override
    protected void onResume() {
        //Atualiza a Tabela quando esta navegando entra as activitys
        //Exclui tudo antes de criar
        table_layout.removeAllViews();
        //Cria a tabela
        BuildTable();
        super.onResume();
    }
}
