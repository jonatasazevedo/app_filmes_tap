package com.jonatas.app_filmes_tap.Model;

import android.graphics.Bitmap;

public class Filme{
    private String titulo,nota,sinopse;
    private int id,favorito;
    private Bitmap imagem;


    public Filme(int id,String titulo, String nota, String sinopse, Bitmap imagem,int favorito) {
        this.titulo = titulo;
        this.nota = nota;
        this.sinopse = sinopse;
        this.imagem = imagem;
        this.id = id;
        this.favorito = favorito;
    }

    public String getTitulo() {
        return titulo;
    }
    public Bitmap getImagem() {
        return imagem;
    }

    public String getNota() {
        return nota;
    }

    public String getSinopse() {
        return sinopse;
    }

    public int getId() {
        return id;
    }

    public int getFavorito() {
        return favorito;
    }
}
