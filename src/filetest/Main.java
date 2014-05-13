package filetest;

import java.io.*;
import java.sql.SQLException;


class Main {

    static
    {
        try
        {
            if(System.getProperty("os.name").toLowerCase().contains("windows"))
            {
                PrintStream out = new PrintStream(System.out, true, "Cp866");
                PrintStream err = new PrintStream(System.err, true, "Cp866");
                System.setOut(out);
                System.setErr(err);
            }
        }
        catch(UnsupportedEncodingException e)
        {
            System.err.println("Couldn't change console encoding " + e);
        }
    }

//    static{
//        BufferedReader input = null;
//        if(System.getProperty("os.name").equals("Windows")) {
//            try {
//                System.setOut(new PrintStream(System.out, true, "Cp866"));
//                input = new BufferedReader(new InputStreamReader(System.in, "Cp866"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//        }
//        else
//            input = new BufferedReader(new InputStreamReader(System.in));
//    }



    public static void main(String[] args) throws IOException, SQLException {


        // first check to see if the program was run with the command line argument
        if (args.length < 1) {

//            System.loadLibrary("mysql-connector-java-5.1.30-bin.jar");
            Menu menu = new Menu();
            menu.checkConsoleMessage("HELP");
        } else {
//            System.setProperty("java.library.path", "./lib");
            Menu menu = new Menu();
            menu.checkCommandLineArgument(args[0]);
        }
    }
}
