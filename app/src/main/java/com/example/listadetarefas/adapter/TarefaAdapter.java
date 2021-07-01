 package com.example.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.myViewHolder> {

    private List<Tarefa> listaTarefas;
    public TarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;
    }

    public myViewHolder onCreateViewHolder(View view) {
        return null;
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_tarefa_adapter, parent, false);

        return new myViewHolder(itemLista);
    }



    @Override
    public void onBindViewHolder( myViewHolder holder, int position) {
        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());
    }

    @Override
    public int getItemCount() {

        return this.listaTarefas.size();
    }


    public static class myViewHolder extends  RecyclerView.ViewHolder{
        TextView tarefa;

        public myViewHolder(View itemView) {

            super(itemView);
            tarefa = itemView.findViewById(R.id.textTarefa);

        }
    }

}
