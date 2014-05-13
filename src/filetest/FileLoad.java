package filetest;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileLoad {

    /**
     * Stores all pairs of this FileLoad
     */
    private List<Pair> pairList = new ArrayList<>();

    /**
     * Loads pairs from a text file
     * and adds them to {@link #pairList} using {@link #addNewPair(String s)}
     *
     * @param sourceFileName the location of the source file
     * @throws java.io.IOException If an input or output
     *                             exception occurred
     */
    public void scanFile(String sourceFileName) throws IOException {

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(sourceFileName), "UTF-8")
            );
            String line;
            while ((line = reader.readLine()) != null) {
                addNewPair(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }


    /**
     * Checks if the incoming string matches regex with {@link #checkWithRegExp(String)},
     * then splits the string by value "="
     * and adds new Pair to this {@link #pairList}.
     * Also displays the message if line is null or does not match.
     *
     * @param s string is used to generate a new Pair.
     * @throws NullPointerException if s is null
     */
    public void addNewPair(String s) {

        try {
            String name = null;
            String value = null;
            String[] box;
            int keyValueCount = 0;

            if (checkWithRegExp(s)) {
                box = s.trim().split("=");

                for (String b : box) {
                    if (keyValueCount % 2 == 0) {
                        name = b.trim();
                    } else {
                        value = b.trim();
                    }
                    keyValueCount++;
                }
                Pair pair = new Pair(name, value);
                pairList.add(pair);
            } else if (s.matches("\\s+") || s.isEmpty()) {
                System.out.println("Line does not match: " + "null");
            } else {
                System.out.println("Line does not match: " + s);
            }
        } catch (NullPointerException e) {
            System.out.print("Inside addNewPair():");
        } catch (Exception e) {
            System.out.print("Inside addNewPair():");
            e.printStackTrace();
        }
    }

    /**
     * Checks if the incoming string matches regex
     *
     * @param s incoming string
     * @return true if userNameString matches regex, false if not
     */
    public boolean checkWithRegExp(String s) {
        Pattern p = Pattern.compile("^_?[a-z|A-Z]\\w+\\s?\\=.+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * Displays all pairs from {@link #pairList}
     */
    public void showList() {
        try {
            for (Pair e : pairList) {
                System.out.print(e.getName() + " = " + e.getValue() + "\n");
            }
        } catch (Exception e) {
            System.out.print("Inside showList():");
            e.printStackTrace();
        }
    }

    /**
     */
    public void addListToDatabase() {
        try {
            for (Pair e : pairList) {
                Database.databaseQuery("INSERT INTO " + "pairlist.pairs(name,value) " +
                        "VALUES ('" + e.getName() + "','" + e.getValue() + "');");

            }
        } catch (Exception e) {
            System.out.print("Inside showList():");
            e.printStackTrace();
        }
    }

    /**
     * Adds {@link #pairList} to the database.
     *
     * @param databaseName name of the database
     * @param columns      list of the columns (column1, column2, etc...).
     */
    public void addListToDatabase(String databaseName, String columns) {
        try {
            for (Pair e : pairList) {
                Database.databaseQuery("INSERT INTO " + databaseName + "(" + columns + ") " +
                        "VALUES ('" + e.getName() + "','" + e.getValue() + "');");
                System.out.println("DATABASE SUCCESS");
            }
        } catch (Exception e) {
            System.out.print("Inside showList():");
            e.printStackTrace();
        }
    }

    /**
     * Saves {@link #pairList} to the file
     *
     * @param destFileName the location of the destination file
     * @throws java.io.IOException If an input or output
     *                             exception occurred
     */
    public void saveList(String destFileName) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(destFileName);) {

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream, "UTF-8"));
            for (Pair p : pairList) {
                bufferedWriter.write(p.getName() + "=" + p.getValue() + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (Exception e) {
            System.out.print("Inside saveList():");
            e.printStackTrace();
        }
    }

    /**
     * Clears {@link #pairList}
     */
    public void clearList() {
        try {
            pairList.clear();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Inside clearList():");

        }
    }

    /**
     * Deletes all pairs with the specified name from the {@link #pairList}.
     *
     * @param s string with the specified name.
     * @throws java.io.IOException If an input or output
     *                             exception occurred
     */
    public void deletePair(String s) throws IOException {
        ListIterator<Pair> it = pairList.listIterator();
        Pair pair;
        for (int i = 0; i < pairList.size(); i++) {

            if (it.hasNext()) {
                pair = it.next();

                if (pair.getName().equals(s)) {
                    System.out.println(pair.getName() + " " + pair.getValue() + " удалено.");

                    it.remove();
                }
            }
        }
    }

    /**
     * Filter {@link #pairList} by name and
     * displays only those pairs for which name matches a specified.
     *
     * @param s string with the specified name.
     * @throws java.io.IOException If an input or output
     *                             exception occurred
     */
    public void filterListByName(String s) throws IOException {
        ListIterator<Pair> it = pairList.listIterator();
        Pair pair;
        System.out.println("Фильтр по имени: " + s + "\n");
        for (int i = 0; i < pairList.size(); i++) {

            if (it.hasNext()) {
                pair = it.next();

                if (pair.getName().equalsIgnoreCase(s)) {
                    System.out.println(pair.getName() + " " + pair.getValue());
                }
            }
        }
    }

    /**
     * Filters the {@link #pairList} by value and
     * displays only those pairs for which value matches a specified.
     *
     * @param s string with the specified value.
     * @throws java.io.IOException If an input or output
     *                             exception occurred
     */
    public void filterListByValue(String s) throws IOException {
        ListIterator<Pair> it = pairList.listIterator();
        Pair pair;
        System.out.println("Фильтр по значению: " + s + "\n");
        for (int i = 0; i < pairList.size(); i++) {

            if (it.hasNext()) {
                pair = it.next();

                if (pair.getValue().equalsIgnoreCase(s)) {
                    System.out.println(pair.getName() + " " + pair.getValue());
                }
            }
        }
    }

    /**
     * Sorts the {@link #pairList} by name ascending.
     */
    public void sortListByNameAscending() {

        Collections.sort(pairList, new ComparatorNameAscending());
        for (Pair x : pairList) {
            System.out.println(x.getName() + "=" + x.getValue());
        }
    }

    /**
     * Sorts the {@link #pairList} by name descending.
     */
    public void sortListByNameDescending() {

        Collections.sort(pairList, new ComparatorNameDescending());
        for (Pair x : pairList) {
            System.out.println(x.getName() + "=" + x.getValue());
        }
    }

    /**
     * Sorts the {@link #pairList} by value ascending.
     */
    public void sortListByValueAscending() {

        Collections.sort(pairList, new ComparatorValueAscending());
        for (Pair x : pairList) {
            System.out.println(x.getName() + "=" + x.getValue());
        }
    }

    /**
     * Sorts the {@link #pairList} by name ascending.
     */
    public void sortListByValueDescending() {

        Collections.sort(pairList, new ComparatorValueDescending());
        for (Pair x : pairList) {
            System.out.println(x.getName() + "=" + x.getValue());
        }
    }


    static class ComparatorNameAscending implements Comparator<Pair> {
        @Override
        public int compare(Pair str1, Pair str2) {

            return str1.getName().compareToIgnoreCase(str2.getName());
        }
    }

    static class ComparatorNameDescending implements Comparator<Pair> {
        @Override
        public int compare(Pair str1, Pair str2) {

            return str2.getName().compareToIgnoreCase(str1.getName());
        }
    }

    static class ComparatorValueAscending implements Comparator<Pair> {
        @Override
        public int compare(Pair str1, Pair str2) {

            return str1.getValue().compareToIgnoreCase(str2.getValue());
        }
    }

    static class ComparatorValueDescending implements Comparator<Pair> {
        @Override
        public int compare(Pair str1, Pair str2) {

            return str2.getValue().compareToIgnoreCase(str1.getValue());
        }
    }
}




