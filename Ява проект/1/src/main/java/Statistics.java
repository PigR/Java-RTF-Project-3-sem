import java.util.Arrays;

public class Statistics {
    private int happinessRank;
    private double happinessScore;
    private double standardError;
    private double economy;
    private double family;
    private double health;
    private double freedom;
    private double trust;
    private double generosity;
    private double dystopia;
    private String[] arr;

    public Statistics(String[] s) {
        arr = Arrays.copyOfRange(s,2,s.length);
        constructor(s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9],s[10],s[11]);
    }

    private void constructor(String happinessRank, String happinessScore, String standardError, String economy, String family, String health, String freedom, String trust, String generosity, String dystopia) {
        this.happinessRank = Integer.parseInt(happinessRank);
        this.happinessScore = Double.parseDouble(happinessScore);
        this.standardError = Double.parseDouble(standardError);
        this.economy = Double.parseDouble(economy);
        this.family = Double.parseDouble(family);
        this.health = Double.parseDouble(health);
        this.freedom = Double.parseDouble(freedom);
        this.trust = Double.parseDouble(trust);
        this.generosity = Double.parseDouble(generosity);
        this.dystopia = Double.parseDouble(dystopia);
    }

    @Override
    public String toString() {
        return String.join(",", arr);
    }
}
