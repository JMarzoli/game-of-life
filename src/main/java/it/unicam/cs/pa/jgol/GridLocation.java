package it.unicam.cs.pa.jgol;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GridLocation {

    private final int row;
    private final int column;


    public GridLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    public Set<GridLocation> getAdjacentLocations(int width, int height) {
        return Stream.of(this.above(width,height),
                this.below(width,height),
                this.left(width,height),
                this.right(width,height)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }

    public Optional<GridLocation> above(int width, int height) {
        return adjacent(width,height,+1,0);
    }

    public Optional<GridLocation> below(int width, int height) {
        return adjacent(width,height,-1,0);
    }

    public Optional<GridLocation> left(int width, int height) {
        return adjacent(width,height,0,-1);
    }

    public Optional<GridLocation> right(int width, int height) {
        return adjacent(width,height,0,+1);
    }

    private Optional<GridLocation> adjacent(int width, int height, int dr, int dc) {
        int newR = row+dr;
        int newC = column+dc;
        if ((0<= newR)&&(newR<height)&&(0<=newC)&&(newC<width)) {
            return Optional.of(new GridLocation(newR,newC));
        } else {
            return Optional.empty();
        }
    }

}
