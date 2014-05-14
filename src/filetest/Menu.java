package filetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Menu {

    private static final String HELP = "HELP";
    private static final String SCAN_FILE = "SCANFILE";
    private static final String SHOW_LIST = "SHOWLIST";
    private static final String ADD_NEW_PAIR = "ADDNEWPAIR";
    private static final String DELETE_PAIR = "DELETEPAIR";
    private static final String FILTER_LIST_BY_NAME = "FILTERNAME";
    private static final String FILTER_LIST_BY_VALUE = "FILTERVALUE";
    private static final String SORT_LIST_BY_NAME_ASCENDING = "SORTNAMEASC";
    private static final String SORT_LIST_BY_NAME_DESCENDING = "SORTNAMEDESC";
    private static final String SORT_LIST_BY_VALUE_ASCENDING = "SORTVALUEASC";
    private static final String SORT_LIST_BY_VALUE_DESCENDING = "SORTVALUEDESC";
    private static final String SAVE_LIST = "SAVELIST";
    private static final String ADD_LIST_TO_DATABASE = "ADDLISTTODATABASE";
    private static final String CLEAR_LIST = "CLEARLIST";
    private static final String PRINT_DATABASE_TABLE = "PRINTDBTABLE";
    private static final String QUIT = "QUIT";


    private FileLoad file = new FileLoad();
    private Database d = new Database();



    public void commandList() {

        String format = "%-20s %s";

        System.out.printf(format, HELP, " Вывод справочной информации о командах.\n");

        System.out.printf(format, SCAN_FILE, " Загрузить пары из указанного текстового файла.\n");

        System.out.printf(format, SHOW_LIST, " Посмотреть текущий список всех пар на экране.\n");

        System.out.printf(format, ADD_NEW_PAIR, " Добавить новую пару в список.\n");

        System.out.printf(format, DELETE_PAIR, " Удалить существующую пару из списка. Из списка пар будут удалены все пары с заданным именем.\n");

        System.out.printf(format, FILTER_LIST_BY_NAME, " Фильтр по имени (регистр букв не учитывается).\n");

        System.out.printf(format, FILTER_LIST_BY_VALUE, " Фильтр по значению (регистр букв не учитывается).\n");

        System.out.printf(format, SORT_LIST_BY_NAME_ASCENDING, " Сортировать пары по имени в порядке возрастания.\n");

        System.out.printf(format, SORT_LIST_BY_NAME_DESCENDING, " Сортировать пары по имени в порядке убывания.\n");

        System.out.printf(format, SORT_LIST_BY_VALUE_ASCENDING, " Сортировать пары по значению в порядке возрастания.\n");

        System.out.printf(format, SORT_LIST_BY_VALUE_DESCENDING, " Сортировать пары по значению в порядке убывания.\n");

        System.out.printf(format, SAVE_LIST, " Сохранить список пар в текстовом файле.\n");

        System.out.printf(format, ADD_LIST_TO_DATABASE, " Сохранить список пар в базу данных.\n");

        System.out.printf(format, PRINT_DATABASE_TABLE, " Просмотреть таблицу базы данных.\n");

        System.out.printf(format, CLEAR_LIST, " Очистить список.\n");

        System.out.printf(format, QUIT, " Выйти из программы.\n\n");

    }


    public void checkCommandLineArgument(String cla) throws IOException {

        try {
            commandList();
            file.scanFile(cla);
            getUserInput();

        } catch (NullPointerException e) {
            System.out.println("Inside checkConsoleMessage: ");
            e.printStackTrace();

        }
    }

    public void checkConsoleMessage(String consoleMessage) throws IOException {

        try {
            boolean notExit = true;
            while (notExit) {
                switch (consoleMessage.toUpperCase().trim()) {

                    case HELP:
                        commandList();
                        break;

                    case SCAN_FILE:
                        System.out.println("Введите путь к файлу.");
                        file.scanFile(consoleInput());
                        break;

                    case SHOW_LIST:
                        file.showList();
                        break;

                    case ADD_NEW_PAIR:
                        System.out.println("Введите новую пару в виде <Имя> = <Значение>.");
                        file.addNewPair(consoleInput());
                        break;

                    case DELETE_PAIR:
                        System.out.println("Введите имя пары, которую требуется удалить. Из списка пар должны быть удалены все пары с заданным именем.");
                        file.deletePair(consoleInput());
                        break;

                    case FILTER_LIST_BY_NAME:
                        System.out.println("Фильтр по имени:\nВведите <Имя> пары, после чего на экране будут отображены только те пары, \nу которых <Имя> совпадает с заданным (регистр букв не учитывается).");
                        file.filterListByName(consoleInput());
                        break;

                    case FILTER_LIST_BY_VALUE:
                        System.out.println("Фильтр по значению:\nВведите <Значение> пары, после чего на экране будут отображены только те пары, \nу которых <Значение> совпадает с заданным (регистр букв не учитывается).");
                        file.filterListByValue(consoleInput());
                        break;

                    case SORT_LIST_BY_NAME_ASCENDING:
                        System.out.println("Сортировка по имени в порядке возрастания:\n");
                        file.sortListByNameAscending();
                        break;

                    case SORT_LIST_BY_NAME_DESCENDING:
                        System.out.println("Сортировка по имени в порядке убывания:\n");
                        file.sortListByNameDescending();
                        break;

                    case SORT_LIST_BY_VALUE_ASCENDING:
                        System.out.println("Сортировка по значению в порядке возрастания:\n");
                        file.sortListByValueAscending();
                        break;

                    case SORT_LIST_BY_VALUE_DESCENDING:
                        System.out.println("Сортировка по значению в порядке убывания:\n");
                        file.sortListByValueDescending();
                        break;

                    case SAVE_LIST:
                        System.out.println("Введите путь к файлу.");
                        file.saveList(consoleInput());
                        System.out.println("Список сохранен.");
                        break;

                    case ADD_LIST_TO_DATABASE:
                        System.out.println("Введите имя базы данных:");
                        String databaseName = consoleInput();
                        System.out.println("Введите столбцы базы данных:");
                        String columns = consoleInput();
                        file.addListToDatabase(databaseName, columns);
                        break;

                    case PRINT_DATABASE_TABLE:
                        d.printDatabaseTable();
                        break;

                    case CLEAR_LIST:
                        file.clearList();
                        System.out.println("Список очищен.");
                        break;

                    case QUIT:
                        notExit = false;
                        System.exit(0);
                        break;

                    default:
                        System.out.println("\"" + consoleMessage + "\" " + "Не является внутренней командой.\n");
                }
                getUserInput();
            }
        } catch (NullPointerException e) {
            System.out.println("Inside checkConsoleMessage: ");
            e.printStackTrace();

        }
    }

    public void getUserInput() throws IOException {
        try {
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(System.in,"Cp866"));                 //??? NULL

            checkConsoleMessage(in.readLine());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String consoleInput() throws IOException {
        BufferedReader in
                = new BufferedReader(new InputStreamReader(System.in,"Cp866"));
        return in.readLine();
    }
}
