import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class gridCreate {
    public static gWorld createGrid(String myFile){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(myFile));
            //while ((int inside= bufferReader))
            String file = bufferedReader.lines().reduce((allLines,lines) -> allLines + "\n" + lines).get();
            String[] rows = file.split("\n");
            int height = rows.length;
            int width = rows[0].split("\t").length;
            double[][] grid = new double[height][width];
            for (int i = 0; i < rows.length; i ++){
                String[] numbers = rows[i].split("\t");
                for (int j = 0; j < numbers.length; j ++){
                    if (numbers[j].equals("NA")){
                        continue;
                    } else {
                        double current = Double.parseDouble(numbers[j]);
                        grid[i][j] = current;
                    }
                }
            }
            return new gWorld(height,width,grid);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
