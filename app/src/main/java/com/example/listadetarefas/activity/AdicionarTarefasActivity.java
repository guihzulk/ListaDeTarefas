package com.example.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.helper.TarefaDao;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefasActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefas);
        editTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa ) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configura text
        if (tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId()){
            case R.id.itemSalvar:
                if ( tarefaAtual != null) { //edição

                    TarefaDao tarefaDao = new TarefaDao(getApplicationContext());
                    String nomeTarefa = editTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar
                        if( tarefaDao.atualizar(tarefa)){
                            if (tarefaDao.atualizar(tarefa)){
                                finish();
                                Toast.makeText(getApplicationContext(),
                                        "Sucesso ao atualizar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao atualizar tarefa!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

            }else {
                    //Salva dados
                    TarefaDao tarefaDao = new TarefaDao(getApplicationContext());
                    String nomeTarefa = editTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        if (tarefaDao.salvar(tarefa)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao salvar tarefa!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                //Toast.makeText(AdicionarTarefasActivity.this, "Salvar Item", Toast.LENGTH_SHORT).show();
            break;
        }
       return  super.onOptionsItemSelected(item);
    }
}