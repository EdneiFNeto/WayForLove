package com.example.wayforlove.modelo;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Usuario implements Serializable {

    public Usuario() {
    }

    public Usuario(String nome, String sexo, String tipoFisico, String cor, LatLng pocisao) {
        this.nome = nome;
        this.sexo = sexo;
        this.tipoFisico = tipoFisico;
        this.cor = cor;
        this.pocisao = pocisao;
    }

    private String nome;
    private String sexo;
    private String tipoFisico;
    private String cor;
    private LatLng pocisao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoFisico() {
        return tipoFisico;
    }

    public void setTipoFisico(String tipoFisico) {
        this.tipoFisico = tipoFisico;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public LatLng getPocisao() {
        return pocisao;
    }

    public void setPocisao(LatLng pocisao) {
        this.pocisao = pocisao;
    }


    @Override
    public String toString() {
        return this.getNome()+" "+this.getSexo()+"\n"+this.getCor()+"\n"+this.getTipoFisico();
    }
}
