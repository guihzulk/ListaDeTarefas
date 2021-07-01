package com.example.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listadetarefas.R;
import com.example.listadetarefas.activity.AdicionarTarefasActivity;
import com.example.listadetarefas.adapter.TarefaAdapter;
import com.example.listadetarefas.helper.DbHelper;
import com.example.listadetarefas.helper.ITarefaDao;
import com.example.listadetarefas.helper.RecyclerItemClickListener;
import com.example.listadetarefas.helper.TarefaDao;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        //Configura Recycler
        recyclerView = findViewById(R.id.recyclerTarefas);

        DbHelper db = new DbHelper(getApplicationContext());

        ContentValues cv = new ContentValues();
        //cv.put("nome", "teste");

        db.getWritableDatabase().insert("tarefas", null , cv);


        //Add Evento
        recyclerView.addOnItemTouchListener(
         new RecyclerItemClickListener(
                 getApplicationContext(),
                 recyclerView,
                 new RecyclerItemClickListener.OnItemClickListener() {
                     @Override
                     public void onItemClick(View view, int position) {
                       //  Log.i("clique","onItemClick");
                         //Recuperando Tarefa para edição
                         Tarefa tarefaSelecionada = listaTarefas.get(position);

                         //Envia tarefa para tela adicionar
                         Intent intent = new Intent(MainActivity.this, AdicionarTarefasActivity.class);
                         intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                         startActivity(intent);
                     }

                     @Override
                     public void onLongItemClick(View view, int position) {
                         //Recuperando tarefa
                         tarefaSelecionada = listaTarefas.get(position);

                         AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                         //COnfigurando titulo e mensagem
                         dialog.setTitle("Confirmar Exclusão de tarefa");
                         dialog.setMessage("Deseja Excluir a tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                         dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                                 TarefaDao tarefaDao = new TarefaDao(getApplicationContext());
                                 if(tarefaDao.deletar(tarefaSelecionada)){
                                     CarregarListaTarefas();
                                     Toast.makeText(getApplicationContext(),
                                             "Sucesso ao deletar tarefa!",
                                             Toast.LENGTH_SHORT).show();
                                 }else {
                                     Toast.makeText(getApplicationContext(),
                                             "Erro ao deletar tarefa!",
                                             Toast.LENGTH_SHORT).show();
                                 }

                             }
                         });

                         dialog.setNegativeButton("Não", null);
                         //Exibir dialog
                         dialog.create();
                         dialog.show();
                         //Log.i("clique","onLongItemClick");
                     }

                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                     }
                 }
         )
        );

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
       // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefasActivity.class);
                startActivity(intent);
            }
        });
    }

    public void CarregarListaTarefas(){
          //Listar Tarefas


        TarefaDao tarefaDao = new TarefaDao(getApplicationContext());
        listaTarefas = tarefaDao.listar();

        tarefaAdapter  = new TarefaAdapter( listaTarefas);
        // Exibe lista de tarefas

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);

    }

    @Override
    protected void onStart() {
        CarregarListaTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    //    if (id == R.id.action_settings) {
    //        return true;
    //    }

        return super.onOptionsItemSelected(item);
    }

  //  @Override
  //  public boolean onSupportNavigateUp() {
  //      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
   //     return NavigationUI.navigateUp(navController, appBarConfiguration)
   //             || super.onSupportNavigateUp();
  //  }
}