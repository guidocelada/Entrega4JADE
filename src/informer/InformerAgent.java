package informer;


import jade.core.Agent;
import jade.core.behaviours.Behaviour;

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
    
    @Override
    public void setup() {
        System.out.println("\nDEBUG: Initiating " + this.getLocalName());
        addBehaviour(new InformerBehaviour(this));
    }
    
    @Override
    public void afterMove() {
        System.out.println("DEBUG: " + this.getLocalName() + " passing through " + this.here().getName());
    }
    
    @Override
    public void takeDown() {
        System.out.println("DEBUG: Taking down " + this.getLocalName() + " on location " + this.here().getName());
    }
}
