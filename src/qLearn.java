import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class qLearn {
    private final double gamma = 0.9;
    private double time;
    private double prob;
    private double costOfMove;
    private gWorld gWorld;
    private int stateCount;
    private int actionCount;
    private double startTime;
    private double[][] R;
    private double[][] Q;

    public qLearn(gWorld gWorld, double time, double probabilityOfDesired, double reward) {
        this.gWorld = gWorld;
        this.time = time;
        this.prob = probabilityOfDesired;
        this.costOfMove = reward;
        this.stateCount = gWorld.getHeight() * gWorld.getWidth();
        initialisation();
        run();
    }

    public void run(){
        startTime = getCurrentTimeInSeconds();
        while((System.currentTimeMillis() / 1000.0) - startTime < time){
            //TODO: Implement Q Learn Algorithm here
            int currentLocation = getStartLocation();
            while (!isTerminalState(currentLocation)){
                System.out.print(Arrays.deepToString(R));
                int[] actionsFromCurrent = possibleActionsFromCurrent(currentLocation);
                Random random = new Random();

                // Random direction exploration
                if (random.nextDouble() < 0.1){

                    // Exploration
                    int index = random.nextInt(actionsFromCurrent.length);
                    int nextLocation = actionsFromCurrent[index];

                    // Retrieving the values for Q algorithm formula
                    double q = Q[currentLocation][nextLocation];
                    // Note: We're using Random direction
                    double maxQ = randomQ(nextLocation);
                    double reward = R[currentLocation][nextLocation];

                    // Calculating Q Value for Current Node
                    double qValue = q + prob *(reward + gamma + maxQ - q);
                    Q[currentLocation][nextLocation] = qValue;

                    // Assigning new Location for Current Node
                    currentLocation = nextLocation;
                } else {
                    // Exploration
                    System.out.println("Length of possible actions: "+actionsFromCurrent.length);
                    int index = random.nextInt(actionsFromCurrent.length);
                    int nextLocation = actionsFromCurrent[index];

                    // Retrieving the values for Q algorithm formula
                    double q = Q[currentLocation][nextLocation];
                    double maxQ = maxQ(nextLocation);
                    double reward = R[currentLocation][nextLocation];

                    // Calculating Q Value for Current Node
                    double qValue = q + prob *(reward + gamma + maxQ - q);
                    Q[currentLocation][nextLocation] = qValue;

                    // Assigning new Location for Current Node
                    currentLocation = nextLocation;
                }
            }
        }
        printQ();
    }

    private void initialisation(){
        R = new double[stateCount][stateCount];
        Q = new double[stateCount][stateCount];

        for (int i = 0; i < stateCount; i++){

            int y = i / gWorld.getWidth();
            int x = i - y * gWorld.getWidth();



            // Populating Reward Table with -1 value for impossible transitions
            for (int k = 0; k < stateCount; k++){
                R[i][k] = -1;
            }

            // If not final state try to move all possible directions
            if (gWorld.getGWorld()[y][x] <=0){

                // Going left
                int left = x - 1;
                if (left >= 0) {
                    int newY = y * gWorld.getWidth() + left;
                    if (gWorld.getGWorld()[y][left] == 0.0) {
                        R[i][newY] = 0.0;
                    } else {
                        R[i][newY] = gWorld.getGWorld()[y][left];
                    }
                }

                // Going right
                int right = x + 1;
                if (right < gWorld.getWidth()) {
                    int newY = y * gWorld.getWidth() + right;
                    if (gWorld.getGWorld()[y][right] == 0.0) {
                        R[i][newY] = 0.0;
                    } else {
                        R[i][newY] = gWorld.getGWorld()[y][right];
                    }
                }

                // Going up
                int up = y - 1;
                if (up >= 0) {
                    int newY = up * gWorld.getWidth() + x;
                    if (gWorld.getGWorld()[up][x] == 0.0) {
                        R[i][newY] = 0.0;
                    } else {
                        R[i][newY] = gWorld.getGWorld()[up][x];
                    }
                }

                // Going down
                int down = y + 1;
                if (down < gWorld.getHeight()) {
                    int newY = down * gWorld.getWidth() + x;
                    if (gWorld.getGWorld()[down][x] == 0.0) {
                        R[i][newY] = 0.0;
                    } else {
                        R[i][newY] = gWorld.getGWorld()[down][x];
                    }
                }

            }
        }
        copyQ();
        printQ();

    }

    // Copying R table values to Q table
    private void copyQ() {
        for (int i = 0; i < stateCount; i++){
            for(int j = 0; j < stateCount; j++){
                Q[i][j] = R[i][j];
            }
        }
    }

    private double randomQ(int nextLocation){
        int[] actionsFromCurrent = possibleActionsFromCurrent(nextLocation);
        Random random = new Random();
        int randomIndex = random.nextInt(actionsFromCurrent.length);
        double value = Q[nextLocation][randomIndex];
        return value;
    }

    private double maxQ(int nextLocation) {
        int[] actionsFromCurrent = possibleActionsFromCurrent(nextLocation);

        double maxValue = -10;
        for (int nextAction : actionsFromCurrent) {
            double value = Q[nextLocation][nextAction];

            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
    }

    private double getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }

    // Getting random start location within the Q table
    private int getStartLocation() {
        Random random = new Random();
        return random.nextInt(stateCount);
   }

   // Terminal state when value is bigger than 0
    private boolean isTerminalState(int coord){
        int y = coord / gWorld.getWidth();
        int x = coord - y * gWorld.getWidth();
//        System.out.println("y = " + y + "; x = " + x);
//        System.out.print("Maze value: " + lookupTable.getMaze()[y][x]);
        return gWorld.getGWorld()[y][x] > 0;
    }

    // All possible actions from state
    int[] possibleActionsFromCurrent(int coord){
        ArrayList<Integer> actions = new ArrayList<>();
        for (int i = 0; i < stateCount; i++){
            if (R[coord][i] != -1){
                actions.add(i);
            }
        }
        return actions.stream().mapToInt(i -> i).toArray();
    }

    void printQ() {
        System.out.println("Q matrix");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("From state " + i + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.printf("%6.2f ", (Q[i][j]));
            }
            System.out.println();
        }
    }

}
