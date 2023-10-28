package it.unicam.cs.pa.jgol;

/**
 * Classe di default che implementa l'interfaccia Cell.
 */
public class SimpleCell<S,L> implements Cell<S,L> {

    //TODO: Completare l'implementazione di questa classe.

    private final S status;
    private final L location;
    private final Field<S, L> field;

    public SimpleCell(S status, L location, Field<S, L> field) {
        this.status = status;
        this.location = location;
        this.field = field;
    }

    @Override
    public S getStatus() {
        return status;
    }

    @Override
    public L getLocation() {
        return location;
    }

    @Override
    public Field<S, L> getField() {
        return field;
    }
}
