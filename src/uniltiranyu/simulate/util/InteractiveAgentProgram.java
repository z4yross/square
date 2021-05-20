package uniltiranyu.simulate.util;


import speco.array.Array;
import uniltiranyu.*;


public class InteractiveAgentProgram implements AgentProgram{
  protected Array<String> cmds = new Array<String>();
  protected Language language;
  public static  InteractiveAgentFrame frame = null;
  public InteractiveAgentProgram(Language _language){
    language = _language;
    if( frame == null ){
      frame = new InteractiveAgentFrame(this);
    }
    frame.setVisible(true);
  }

  public Action compute( Percept p ){
  	if( cmds.size() > 0 ){
  		try{
    		Action x = new Action( cmds.get(0) );
    		cmds.remove(0);
    		return x;
  	   	}catch(Exception e){}
   	}
    return null; 
  }

  public void setCommands( String _cmds ){
    String[] actions = _cmds.split(",");
    for( int i=0; i<actions.length; i++ ){
      cmds.add(actions[i]);
    }
  }

  public void init(){
    cmds.clear();
  }

  public boolean goalAchieved( Percept p ){
    return (((Boolean)p.get(language.getPercept(4))).booleanValue());
  }
  
  public static void main( String[] args ){
      new InteractiveAgentProgram(null);
  }
  
}
