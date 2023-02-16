public class Main {
    public static void main(String[] args) {
        Dealership FNCD = new Dealership(); //Create dealership
        for(int i = 0; i < 30; i++) { //Loops through 30 days of dealership
            FNCD.open();
        }
    }
}
