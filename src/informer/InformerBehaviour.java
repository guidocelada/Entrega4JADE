package informer;


import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.TickerBehaviour;
import java.util.HashMap;

/**
 * Performs the task of the InformerAgent:
 * 
 * Informs the processing load, the total JVM memory, 
 * and the total computer memory of the containers. 
 * 
 * @author Guido J. Celada
 */
@SuppressWarnings("serial")
public class InformerBehaviour extends TickerBehaviour {
    
    private static int mb = 1024*1024;
    
    private static final int PERIOD = 10000; //10 seconds 
    
    private String[] containers = { "Container-1", "Container-2" }; //the containers to move to
       
    private HashMap<String,Integer> results;
    
    
    public InformerBehaviour(Agent a) {
        super(a, PERIOD);
        initialize();
    }
    
    private void initialize() {
        results = new HashMap<>();
        results.put("jvmMemory", 0);
        results.put("processingLoad", 0);
        results.put("totalMemory", 0);
    }
    
    @Override
    public void onTick() {
        for (String container : containers) {
                this.myAgent.doMove(new ContainerID(container, null));
                results.put("jvmMemory", results.get("jvmMemory") + (int) Runtime.getRuntime().totalMemory()); 
        }
        this.myAgent.doMove(new ContainerID("Main-Container", null));
        System.out.println("Total JVM Memory: " + results.get("jvmMemory") + "\n");
        initialize();
    }
    
}
