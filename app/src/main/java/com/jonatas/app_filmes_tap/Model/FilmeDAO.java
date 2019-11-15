package com.jonatas.app_filmes_tap.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.jonatas.app_filmes_tap.Conexao.Resultados.FilmeResposta;
import com.jonatas.app_filmes_tap.DB.DBHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {
    private DBHelper conexao;
    private SQLiteDatabase banco;
    private String TABLE_DADOS="filme";

    public FilmeDAO(Context ctx){
        conexao = new DBHelper(ctx);
        banco = conexao.getWritableDatabase();
    }

    public boolean bancoIsEmpty(){
        Cursor cursor = banco.rawQuery("select count(*) from filme",null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        return count==0;
    }

    public List<Filme> retornarTodos(){
        List<Filme> lista_filmes = new ArrayList<>();
        Cursor cursor = banco.query(TABLE_DADOS,new String[]{"TITULO","IMAGEM","NOTA","SINOPSE"},null,null,null,null,null);
        while(cursor.moveToNext()){
            String titulo = cursor.getString(0);
            Bitmap imagem = converterImagemDoBanco(cursor.getBlob(1));
            String nota = cursor.getString(2);
            String sinopse = cursor.getString(3);
            lista_filmes.add(new Filme(titulo,nota,sinopse,imagem));
        }
        cursor.close();
        return lista_filmes;
    }

    public void salvar(List<FilmeResposta> lista){
        for(FilmeResposta filmeResposta: lista){
            ContentValues cv = new ContentValues();
            cv.put("TITULO",filmeResposta.getTitulo());
            cv.put("NOTA",filmeResposta.getNota());
            cv.put("SINOPSE",filmeResposta.getSinopse());
            cv.put("IMAGEM",converterImagemParaBanco("https://image.tmdb.org/t/p/w500"+filmeResposta.getUrl()));
            banco.insert(TABLE_DADOS,null,cv);
        }
    }

    private Bitmap converterImagemDoBanco(byte[] imagemByte){
        ByteArrayInputStream imagemstream = new ByteArrayInputStream(imagemByte);
        Bitmap imagemBitmap = BitmapFactory.decodeStream(imagemstream);
        //Usa-se stream para converter o array de bites que representa uma imagem JPEG em um bitmap para colocar no imageview
        return imagemBitmap;
    }
    private byte[] converterImagemParaBanco(String url){
        Bitmap imagem = DownloadImage(url);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG,100,stream);
        //Usa-se stream para converter o Bitmap em um array de bytes que representa uma imagem JPEG para salvar no banco
        return stream.toByteArray();
    }
    private Bitmap DownloadImage(String pURL){

        StrictMode.ThreadPolicy vPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(vPolicy);
        //StrictMode é usado para detectar violações de execução de tarefas na thread principal
        InputStream inStream = null;
        Bitmap vBitmap = null;
        try{
            URL url = new URL(pURL);
            HttpURLConnection pConnection = (HttpURLConnection)url.openConnection();
            pConnection.setDoInput(true);
            pConnection.connect();
            //Conexão feita com a URL recebida
            if(pConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                //Caso tudo dê certo com a conexão
                //haverá uma conversão da imagem para o Bitmap
                inStream = pConnection.getInputStream();
                vBitmap = BitmapFactory.decodeStream(inStream);
                inStream.close();
                return vBitmap;
            }
        }
        catch(Exception ex){
            Log.e("Exception",ex.toString());
        }
        return null;
    }
}
