package com.jonatas.app_filmes_tap.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Filme implements Serializable {
    private String titulo,nota,sinopse;
    private Bitmap imagem;

    public Filme(){}

    public Filme(String titulo, String nota, String sinopse, Bitmap imagem) {
        this.titulo = titulo;
        this.nota = nota;
        this.sinopse = sinopse;
        this.imagem = imagem;
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
}
