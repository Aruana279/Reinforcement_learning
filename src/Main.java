public class Main {
    // arg 1: file name; arg 2: time to learn(sec); arg 3: probability of movement to desired direction; arg 4: const reward
    public static void main(String[] args) {

        // Assigning input values
        String fileName = args[0];
        double seconds = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        double reward = Double.parseDouble(args[3]);

        // Using helper class to create board with values from txt
        gWorld gWorld = gWorldCreator.createBoard(fileName);

        // Running QLearn with input values
        qLearn qlearn = new qLearn(gWorld,seconds,probability,reward);


    }
}
