// It annoys me to type SYSTEM.OUT.PRINTLN every time I want to print!!!!
// Sorry, I'll calm down now.
public interface SysOut {
    default void out(String msg) {
        System.out.println(msg);
    }
    default void outP(String msg, Announcer announcer, int day) { // Print to console and publish events
        announcer.publishEvent(msg, day);
        out(msg);
    }
}
