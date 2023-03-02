package it.unibas.ingressiaula.modello;

import java.util.ArrayList;
import java.util.List;

public class Aula implements Comparable<Aula> {
    
    private String codice;
    private String nome;
    private int piano;
    private List<Accesso> listaAccessi = new ArrayList<>();

    public Aula(String codice, String nome, int piano) {
        this.codice = codice;
        this.nome = nome;
        this.piano = piano;
    }

    public String getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public int getPiano() {
        return piano;
    }

    public List<Accesso> getListaAccessi() {
        return listaAccessi;
    }

    public boolean aggiungiAccesso(Accesso accesso) {
        return listaAccessi.add(accesso);
    }

    public String prova(){
        return "Prova git";
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codice: ").append(codice).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Piano ").append(piano).append("\n");
        sb.append("Lista accessi: ").append(listaAccessi).append("\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Aula o) {
        return this.getNome().compareTo(o.getNome());
    }
    
    
    
}
