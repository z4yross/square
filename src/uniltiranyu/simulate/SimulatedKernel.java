package uniltiranyu.simulate;
import speco.array.Array;
import uniltiranyu.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gómez
 * @version 1.0
 */
public class SimulatedKernel extends Kernel{
  public SimulatedKernel(Array<Agent> _agents, Environment environment ) {
    super( _agents );
    int n = agents.size();
    try{ for( int i=0; i<n; i++ ) agents.set( i, new SimulatedAgent(environment, agents.get(i).getProgram()) ); }catch(Exception e){}
  }
}
