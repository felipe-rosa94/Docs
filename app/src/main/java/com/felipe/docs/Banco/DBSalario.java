package com.felipe.docs.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBSalario {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "salario.sqb";
    private static final String DATABASE_TABLE = "salario";

    private static final String _ID = "Id";
    private static final String _SALARIO_TOTAL = "SalarioTotal";
    private static final String _SALARIO_QUINZENA = "SalarioQuinzena";
    private static final String _SALARIO_MES = "SalarioMes";
    private static final String _DATA_QUINZENA = "DataQuinzena";
    private static final String _DATA_MES = "DataMes";
    private static final String _DIA_FECHAMENTO = "DiaFechamento";
    private static final String _CONTAS = "Contas";
    private static final String _OUTROS = "Outros";

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + _ID + " integer primary key, "
                    + _SALARIO_TOTAL + " text, "
                    + _SALARIO_QUINZENA + " text, "
                    + _SALARIO_MES + " text, "
                    + _DATA_QUINZENA + " text, "
                    + _DATA_MES + " text, "
                    + _CONTAS + " text, "
                    + _DIA_FECHAMENTO + " text, "
                    + _OUTROS + " text "
                    + ");";

    public class Struc {
        public int Id = 1;
        public String SalarioTotal = "";
        public String SalarioQuinzena = "";
        public String SalarioMes = "";
        public String DataQuinzena = "";
        public String DataMes = "";
        public String DiaFechamento = "";
        public String Contas = "";
        public String Outros = "";
    }

    public final Struc Fields = new Struc();

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBSalario(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public boolean emtpyTable() {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL("insert into salario (Id,SalarioTotal,SalarioQuinzena,SalarioMes,DataQuinzena,DataMes,DiaFechamento,Contas,Outros) values (1,\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Database", "Atualizando da versão " + oldVersion
                    + " para "
                    + newVersion + ". Isto destruirá todos os dados.");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE + ";");
            onCreate(db);
        }
    }

    public void open() throws SQLException {
        try {
            db = DBHelper.getWritableDatabase();
            getId(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        DBHelper.close();
    }

    private ContentValues getArgs() {
        ContentValues args = new ContentValues();
        args.put(_ID, Fields.Id);
        args.put(_SALARIO_TOTAL, Fields.SalarioTotal);
        args.put(_SALARIO_QUINZENA, Fields.SalarioQuinzena);
        args.put(_SALARIO_MES, Fields.SalarioMes);
        args.put(_DATA_QUINZENA, Fields.DataQuinzena);
        args.put(_DATA_MES, Fields.DataMes);
        args.put(_DIA_FECHAMENTO, Fields.DiaFechamento);
        args.put(_CONTAS, Fields.Contas);
        args.put(_OUTROS, Fields.Outros);
        return args;
    }

    public void getFields(Cursor c) {
        if (!c.isAfterLast() && !c.isBeforeFirst()) {
            int i = 0;
            Fields.Id = c.getInt(i++);
            Fields.SalarioTotal = c.getString(i++);
            Fields.SalarioQuinzena = c.getString(i++);
            Fields.SalarioMes = c.getString(i++);
            Fields.DataQuinzena = c.getString(i++);
            Fields.DataMes = c.getString(i++);
            Fields.DiaFechamento = c.getString(i++);
            Fields.Contas = c.getString(i++);
            Fields.Outros = c.getString(i++);
        }
    }

    public long insert() {
        return db.insert(DATABASE_TABLE, null, getArgs());
    }

    public boolean update(long Id) {
        return db.update(DATABASE_TABLE, getArgs(), _ID + "=" + Id, null) > 0;
    }

    public boolean delete(long Id) {
        return db.delete(DATABASE_TABLE, _ID + "=" + Id, null) > 0;
    }

    public Cursor getAll() {
        return getWhere(null);
    }

    public Cursor getId(long Id) throws SQLException {
        Cursor c = getWhere(_ID + "=" + Id);
        return c;
    }

    public Cursor getWhere(String Where) throws SQLException {
        Cursor c =
                db.query(DATABASE_TABLE, new String[]{
                                _ID,
                                _SALARIO_TOTAL,
                                _SALARIO_QUINZENA,
                                _SALARIO_MES,
                                _DATA_QUINZENA,
                                _DATA_MES,
                                _DIA_FECHAMENTO,
                                _CONTAS,
                                _OUTROS},
                        Where,
                        null,
                        null,
                        null,
                        _ID);

        if (c != null) {
            c.moveToFirst();
            getFields(c);
        }
        return c;
    }
}
