package filetest;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;


class Main {


    static {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                PrintStream out = new PrintStream(System.out, true, "Cp866");
                PrintStream err = new PrintStream(System.err, true, "Cp866");
                System.setOut(out);
                System.setErr(err);
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Couldn't change console encoding " + e);
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        // first check to see if the program was run with the command line argument
        if (args.length < 1) {
            Menu menu = new Menu();
            menu.checkConsoleMessage("HELP");
        } else {
            Menu menu = new Menu();
            menu.checkCommandLineArgument(args[0]);
        }
    }
}
