package Logger;

public class Log {
    public static boolean debug = true;

    public static void NowLn(String log) {
        if (debug)
            System.out.println(log);
    }
    public static void NowLn() {
        NowLn("");
    }
    public static void Now(String log) {
        if (debug)
            System.out.print(log);
    }
}
