import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Project {

    public static void main(String[] args) {
        try {
            var path = "src/main/resources/";
            var sc = new Scanner(new FileReader(path + "Показатель счастья по странам 2015.csv"));
            var countries = new ArrayList<Country>();
            sc.nextLine();
            while (sc.hasNextLine()) {
                var data = sc.nextLine().split(",");
                var stata = new Statistics(data);
                var country = new Country(data[0], data[1], stata);
                countries.add(country);
            }

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path + "Country.db");
            Statement statement = connection.createStatement();


//            создание базы данных
//            createData(countries, statement);

            saveGraphDataFile(path, statement);
            System.out.println("==== 2 ====");
            showCountry(statement, "SELECT name,max(economy) FROM Country WHERE region = \"Latin America and Caribbean\" OR region = \"Eastern Asia\"");
            // 3 как я понял, надо найти максимальный средний по всем показателям
            System.out.println("==== 3 ====");
            showCountry(statement, "select *, " +
                    "('happiness rank' + 'happiness score' + 'standard error' + economy + family + health + freedom + trust + generosity + 'dystopia residual')/10 as x " +
                    "from Country " +
                    "where region = \"Western Europe\" or region = \"North America\" " +
                    "order by x DESC");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void showCountry(Statement statement, String query) throws SQLException {
        var result = statement.executeQuery(query);
        System.out.println(result.getString("name"));
    }

    private static void saveGraphDataFile(String path, Statement statement) throws FileNotFoundException, SQLException {
        var pw = new PrintWriter(new File(path + "output.txt"));
        var rs = statement.executeQuery("SELECT name,sum(economy) as e FROM Country GROUP BY name ORDER BY e");
        while (rs.next()) {
            pw.println(rs.getString("name") + ";=" + rs.getString("e").replace(".", ","));
        }
        pw.close();
    }

    private static void createData(ArrayList<Country> countries, Statement statement) throws SQLException {
        statement.execute("CREATE TABLE Country (" +
                "name varchar, " +
                "region varchar, " +
                "'Happiness Rank' int, " +
                "'Happiness Score' real, " +
                "'Standard Error' real, " +
                "Economy real, " +
                "Family real, " +
                "Health real, " +
                "Freedom real, " +
                "Trust real, " +
                "Generosity real, " +
                "'Dystopia Residual' real); ");

        for (var country : countries) {
            statement.executeUpdate(country.toString());
        }
    }

}
