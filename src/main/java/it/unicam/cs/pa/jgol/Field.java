package it.unicam.cs.pa.jgol;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Definisce il campo dove vengono posizionate le celle.
 *
 * @param <S> Tipo per lo stato delle celle
 * @param <L> Tipo per la posizione delle celle
 */
public interface Field<S, L> {

    /**
     * Restituisce la cella in una data posizione.
     *
     * @param loc posizione nel campo.
     * @return la cella nella posizione richiesta.
     */
    Cell<S,L> getCellAt(L loc);

    /**
     * Restituisce l'insieme delle cella vicino alla posizione data.
     *
     * @param loc posizione nel campo.
     * @return l'insieme delle cella vicino alla posizione data.
     */
    Set<Cell<S,L>> getAdjacentCells(L loc);

    /**
     * Restituisce lo stato della cella nella posizione richiesta.
     * @param loc posizione nel campo.
     * @return lo stato della cella nella posizione richiesta.
     */
    default S getStatus(L loc) {
        return getCellAt(loc).getStatus();
    }

    /**
     * Restituisce l'insieme delle locazioni adiacenti a quella data.
     *
     * @param loc posizione nel campo.
     * @return l'insieme delle locazioni adiacenti a quella data.
     */
    Set<L> getAdjacentLocations(L loc);

    /**
     * Applica una regola al campo per ottenere un campo aggiuntivo.
     *
     * @param rule regola da applicare.
     * @return campo risultante dall'applicazione della regola.
     */
    Field<S,L> apply(Rule<S,L> rule);

    /**
     * Restituisce il numero di celle che soddisfano un certo predicato.
     *
     * @param p predicato per selezionare le celle.
     * @return il numero di celle che soddisfano un certo predicato.
     */
    int count(Predicate<? super Cell<S,L>> p);

    /**
     * Restituisce il numero di celle nello stato dato.
     *
     * @param status stato della cella.
     * @return numero di celle nello stato dato.
     */
    default int count(S status) {
        if (status == null) {
            throw new NullPointerException();
        }
        return count(c -> status.equals(c.getStatus()));
    }
}
