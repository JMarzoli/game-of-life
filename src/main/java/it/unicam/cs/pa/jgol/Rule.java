package it.unicam.cs.pa.jgol;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Definisce una regola per l'eveluzione delle celle.
 *
 * @param <S> tipo di dato per lo stato delle celle.
 * @param <L> tipo di dato per la locazione delle celle.
 */
@FunctionalInterface
public interface Rule<S, L> {


    static <L> Rule<DefaultConwayStatus,L> getConwayRules( ) {
        return  new RuleList<>( List.of( Rule::isolationRule, Rule::surviveRule ) );
    }

    /**
     * Metodo statico che implementa la regola standard di Conway: Se una cella viva &egrave; ha meno di due celle
     * vive intorno, muore.
     *
     *
     * @param current la cella a cui viene applicata la regola
     * @param near insieme delle celle intorno
     * @param <L> tipo di dato della locazione
     * @return stato della cella al passo successivo
     */
    static <L> Optional<DefaultConwayStatus> isolationRule(Cell<DefaultConwayStatus,L> current, Set<Cell<DefaultConwayStatus,L>> near) {
        if (current.getStatus() == DefaultConwayStatus.ALIVE) {
            if (near.stream().filter(c -> c.getStatus()==DefaultConwayStatus.ALIVE).count()<2) {
                return Optional.of(DefaultConwayStatus.DEATH);
            }
        }
        return Optional.empty();
    }

    /**
     * Metodo statico che implementa la regola standard di Conway: Se una cella viva &egrave; circondata da due o tre celle
     * vive intorno sopravvive.
     *
     *
     * @param current la cella a cui viene applicata la regola
     * @param near insieme delle celle intorno
     * @param <L> tipo di dato della locazione
     * @return stato della cella al passo successivo
     */
    static <L> Optional<DefaultConwayStatus> surviveRule(Cell<DefaultConwayStatus,L> current, Set<Cell<DefaultConwayStatus,L>> near) {
        if (current.getStatus() == DefaultConwayStatus.ALIVE) {
            long counter = near.stream().filter(c -> c.getStatus()==DefaultConwayStatus.ALIVE).count();
            if ((counter==2)||(counter==3)) {
                return Optional.of(DefaultConwayStatus.ALIVE);
            }
        }
        return Optional.empty();
    }


    /**
     * Calcola il novo stato della cella data la cella corrente e le celle ad essa
     * adiacenti. Se la regola non viene applicata, il valore None &egrave; restituito.
     *
     * @param current cella corrente.
     * @param near insieme delle celle adiacenti.
     * @return stato della cella al passo successivo, None se la regola non &egrave; stata applicata.
     */
    Optional<S> apply(Cell<S,L> current, Set<Cell<S,L>> near);

}
