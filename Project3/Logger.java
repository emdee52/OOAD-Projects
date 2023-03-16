import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/*
 *
 * This file is used to help implement the Observer pattern and singleton pattern
 * The singleton pattern is implemented using the lazy method
 * 
 * 
 */
public class Logger implements Listener{
    private static Logger instance;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) { // Lazy method for singleton instantiation
            instance = new Logger();
        }
        return instance;
    }
    @Override
    public void notifyEvent(String event, int day) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("Logger/Logger-"+day+".txt", true)); // Create/open file in append mode
            out.write(event+"\n"); // Write to output stream
            out.close();
        }
        catch (IOException e) {
            System.out.println("[ERROR] " + e); // Print error when exception occurs
        }
    }
}