public class Main {
    
    public static void main(String[] args) {
        // Logic.logic();
        GUI gui = new GUI(new Logic());
        System.out.println("Program started");

        gui.engage();
    }
}
