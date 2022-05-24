package com.Fabrica.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class paginas implements IPagina {

    private List<Object> pagina;
    private  int i;
    private int j;

    public paginas(){
        this.pagina=new ArrayList<>();
        this.i=0;
        this.j=0;
    }
    public paginas(List<Object> pagina, int i, int j) {
        this.pagina = pagina;
        this.i = i;
        this.j = j;
    }

    public List<Object> getPagina() {
        return pagina;
    }

    public void setPagina(List<Object> pagina) {
        this.pagina = pagina;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void inserePaginas(Object nome) {
        this.pagina.add(nome);
    }
    public int getpaginaNr() {
        if (this.j % 5 != 0) return ((this.j) / 5) + 1;
        return (this.j) / 5;
    }

    public int getNrPagina() {
        if (this.pagina.size() % 5 == 0) return this.pagina.size() / 5;
        else return (this.pagina.size() / 5) + 1;
    }

    public void avancaPagina() {

        this.i += 5;
        this.j += 5;
    }

    public void retrocedePagina() {

        this.i -= 5;
        this.j -= 5;
    }


    public boolean ePrimeiraPagina() {
        return this.i == 0;
    }

    public boolean eUltimaPagina() {
        return this.j == this.pagina.size();
    }


    public void retrocedePaginas() {
        if (!this.ePrimeiraPagina())
            retrocedePagina();
    }

    public void avancaPaginas() {
        if (!this.eUltimaPagina())
            avancaPagina();
    }


    public void escolhePagina(int i) {
        this.i = 5 * (i - 1);
        this.j = 5 * i;
        if (this.j > this.pagina.size()) {
            this.j = this.pagina.size();
            this.i = this.pagina.size() - 5;
        }
        if (this.i < 0)
            this.i = 0;
    }
    public void paginaInicial(paginas p) {
        int k;
        for (k = p.getI(); k < p.getJ(); k++)
            System.out.println("              "+p.getPagina().get(k));
    }
    public void showOpcoesPaginas (paginas p){
        System.out.println("\n            Página: " + p.getpaginaNr() + " de " + p.getNrPagina());

    }

    public void menuEscolhePagina (paginas p) {
        System.out.print("\n              Escolha uma página de "+ p.getNrPagina() + " páginas :");

    }
    public void paginaMenugeral () {
        System.out.println("\n              _________NAVEGADOR_______");
        System.out.println("              | |1| Página Inicial ");
        System.out.println("              | |2| Retroceder uma Página ");
        System.out.println("              | |3| Avançar uma Página ");
        System.out.println("              | |4| Página Final ");
        System.out.println("              | |5| Página à Escolha");
        System.out.print  ("              | |0| Sair     Opção: ");
    }

    public int paginaApresentacao (paginas p) {
        p.paginaMenugeral();
        Scanner leitor = new Scanner(System.in);
        String str = leitor.next() ;
        System.out.print("\n");
        int run=1;
        switch (str.toUpperCase()) {
            case "1" -> {
                p.escolhePagina(1);
                p.paginaInicial(p);
                p.showOpcoesPaginas(p);
            }
            case "2" -> {
                p.escolhePagina(p.getpaginaNr() - 1);
                p.paginaInicial(p);
                p.showOpcoesPaginas(p);
            }
            case "3" -> {
                p.escolhePagina(p.getpaginaNr() + 1);
                p.paginaInicial(p);
                p.showOpcoesPaginas(p);
            }
            case "4" -> {
                p.escolhePagina(p.getNrPagina());
                p.paginaInicial(p);
                p.showOpcoesPaginas(p);
            }
            case "5" -> {
                p.menuEscolhePagina(p);
                int pagina = leitor.nextInt();
                p.escolhePagina(pagina);
                p.paginaInicial(p);
                p.showOpcoesPaginas(p);
            }
            case "0" -> run = 0;
            default -> System.out.println("ATENÇÃO: Opção inválida!");
        }
        return run;
    }
    public void menuPaginaLeitura(paginas a){
        int corre=1;
        while(corre!=0){
            corre=a.paginaApresentacao(a);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        paginas paginas = (paginas) o;
        return i == paginas.i &&
                j == paginas.j &&
                Objects.equals(pagina, paginas.pagina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pagina, i, j);
    }

    @Override
    public String toString() {
        return "paginas{" +
                "pagina=" + pagina +
                ", i=" + i +
                ", j=" + j +
                '}';
    }

}
