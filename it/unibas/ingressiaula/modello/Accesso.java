package it.unibas.ingressiaula.modello;

import java.util.Calendar;
import java.util.Comparator;

public class Accesso {

    private String matricola;
    private String nomeStudente;
    private int permanenzaMinuti;
    private String motivazione;
    private Calendar data;

    public Accesso(String matricola, String nomeStudente, int permanenzaMinuti, String motivazione, Calendar data) {
        this.matricola = matricola;
        this.nomeStudente = nomeStudente;
        this.permanenzaMinuti = permanenzaMinuti;
        this.motivazione = motivazione;
        this.data = data;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getNomeStudente() {
        return nomeStudente;
    }

    public int getPermanenzaMinuti() {
        return permanenzaMinuti;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public Calendar getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matricola: ").append(matricola).append("\n");
        sb.append("Nome studente: ").append(nomeStudente).append("\n");
        sb.append("Permanenza: ").append(permanenzaMinuti).append(" minuti\n");
        sb.append("Motivazione: ").append(motivazione).append("\n");
        sb.append("Data: ").append(data).append("\n");
        return sb.toString();
    }

}
