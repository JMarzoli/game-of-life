package it.unicam.cs.pa.jgol;

/**
 * Interfaccia che definisce una generica cella.
 *
 * @param <S> tipo per lo stato della cella
 * @param <L> locazione della cella
 */
public interface Cell<S,L> {

    /**
     * Restituisce lo stato della cella.
     *
     * @return lo stato della cella.
     */
    S getStatus();

    /**
     * Restituisce la posizione della cella.
     *
     * @return la posizione della cella.
     */
    L getLocation();

    /**
     * Restituice il campo che contiene la cella.
     *
     * @return il campo che contiene la cella.
     */
    Field<S,L> getField();

}
