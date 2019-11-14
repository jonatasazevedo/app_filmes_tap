package com.jonatas.app_filmes_tap.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jonatas.app_filmes_tap.R;
import com.jonatas.app_filmes_tap.UI.Tela_detalhes_filmes;

import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.MyViewHolder>{
    private Context contexto;
    private List<Filme> dados;

    public FilmeAdapter(Context contexto) {
        this.contexto = contexto;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contexto).inflate(R.layout.cardview_filme,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_titulo_filme.setText(dados.get(position).getTitulo());
        Glide.with(contexto).load("https://image.tmdb.org/t/p/w500"+dados.get(position).getUrl()).centerCrop().into(holder.img_filme);
        //o uso do Glide é apenas para configurar a imagem na View pela URL
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, Tela_detalhes_filmes.class);
                intent.putExtra("filme",dados.get(position));
                contexto.startActivity(intent);
                //conteudo do filme passado pelo Bundle em uma Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dados!=null) return dados.size();
        else return 0;
    }

    public void setFilmes(List<Filme> dados){
        this.dados = dados;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_titulo_filme;
        ImageView img_filme;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_titulo_filme = itemView.findViewById(R.id.titulo_filme);
            img_filme = itemView.findViewById(R.id.imagem_filme);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
