package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDao  implements ITarefaDao{

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public TarefaDao(Context context) {
        DbHelper db = new DbHelper(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try {
            escrever.insert(DbHelper.TABELA_TAREFAS, null , cv);
            Log.e("INFO", "TAREFA SALVA COM SUCESSO!!" );

        }catch(Exception e){
            Log.e("INFO", "ERRO AO SALVAR TAREFA " + e.getMessage() );
            return false;
        }
        return true;

    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        ContentValues cv = new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.update(DbHelper.TABELA_TAREFAS, cv,"id =?", args );
            Log.e("INFO", "TAREFA ATUALIZADA COM SUCESSO!!" );

        }catch(Exception e){
            Log.e("INFO", "ERRO AO ATUALIZAR TAREFA " + e.getMessage() );
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            escrever.delete(DbHelper.TABELA_TAREFAS,"id =?", args );
            Log.e("INFO", "TAREFA REMOVIDA COM SUCESSO!!" );

        }catch(Exception e){
            Log.e("INFO", "ERRO AO REMOVER TAREFA " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

            List<Tarefa> tarefas = new ArrayList<>();

            String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
            Cursor c = ler.rawQuery(sql, null);
            while( c.moveToNext() ){
                Tarefa tarefa = new Tarefa();

                Long id = c.getLong(c.getColumnIndex("id"));
                String nomeTarefa = c.getString(c.getColumnIndex("nome"));

                tarefa.setId(id);
                tarefa.setNomeTarefa(nomeTarefa);

                tarefas.add( tarefa );
            }
            return  tarefas;

    }
}
