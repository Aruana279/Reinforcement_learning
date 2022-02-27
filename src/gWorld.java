import java.util.HashMap;

public class gWorld {
    private int height;
    private int width;
    private HashMap<Coord,Double> map;
    private double[][] gWorld;

    public gWorld(int height, int width, HashMap<Coord,Double> map, double[][] gWorld) {
        this.height = height;
        this.width = width;
        this.map = map;
        this.gWorld = gWorld;
        //System.out.println(Arrays.deepToString(maze));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double[][] getGWorld() {
        return gWorld;
    }
}
