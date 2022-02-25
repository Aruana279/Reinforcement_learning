import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    private int columnNumber = -1;
    private int rowNumber = -1;

    public String readFile(String pathName) {

        String terrainString = "";

        try {
            File terrainMap = new File(pathName);
            Scanner reader = new Scanner(terrainMap);
            int totalRowCount = 0;

            while (reader.hasNextLine()){
                String data = reader.nextLine();
                terrainString = terrainString + data + "\t";

                if (totalRowCount==0) {
                    this.columnNumber = getColumnCount(data);
                }

                totalRowCount++;
                //System.out.println(data);
            }
            this.rowNumber = totalRowCount;
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }

        return terrainString;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public int getColumnCount(String row) {
        String[] noTabs = row.split("\t");
        return noTabs.length;
    }


}
