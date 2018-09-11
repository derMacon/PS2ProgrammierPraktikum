package logic.Token;

/**
 * a position. It may have negative values.
 * @author GeritKaleck
 */
public class Pos {
    private final int x;
    private final int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    /**
     * checks if this position is next to given position.
     * @param p position to be near to
     * @return if this position is next to given position
     */
    public boolean isNextTo(Pos p) {
        int xDiff = Math.abs(x - p.x());
        int yDiff = Math.abs(y - p.y());
        return (xDiff == 1 && yDiff == 0 ||
                xDiff == 0 && yDiff == 1);
    }

    /**
     * gets the four neighboured positions even if they are not on board.
     * @return the four neighboured positions
     */
    public Pos[] getNeighbours() {
        Pos[] neighbours = new Pos[4];
        neighbours[0] = new Pos(this.x-1, this.y  );
        neighbours[1] = new Pos(this.x  , this.y-1);
        neighbours[2] = new Pos(this.x+1, this.y)  ;
        neighbours[3] = new Pos(this.x  , this.y+1);
        return neighbours;
    }

    /**
     * two positions are equal if the x-values and y-values are equal
     * @param obj
     * @return true, if the x-values and y-values are equal
     */
    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj.getClass() == this.getClass() &&
                ((Pos)obj).x == this.x &&
                ((Pos)obj).y == this.y;
    }

    /**
     * x/y
     * @return x/y
     */
    @Override
    public String toString() {
        return x + "/" + y;
    }
}
