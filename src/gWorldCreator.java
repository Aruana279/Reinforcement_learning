import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class gWorldCreator {
    public static gWorld createBoard(String fileName){
        try {
            // Reading input values from txt
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String file = bufferedReader.lines().reduce((allLines,lines) -> allLines + "\n" + lines).get();
            String[] rows = file.split("\n");

            // Getting board height and width
            int boardHeight = rows.length;
            int boardWidth = rows[0].split("\t").length;

            // Instantiating HashMap for QLearn
            HashMap<Coord,Double> map = new HashMap<>();

            // Instantiating 2D Array for maze
            double[][] maze = new double[boardHeight][boardWidth];
            // Iterating trough all input values
            for (int i = 0; i < rows.length; i ++){
                String[] elements = rows[i].split("\t");
                for (int k = 0; k < elements.length; k ++){
                    if (elements[k].equals("NA")){
                        // TODO: Might need to be handled
                        continue;
                    } else {
                        double current = Double.parseDouble(elements[k]);
                        map.put(new Coord(i, k), current);
                        maze[i][k] = current;
                    }
                }
            }
            return new gWorld(boardHeight,boardWidth,map, maze);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
