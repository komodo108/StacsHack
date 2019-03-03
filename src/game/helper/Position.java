package game.helper;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position) {
            Position p2 = (Position) obj;
            return (p2.getX() == this.getX() && p2.getY() == getY());
        } else return false;
    }

    @Override
    public int hashCode() {
        return String.valueOf(this.getX() + this.getY()).hashCode();
    }

    @Override
    public String toString() {
        return "{ x:" + this.x + ", y:" + this.y + " }";
    }
}
