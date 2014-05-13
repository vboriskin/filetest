package filetest;

import java.sql.*;


public class Database {



    /**
     * Checks if driver works
     */

    public void checkDatabaseDriver() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
             /*Class.forName() служит для загрузки класса по его имени.
                                                         Загружать драйвер следует всего один раз на протяжении жизни приложения,
                                                         но до того, как будет создаваться соединение с БД.*/

            System.out.println("Driver loading success!");

                /* URL к базе состоит из протокола:подпротокола://других_сведений. */
            String url = "jdbc:mysql://localhost/mysql";
            String name = "root";
            String password = "32167";
            try {

                    /* Class DriverManager  manages the drivers, connections, as well as trace. */
                    /* This line creates a connection*/
                    /* Static method getConnection () looks at the list of drivers, and if found suitable
                      to the specified URL, then creates and returns a connection.*/
                Connection con = DriverManager.getConnection(url, name, password);
                /*Как только сервер проверил правильность имени пользователя и пароля, создается соединение с БД*/
                System.out.println("Connected.");
                /*Это соединение удерживается до тех пор, пока приложение не высвободит соединение, либо пока соединение не будет закрыто сервером*/
                con.close();
                System.out.println("Disconnected.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void databaseQuery(String sqlQuery) {
        Connection connection = null;
        Statement statement = null;
        try {
            //Loads driver
            Class.forName("com.mysql.jdbc.Driver");
            //Creates connection to DB
            String url = "jdbc:mysql://localhost/mysql";
            connection = DriverManager.getConnection(url, "root", "32167");
            //To execute SQL-commands, creates a Statement.
            statement = connection.createStatement();

            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void databaseQuery(String sqlQuery, String databaseName) {
        Connection connection = null;
        Statement statement = null;
        try {
            //Загружаем драйвер
            Class.forName("com.mysql.jdbc.Driver");
            //Нужно создать подключение к БД. У MySQL обязательно есть системная база,
            //к ней и будем создавать соединение.
            //Подключаемся к новосозданной базе. Значение параметров после "?"
            //ясно из их имен.
            String url = "jdbc:mysql://localhost/" +
                    databaseName +
                    "?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "32167");
            //Для выполнения SQL-команд нужно создать объект Statement. Для этой цели будет использоваться Connection:
            statement = connection.createStatement();
            //Обратите внимание, что создаем базу с помощью executeUpdate(). Об этом мы поговорим немного позже.
            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //позакрываем теперь все
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*Выбираем столбцы*/
    public void selectFromDatabase() {

        /*SELECT *(все столбцы)    // Определяет стобцы, которые должны быть включены в результирующий набор запроса.
         //SELECT DISTINCT name    // ключевое слово DISTINCT исключает дублирующие строки


         или name,artist, ...

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         FROM songs                // Указывает таблицы, из которых должны быть извлечены данные, и то,
                                   // как эти таблицы должны быть соединены.
         FROM employee
         INNER JOIN department     // Значение столбца dept_id таблицы employee для поиска соотвествующего отдела
         ON employee.dept_id =     // в котором работает сотрдуник, хранящийся в таблице employee.
            department.dept_id ;

         FROM employee e
         INNER JOIN department d   // Присваеваем псевдоним каждой таблице.
         ON e.dept_id =            // Далее псевдонимы используются в блоке ON при описании условий соединения,
            d.dept_id ;            // а также в блоке select при задании столбцов, которые должны быть включены
                                      в результирующий набор

                                   // Если определенное значение столбца присутствует в одной таблице, но его нет в другой
                                      соединение строк не происходит, и они н включаются в результирующий набор.
                                      Такой тип соеденения называют внутренним(inner join);

         ON e.dept_id =            // Эквисоединения(Equi-joins), для успешности соединения значения должны совпадать,
            d.dept_id ;            // используется знак равенства

         ON e.date => d.date_offer // Неэквисоединения(Equi-joins), для  соединения используется диапозон значений.
         AND
            e.date =< d.date_ret;  //

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         WHERE artist= 'AC/DC'     // Ограничивает число строк в окончательном результирующем наборе.
                                   // Блок WHERE - Механизм отсеивания нежелательных строк из результирующего набора.
         AND start_date > '200'    // Несколько условий фильтрации.
         OR something_s = 'AA'     // При использовании оператора OR достаточно, чтобы выполнялось только одно из условий.
         (something AND anything)  // Условия можно группировать круглыми скобками.
         OR (something_else)

         WHERE product_cd IN
         ('CHK','SAV','CD','MM');  // Указываем несколько значений, которые может принять столбец

         WHERE product_cd IN
         (SELECT                   // Вместо явного указания имен создаем набор выражений с помощью подзапроса.
         product_cd FROM product
         WHERE
         product_type_cd='ACCOUNT');

         WHERE product_cd NOT IN
         ('CHK','SAV','CD','MM');  // Проверяем на отсутствие определенного выражения.

         WHERE LEFT (lname,1)='T'; // Ищем содержимое в столбце lname, которое начинается с 'T'.

         WHERE
         product_cd like '%C%';    // Символ "%" замещает разное количество символов
                          "F%"     // Строки, начинающиеся с "F"
                          "%t"     // Строки, заканчивающиеся на "t"
                          "%bas"   // Строки, содержащие подстроку "bas"
                          "__t_'"  // Строки, состоящие из четырех символов с "t" в третьей позиции
         OR like '_A';             // Символ "_" замещает один символ

         WHERE
         lname REGEXP '^[FG]';     // Оператор regexp принимает регулярное выражение и применяет его к выражению,
                                   // находящемуся в левой  части условия.

         WHERE
         lname IS NULL             // С помощью оператора IS NULL можно проверить выражение на значение null
         (= NULL - неправильно)
         lname IS NOT NULL         // -//-//- не null



         WHERE date BETWEEN
         '2012-10-12' AND          //Первой указывается нижняя граница. Верхняя и нижняя границы включаются в диапозон
         '2013-11-05';


         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         GROUP BY                  // Используется для группировки строк по одинаковым значениям столбцов

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         HAVING                    // Ограничивает число строк в окончательном результирующм наборе с помощью группировки данных

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         ORDER BY name;            // ORDER BY указывает серверу, как сортировать данные, возвращаемые запросом.
         ORDER BY something_s DESC;// Ключевое слово DESC применяется, если требуется сортировка по убыванию.
                                   // ASC по увеличению (Но она не требуется, т.к. стоит по умполчанию).
         ORDER BY RIGHT(fed_id, 3);// С помощью функции right() извлекаем 3 последних символа значения столбца fed_id
                                   // и сортирует на основании этого значения
         ORDER BY 2, 5;            // Сортировка по номеру стобцов, возвращаемого запроса.

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-
         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         UNION, UNION ALL

         select cust_id, lname
         from individual
         UNION ALL                    //UNION ALL в конечной таблице всегда будет столько строк, сколько во всех исходных
         select cust_id, name         //таблицах в сумме
         from business
         UNION ALL

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         INSERSECT, INTERSECT ALL


         select emp_id
         from employee
         where assigned_branch_id = 2
         and (title= 'Teller'
         or title = 'Head Teller')
         INTERSECT                    //Оператор INTERSECT предназначен для выполнения пересечений.
         select distinct open_emp_id
         from account where open_branch_id = 2;

         INTERSECT ALL                //Оператор INTERSECT ALL не удаляет дублирующие строки пересечений.

         -=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=-

         EXCEPT                       //Операция except возвращает первую таблицу за вычетом всех перекрытий со второй таблицей

         select emp_id
         from employee
         where assigned_branch_id = 2
         and (title= 'Teller' or title = 'Head Teller')
         EXCEPT
         select distinct open_emp_id
         from account;
          */
    }


    /*Выводим в консоль таблицу*/
    public void printDatabaseTable() {

        Connection con;

        String url = "jdbc:mysql://localhost/pairlist" +
                "?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
        String name = "root";
        String password = "32167";
        try {               DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            con = DriverManager.getConnection(url, name, password);
            System.out.println("Connected.");
            Statement st = con.createStatement();
            String query = "select * from pairs";
            ResultSet rs = st.executeQuery(query);

            printResults(rs);
            System.out.println("Disconnected.");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void printResults(ResultSet rs) throws SQLException {

        String format = "| %-25s | %-25s | %-25s|";
        String border = "-----------------------------------------------------------------------------------";
        String id, name, value;
        System.out.println(border);
        System.out.printf(format, "id", "name", "value");
        System.out.print("\n" + border);
        while (rs.next()) {
            id = rs.getString("id");
            name = rs.getString("name");
            value = rs.getString("value");
            System.out.println();

            System.out.printf(format, id, name, value);
        }
        System.out.println("\n" + border);
    }
}