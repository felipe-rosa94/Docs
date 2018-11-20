package com.felipe.docs.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConfig {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cods.sqb";
    private static final String DATABASE_TABLE = "cods";

    private static final String _ID = "Id";
    private static final String _SENHA = "Senha";
    private static final String _INTRO = "Intro";
    private static final String _NOME = "Nome";
    private static final String _DOCS = "Docs";
    private static final String _CARTAO = "Cartao";
    private static final String _EMAILS = "Emails";
    private static final String _CONTAS = "Contas";
    private static final String _OUTROS = "Outros";

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                    + _ID + " integer primary key, "
                    + _SENHA + " text, "
                    + _INTRO + " text, "
                    + _NOME + " text, "
                    + _DOCS + " text, "
                    + _CARTAO + " text, "
                    + _CONTAS + " text, "
                    + _EMAILS + " text, "
                    + _OUTROS + " text "
                    + ");";

    public class Struc {
        public int Id = 1;
        public String Senha = "";
        public String Intro = "";
        public String Nome = "";
        public String Docs = "";
        public String Cartao = "";
        public String Emails = "";
        public String Contas = "";
        public String Outros = "";
    }

    public final Struc Fields = new Struc();

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBConfig(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public boolean emtpyTable() {
        return db.delete(DBConfig.DATABASE_TABLE, null, null) > 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL("insert into Cods (Id,Senha,Intro,Nome,Docs,Cartao,Emails,Contas,Outros) values (1,\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\");");

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
        args.put(_SENHA, Fields.Senha);
        args.put(_INTRO, Fields.Intro);
        args.put(_NOME, Fields.Nome);
        args.put(_DOCS, Fields.Docs);
        args.put(_CARTAO, Fields.Cartao);
        args.put(_EMAILS, Fields.Emails);
        args.put(_CONTAS, Fields.Contas);
        args.put(_OUTROS, Fields.Outros);
        return args;
    }

    public void getFields(Cursor c) {
        if (!c.isAfterLast() && !c.isBeforeFirst()) {
            int i = 0;
            Fields.Id = c.getInt(i++);
            Fields.Senha = c.getString(i++);
            Fields.Intro = c.getString(i++);
            Fields.Nome = c.getString(i++);
            Fields.Docs = c.getString(i++);
            Fields.Cartao = c.getString(i++);
            Fields.Emails = c.getString(i++);
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
                                _SENHA,
                                _INTRO,
                                _NOME,
                                _DOCS,
                                _CARTAO,
                                _EMAILS,
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
