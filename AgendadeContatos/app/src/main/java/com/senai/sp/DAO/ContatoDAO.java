package com.senai.sp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.senai.sp.modal.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends SQLiteOpenHelper {
    public ContatoDAO(Context context) {
        super(context, "db_contato", null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE TABLE tbl_contato (" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "endereco TEXT NOT NULL," +
                "telefone TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "linkedin TEXT NOT NULL," +
                "foto BLOB)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE tbl_contato;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void salvar(Contato contato){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(contato);
        db.insert("tbl_contato", null, dados);

    }

    private ContentValues getContentValues(Contato contato) {

        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("endereco", contato.getEndereco());
        dados.put("telefone", contato.getTelefone());
        dados.put("email", contato.getEmail());
        dados.put("linkedin", contato.getEndereco());
        dados.put("foto", contato.getFoto());

        return dados;
    }

    public List<Contato> getContatos() {
        List<Contato> contatos = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tbl_contato";

        Cursor c = db.rawQuery(sql, null);

        while(c.moveToNext()){
            Contato contato = new Contato();

            contato.setId(c.getInt(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contato.setLinkedin(c.getString(c.getColumnIndex("linkedin")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
            contato.setEndereco(c.getString(c.getColumnIndex("endereco")));
            contato.setFoto(c.getBlob(c.getColumnIndex("foto")));


            contatos.add(contato);

        }

        return contatos;
    }

    public void atualizar(Contato contato) {
        SQLiteDatabase db = getReadableDatabase();
        String[] parametros= {String.valueOf(contato.getId())};
        ContentValues dados= getContentValues(contato);
        db.update("tbl_contato",dados, "id = ?", parametros);

    }

    public void excluir(Contato contato) {

        SQLiteDatabase db = getReadableDatabase();
        String[] argumentos = {String.valueOf(contato.getId())};

    db.delete("tbl_contato", "id = ?", argumentos);
    }
}
