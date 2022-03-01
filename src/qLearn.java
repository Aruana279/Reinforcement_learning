import java.util.ArrayList;
import java.util.Random;

public class qLearn {
    private double[][] R;
    private double[][] Q;
    private double givenTime;
    private double givenProbability;
    private gWorld grid;
    private int statesCount;
    private final double alpha = 0.1;
    private final double gamma = 0.9;
    private double startTime;


    public qLearn(gWorld gWorld, double givenTime, double givenProbability, double reward) {
        this.grid = gWorld;
        this.givenTime = givenTime;
        this.givenProbability = givenProbability;
        this.statesCount = gWorld.getHeight() * gWorld.getWidth();
        init();
        qLearnImplement();
    }

    public void qLearnImplement(){
        startTime = getCurrentTimeInSeconds();
        Random rand = new Random();
        while((System.currentTimeMillis() / 1000.0) - startTime < givenTime){
            int currentNode = getstartTimeLocation();
            while (!isFinalNode(currentNode)){
                double probability = rand.nextDouble();
                if (probability < 0.1){
                    int[] actionsFromCurrentState = possibleActions(currentNode);

                    int index = rand.nextInt(actionsFromCurrentState.length);
                    int nextNode = actionsFromCurrentState[index];

                    double q = Q[currentNode][nextNode];
                    double maxQ = randomQ(nextNode);
                    int r = (int)R[currentNode][nextNode];

                    double value = q + alpha * (r + gamma * maxQ - q);
                    Q[currentNode][nextNode] = value;

                    currentNode = nextNode;
                } else {
                    int[] actionsFromCurrentState = possibleActions(currentNode);

                    int index = rand.nextInt(actionsFromCurrentState.length);
                    int nextNode = actionsFromCurrentState[index];

                    double q = Q[currentNode][nextNode];
                    double maxQ = maxQ(nextNode);
                    int r = (int)R[currentNode][nextNode];

                    double value = q + alpha * (r + gamma * maxQ - q);
                    Q[currentNode][nextNode] = value;

                    currentNode = nextNode;
                }
            }
        }
        //System.out.println("Training finished in " + ((System.currentTimeMillis() / 1000.0) - startTime) +" seconds");
        printQ();
        printMove();
    }

    private void init(){
        R = new double[statesCount][statesCount];
        Q = new double[statesCount][statesCount];

        int i = 0;
        int j = 0;
        // We will navigate through the reward matrix R using k index
        for (int a = 0; a < statesCount; a++) {

            // We will navigate with i and j through the maze, so we need
            // to translate a into i and j
            i = a / grid.getWidth();
            j = a - i * grid.getWidth();

            // Fill in the reward matrix with -1
            for (int s = 0; s < statesCount; s++) {
                R[a][s] = -1;
            }

            // If not in final state or a wall try moving in all directions in the maze
            if (grid.getGrid()[i][j] != 'F') {

                // Try to move left in the maze
                int goLeft = j - 1;
                if (goLeft >= 0) {
                    int target = i * grid.getWidth() + goLeft;
                    if (grid.getGrid()[i][goLeft] == 0) {
                        R[a][target] = 0;
                    } else if (grid.getGrid()[i][goLeft] > 0) {
                        R[a][target] = grid.getGrid()[i][goLeft];
                    } else {
                        R[a][target] = grid.getGrid()[i][goLeft];
                    }
                }

                // Try to move right in the maze
                int goRight = j + 1;
                if (goRight < grid.getWidth()) {
                    int target = i * grid.getWidth() + goRight;
                    if (grid.getGrid()[i][goRight] == 0) {
                        R[a][target] = 0;
                    } else if (grid.getGrid()[i][goRight] > 0) {
                        R[a][target] = grid.getGrid()[i][goRight];
                    } else {
                        R[a][target] = grid.getGrid()[i][goRight];
                    }
                }

                // Try to move up in the maze
                int goUp = i - 1;
                if (goUp >= 0) {
                    int target = goUp * grid.getWidth() + j;
                    if (grid.getGrid()[goUp][j] == 0) {
                        R[a][target] = 0;
                    } else if (grid.getGrid()[goUp][j] > 0) {
                        R[a][target] = grid.getGrid()[goUp][j];
                    } else {
                        R[a][target] = grid.getGrid()[goUp][j];
                    }
                }

                // Try to move down in the maze
                int goDown = i + 1;
                if (goDown < grid.getHeight()) {
                    int target = goDown * grid.getWidth() + j;
                    if (grid.getGrid()[goDown][j] == 0) {
                        R[a][target] = 0;
                    } else if (grid.getGrid()[goDown][j] > 0) {
                        R[a][target] = grid.getGrid()[goDown][j];
                    } else {
                        R[a][target] = grid.getGrid()[goDown][j];
                    }
                }
            }
        }
        copyQ();
        printQ();

    }

    // Copying R table values to Q table
    private void copyQ() {
        for (int i = 0; i < statesCount; i++){
            for(int j = 0; j < statesCount; j++){
                Q[i][j] = R[i][j];
            }
        }
    }

    private double randomQ(int nextLocation){
        int[] actionsFromCurrent = possibleActions(nextLocation);
        Random random = new Random();
        int randomIndex = random.nextInt(actionsFromCurrent.length);
        double value = Q[nextLocation][randomIndex];
        return value;
    }

    private double maxQ(int nextLocation) {
        int[] actionsFromCurrent = possibleActions(nextLocation);

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

    // Getting random startTime location within the Q table
    private int getstartTimeLocation() {
        Random random = new Random();
        return random.nextInt(statesCount);
    }

    // Terminal state when value is bigger than 0
    private boolean isFinalNode(int coord){
        int y = coord / grid.getWidth();
        int x = coord - y * grid.getWidth();
        return grid.getGrid()[y][x] > 0;
    }
    //possible actions for current location/node
    int[] possibleActions(int coord){
        ArrayList<Integer> actions = new ArrayList<>();
        for (int i = 0; i < statesCount; i++){
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

    void printMove() {
        System.out.println("\nPrint policy");
        for (int i = 0; i < statesCount; i++) {
            System.out.println("From node " + i + " go to node " + getMoveFromCurrent(i));
        }
    }

    int getMoveFromCurrent(int state) {
        int[] actionsFromState = possibleActions(state);

        double maxValue = Double.MIN_VALUE;
        int policyGotoState = state;

        // Pick to move to the state that has the maximum Q value
        for (int nextNode : actionsFromState) {
            double value = Q[state][nextNode];

            if (value > maxValue) {
                maxValue = value;
                policyGotoState = nextNode;
            }
        }
        return policyGotoState;
    }

}
