public class Main {
    // arg 1: file name; arg 2: time to learn(sec); arg 3: probability of movement to desired direction; arg 4: const reward
    public static void main(String[] args) {
        String myFile = args[0];
        double seconds = Integer.parseInt(args[1]);
        double probability = Double.parseDouble(args[2]);
        double reward = Double.parseDouble(args[3]);
        gWorld gWorld = gridCreate.createGrid(myFile);
        qLearn qlearn = new qLearn(gWorld,seconds,probability,reward);
    }
}
