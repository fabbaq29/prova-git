package it.unibas.ingressiaula.modello;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Archivio {
    
    private Logger logger = LoggerFactory.getLogger(Archivio.class);

    private List<Aula> listaAule = new ArrayList<>();
   

    public List<Aula> getListaAule() {
        return listaAule;
    }

    public boolean aggiungiAula(Aula aula) {
        return listaAule.add(aula);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista aule: ").append(listaAule);
        return sb.toString();
    }

    public List<Aula> cercaAulePiano(int piano) {
        List<Aula> listaFiltrata = new ArrayList<>();
        for (Aula aula : listaAule) {
            if (aula.getPiano() <= piano) {
                listaFiltrata.add(aula);
            }
        }
        Collections.sort(listaFiltrata);
        return listaFiltrata;
    }

    public boolean verificaArchivio() {
        List<Accesso> accessiDomenica = accessiDomenica();
        if (accessiDomenica.isEmpty()) {
            return false;
        }
        for (Accesso accesso : accessiDomenica) {
            if (contaOccorrenze(accesso, accessiDomenica) > 1) {
                return true;
            }
        }
        return false;
    }

    public List<Accesso> accessiDomenica() {
        List<Accesso> listaAccessiDomenica = new ArrayList<>();
        for (Aula aula : listaAule) {
            for (Accesso accesso : aula.getListaAccessi()) {
                Calendar data = accesso.getData();
                int giornoDellaSettimana = data.get(Calendar.DAY_OF_WEEK);
                if (giornoDellaSettimana == Calendar.SUNDAY) {
                    listaAccessiDomenica.add(accesso);
                }
            }
        }

        return listaAccessiDomenica;
    }

    private int contaOccorrenze(Accesso accesso, List<Accesso> accessiDomenica) {
        int conta = 0;
        DateFormat df = DateFormat.getDateInstance(); //trucco per escludere ore e minuti
        String giornoAccesso = df.format(accesso.getData().getTime());
        for (Accesso accessoDaControllare : accessiDomenica) {
            String giornoAccessoDaControllare = df.format(accessoDaControllare.getData().getTime());
            if (accesso.getMatricola().equals(accessoDaControllare.getMatricola())
                    && giornoAccesso.equals(giornoAccessoDaControllare)) {
                conta++;
            }
        }

        return conta;
    }

    public List<DatiAccessiMese> calcolaDatiMeseFrequente() {
        Map<Integer, List<Accesso>> mappaAccessiMese = raggruppaAccessiPerMese();
        logger.debug("Mappa accessi mese {}",mappaAccessiMese);
        List<DatiAccessiMese> risultato = new ArrayList<DatiAccessiMese>();
        for (Integer mese : mappaAccessiMese.keySet()) {
            List<Accesso> accessiMese = mappaAccessiMese.get(mese);
            int tempoTotale = calcolaTempoTotale(accessiMese);
            String motivazioneFrequente = calcolaMotivazioneFrequente(accessiMese);
            DatiAccessiMese datiAccessiMese = new DatiAccessiMese(mese, motivazioneFrequente, tempoTotale);
            risultato.add(datiAccessiMese);
        }
        Collections.sort(risultato);
        return risultato;
    }

    private Map<Integer, List<Accesso>> raggruppaAccessiPerMese() {
        Map<Integer, List<Accesso>> mappaAccessiPerMese = new HashMap<Integer, List<Accesso>>();
        for (Aula aula : listaAule) {
            for (Accesso accesso : aula.getListaAccessi()) {
                Calendar data = accesso.getData();
                int mese = data.get(Calendar.MONTH);
                List<Accesso> accessiMese = mappaAccessiPerMese.get(mese);
                if (accessiMese == null) {
                    accessiMese = new ArrayList<Accesso>();
                    mappaAccessiPerMese.put(mese, accessiMese);
                }
                accessiMese.add(accesso);
            }
        }

        return mappaAccessiPerMese;
    }

    private int calcolaTempoTotale(List<Accesso> accessiMese) {
        int somma = 0;
        for (Accesso accesso : accessiMese) {
            somma += accesso.getPermanenzaMinuti();
        }
        return somma;
    }

    private String calcolaMotivazioneFrequente(List<Accesso> accessiMese) {
        Map<String,Integer> mappaOccorrenzeTipologia = calcolaMappaOccorrenzeTipologia(accessiMese);
        String motivazioneMax = null;
        for (String motivazione : mappaOccorrenzeTipologia.keySet()) {
            if(motivazioneMax == null || mappaOccorrenzeTipologia.get(motivazione) > mappaOccorrenzeTipologia.get(motivazioneMax)){
                motivazioneMax = motivazione;
            }
        }
        return motivazioneMax;
    }

    private Map<String, Integer> calcolaMappaOccorrenzeTipologia(List<Accesso> accessiMese) {
        Map<String, Integer> mappaOccorrenzeTipologia = new HashMap<String,Integer>();
        for (Accesso accesso : accessiMese) {
            Integer vecchieOccorrenze = mappaOccorrenzeTipologia.get(accesso.getMotivazione());
            if(vecchieOccorrenze == null){
                mappaOccorrenzeTipologia.put(accesso.getMotivazione(),1);
            } else {
                mappaOccorrenzeTipologia.put(accesso.getMotivazione(), vecchieOccorrenze + 1);
            }
        }
        return mappaOccorrenzeTipologia;
    }
    
    

}
