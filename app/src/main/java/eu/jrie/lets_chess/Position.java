package eu.jrie.chess_time;

public class Position{
    public int x;
    public int y;

    public Position(int _x, int _y){
        x = _x;
        y = _y;
    }

    public static boolean areEqual(Position a, Position b){
        return a.x == b.x && a.y == b.y;
    }
}
