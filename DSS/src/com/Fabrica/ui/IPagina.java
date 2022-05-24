package com.Fabrica.ui;

import com.Fabrica.GestArmazem.business.Localizacao;

import java.util.List;

public interface IPagina {
    public List<Object> getPagina();
    public void setPagina(List<Object> pagina);
    public void inserePaginas(Object nome);
    public int getpaginaNr();
    public int getNrPagina();
    public void avancaPagina();
    public void menuPaginaLeitura(paginas a);
    public void retrocedePagina();
    public boolean ePrimeiraPagina();
    public boolean eUltimaPagina();
    public void retrocedePaginas();
    public void avancaPaginas();
    public void escolhePagina(int i);
    public void paginaMenugeral ();
    public   void paginaInicial(paginas p);
    public void showOpcoesPaginas (paginas p);
    public int paginaApresentacao (paginas p);
}
