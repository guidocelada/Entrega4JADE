package informer;


import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.Location;
import java.util.HashMap;

/**
 * This is a mobile agent that moves between containers and informs each X
 * seconds: the processing load, the total JVM memory, and the total computer
 * memory of all the containers. It also informs the total time that took this
 * "tour".
 *
 * @author Celada, Soria
 */
@SuppressWarnings("serial")
public class InformerAgent extends Agent {  
    
    private static int mb = 1024*1024;
    
    private String[] containers = { "Container-1", "Container-2" }; //the containers to move to
       
    private HashMap<String,Integer> results;
    
    private Location origin;
    
    private int iterator;
    
    
    private void initialize() {
        this.iterator = 0;
        results = new HashMap<>();
        results.put("jvmMemory", 0);
        results.put("processingLoad", 0);
        results.put("totalMemory", 0);
    }
    
    @Override
    public void setup() {
        System.out.println("\nDEBUG: Initiating " + this.getLocalName());
        
        initialize();
        this.origin = this.here();
        this.doMove(new ContainerID(containers[iterator], null));     
    }
    
    @Override
    public void afterMove() {      
        
        if (this.here().getID().equals(this.origin.getID())) {
            System.out.println("Total JVM Memory: " + results.get("jvmMemory") + "mb \n");
            this.doDelete();
        } else {
            System.out.println("DEBUG: " + this.getLocalName() + " passing through " + this.here().getName());
            System.out.println("DEBUG: " + this.getLocalName() + " JVM memory is: " + (Runtime.getRuntime().totalMemory() / mb) + " mb");

            int auxMemory = (int) (results.get("jvmMemory") + (Runtime.getRuntime().totalMemory() / mb));
            results.put("jvmMemory", auxMemory); 
            this.iterator++;
            if (iterator < this.containers.length) 
                this.doMove(new ContainerID(containers[iterator], null));
            else 
                this.doMove(this.origin);
        }
    }
    
    @Override
    public void takeDown() {
        System.out.println("DEBUG: Taking down " + this.getLocalName() + " at " + this.here().getName());
    }
}
