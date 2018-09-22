package logic.token;

/**
 * a position. It may have negative values.
 * @author GeritKaleck
 */
public class Pos {

    /**
     * Constant determining the leftward rotation
     */
    public static final int LEFT_ROT = 0;

    /**
     * Constant determining the downward rotation
     */
    public static final int DOWN_ROT = 1;

    /**
     * Constant determining the rightward rotation
     */
    public static final int RIGHT_ROT = 2;

    /**
     * Constant determining the upward rotation
     */
    public static final int UP_ROT = 3;

    /**
     * x komponent of a position
     */
    private final int x;

    /**
     * y komponent of a position
     */
    private final int y;

    /**
     * Constructor for a position
     * @param x x komponent of the position
     * @param y y komponent of the position
     */
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x coordinate of the position.
     * @return x coordinate of the position
     */
    public int x() {
        return this.x;
    }

    /**
     * Getter for the y coordinate of the position.
     * @return y coordinate of the position
     */
    public int y() {
        return this.y;
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
        neighbours[LEFT_ROT] = new Pos(this.x-1, this.y  );
        neighbours[DOWN_ROT] = new Pos(this.x  , this.y-1);
        neighbours[RIGHT_ROT] = new Pos(this.x+1, this.y)  ;
        neighbours[UP_ROT] = new Pos(this.x  , this.y+1);
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
