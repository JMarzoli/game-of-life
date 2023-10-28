package it.unicam.cs.pa.jgol;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rappresenta un campo in cui le celle sono collocate su di una griglia.
 *
 * @param <S> tipo dello stato delle celle.
 */
public class GridField<S> implements Field<S,GridLocation> {

    private final Cell<S,GridLocation>[][] grid;
    private final int width;
    private final int height;

    /**
     * Crea un campo con le dimensioni date le cui celle sono inizializzate attraverso la funzione passata come parametro.
     * @param width larghezza del campo.
     * @param height altezza del campo.
     * @param init funzione di inizializzazione.
     */
    public GridField(int width, int height, BiFunction<Integer,Integer,S> init) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        for( int r=0 ; r<height ; r++ ) {
            for( int c=0 ; c<width ; c++ ) {
                this.grid[r][c] = new SimpleCell(init.apply(r,c),new GridLocation(r,c),this);
            }
        }
    }

    /**
     * Crea un campo delle dimensioni date con tutte le celle inizializzate al valore dato.
     *
     * @param width larghezza del campo.
     * @param height altezza del campo.
     * @param initialState stato iniziale delle celle.
     */
    public GridField(int width, int height, S initialState) {
        this(width, height, (r,c) -> initialState );
    }

    @Override
    public Cell<S, GridLocation> getCellAt(GridLocation loc) {
        if (!isValid(loc)) {

        }
        //TODO: Gestire le eccezioni!
        return grid[loc.getRow()][loc.getColumn()];
    }

    /**
     * Verifica se la locazione data &egrave; valida per il campo.
     *
     * @param loc locazione da verificare.
     * @return true se la locazione &grave; valida per il campo, false altrimenti.
     */
    public boolean isValid(GridLocation loc) {
        return isValid(loc.getRow(),loc.getColumn());
    }

    /**
     * Verifica se le righe colonne date sono valide per il campo.
     *
     * @param
     * @return true se la locazione &grave; valida per il campo, false altrimenti.
     */
    public boolean isValid(int row, int column) {
        return (0<=column)&&(column<width)&&(0<=row)&&(row<height);
    }

    @Override
    public Set<Cell<S, GridLocation>> getAdjacentCells(GridLocation loc) {
        return getCells(loc.getAdjacentLocations(width,height));
    }

    private Set<Cell<S, GridLocation>> getCells(Set<GridLocation> adjacentLocations) {
        return adjacentLocations.stream().map(this::getCellAt).collect(Collectors.toSet());
    }

    @Override
    public Set<GridLocation> getAdjacentLocations(GridLocation loc) {
        return loc.getAdjacentLocations(width,height);
    }

    private S next(GridLocation loc, Rule<S, GridLocation> rule) {
        return rule.apply(getCellAt(loc),getAdjacentCells(loc)).orElse(getStatus(loc));
    }


    private S getStatus(int r, int c) {
        return grid[r][c].getStatus();
    }

    private Cell<S, GridLocation> getCellAt(int r, int c) {
        return grid[r][c];
    }
    

    @Override
    public Field<S, GridLocation> apply(Rule<S, GridLocation> rule) {
        return new GridField<>(width,height,(r,c) -> this.next(new GridLocation(r,c),rule) );
    }

    @Override
    public int count(Predicate<? super Cell<S, GridLocation>> p) {
        return Stream.of(grid).map(Stream::of).map(s -> s.filter(p)).map(s -> s.count()).reduce(0L,(x, y) -> x+y).intValue();
    }
}
