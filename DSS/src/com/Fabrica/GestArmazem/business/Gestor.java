package com.Fabrica.GestArmazem.business;

import java.util.Objects;

public class Gestor {

    private String codGestor;
    private String mail;
    private String pass;
    private boolean logado;

    public Gestor() {
        this.codGestor = "";
        this.mail = "";
        this.pass = "";
        this.logado = false;
    }

    public Gestor(String codGestor, String mail, String pass, boolean logado) {
        this.codGestor = codGestor;
        this.mail = mail;
        this.pass = pass;
        this.logado = logado;
    }

    public String getCodGestor() {
        return codGestor;
    }

    public void setCodGestor(String codGestor) {
        this.codGestor = codGestor;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public boolean validaCredenciais(String cod, String password) {
        return this.codGestor.equals(cod) && this.pass.equals(password);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gestor gestor = (Gestor) o;
        return logado == gestor.logado &&
                Objects.equals(codGestor, gestor.codGestor) &&
                Objects.equals(mail, gestor.mail) &&
                Objects.equals(pass, gestor.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codGestor, mail, pass, logado);
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "codGestor='" + codGestor + '\'' +
                ", mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                ", logado=" + logado +
                '}';
    }


}