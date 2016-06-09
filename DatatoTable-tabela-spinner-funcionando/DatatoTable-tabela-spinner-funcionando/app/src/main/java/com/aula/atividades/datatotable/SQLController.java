package com.aula.atividades.datatotable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

	private MyDbHelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new MyDbHelper(ourcontext);
		database = dbhelper.getWritableDatabase();
		return this;

	}

	public void close() {
		dbhelper.close();
	}

	public void insertData(String name, String lname) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(MyDbHelper.MEMBER_FIRSTNAME, name);
		cv.put(MyDbHelper.MEMBER_LASTNAME, lname);
		database.insert(MyDbHelper.TABLE_MEMBER, null, cv);

	}
	public void insertTipo(String novo_tipo) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(MyDbHelper.CAMPO_TIPO_DESC, novo_tipo);
		database.insert(MyDbHelper.TABLE_TIPO, null, cv);
	}

	public Cursor VerTipos() {
		// TODO Auto-generated method stub
		String[] allColumns = new String[] { MyDbHelper.CAMPO_TIPO_COD, MyDbHelper.CAMPO_TIPO_DESC};

		Cursor c = database.query(MyDbHelper.TABLE_TIPO, allColumns, null, null, null,
				null, null);

		if (c != null) {
			c.moveToFirst();
		}
		return c;

	}

	public Cursor readEntry() {
		// TODO Auto-generated method stub
		String[] allColumns = new String[] { MyDbHelper.MEMBER_ID, MyDbHelper.MEMBER_FIRSTNAME,
				MyDbHelper.MEMBER_LASTNAME };

		Cursor c = database.query(MyDbHelper.TABLE_MEMBER, allColumns, null, null, null,
				null, null);

		if (c != null) {
			c.moveToFirst();
		}
		return c;

	}

}
