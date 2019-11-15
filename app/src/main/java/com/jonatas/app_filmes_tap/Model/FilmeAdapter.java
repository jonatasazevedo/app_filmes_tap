package com.jonatas.app_filmes_tap.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jonatas.app_filmes_tap.R;
import com.jonatas.app_filmes_tap.UI.Tela_detalhes_filmes;

import java.io.ByteArrayOutputStream;
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
        holder.img_filme.setImageBitmap(dados.get(position).getImagem());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, Tela_detalhes_filmes.class);
                FilmeTransition filmeTransition = new FilmeTransition();
                filmeTransition.setNota(dados.get(position).getNota());
                filmeTransition.setSinopse(dados.get(position).getSinopse());
                filmeTransition.setTitulo(dados.get(position).getTitulo());
                filmeTransition.setId(dados.get(position).getId());
                filmeTransition.setFavorito(dados.get(position).getFavorito());
                //classe para passar strings e inteiros
                intent.putExtra("filme",filmeTransition);
                intent.putExtra("imagem",converterBitmapParaArrayBytes(dados.get(position).getImagem()));
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

    private byte[] converterBitmapParaArrayBytes(Bitmap imagem){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG,100,stream);
        //Usa-se stream para converter o Bitmap em um array de bytes que representa uma imagem JPEG
        return stream.toByteArray();
    }
}
