public class gWorld {
    private int height;
    private int width;
    private double[][] grid;

    public gWorld(int height, int width, double[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public double[][] getGrid() {
        return grid;
    }
}
