import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements Listener{
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