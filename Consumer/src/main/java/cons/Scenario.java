package cons;

/**
 * singleton
 * @author Alex Binguy
 */
public class Scenario {

    // TODO complete the attribute list
    private String conf = null;
    private int nbRequests = 0;

    private Scenario() {
        conf = "";
    }

    private static class ScenarioHolder {
        private final static Scenario instance = new Scenario();
    }

    public static Scenario getInstance() {
        return ScenarioHolder.instance;
    }
    
    public void init(String conf) {
        // TODO parse conf to initialize the attributes
        this.conf = conf;
    }



}
