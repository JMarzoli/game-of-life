package it.unicam.cs.pa.jgol;

import java.util.function.Predicate;

/**
 * Definisce il controllore del GOL.
 */
public interface Controller<S, L> {

    Field<S, L> getField();

    boolean hasPrevious();

    boolean hasNext();

    Field<S, L> previous();

    Field<S, L> next();

    Field<S, L> apply();

    default void recordHandler(L loc, S status, Runnable activity) {
        recordHandler(f -> f.getStatus(loc).equals(status), activity);
    }

    void recordHandler(Predicate<Field<S, L>> p, Runnable activity);

}
