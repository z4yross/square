package uniltiranyu.examples.games.sokoban;

import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.teseo.simple.RandomReflexTeseo;
import uniltiranyu.simulate.util.SimpleLanguage;

public class SokobanMain {
    private static SimpleLanguage getLanguage(){
	    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "block", "mark"},
	                                   new String[]{"no_op", "die", "advance", "rotate", "play"}
	                                   );
	  }

	  public static void main( String[] argv ){
	    //  InteractiveAgentProgram p = new InteractiveAgentProgram( getLanguage() );
//	    TeseoSimple p = new TeseoSimple();
	    RandomReflexTeseo p = new RandomReflexTeseo();
	    p.setLanguage(getLanguage());
	    SokobanBoardDrawer.DRAW_AREA_SIZE = 600;
	    SokobanBoardDrawer.CELL_SIZE = 40;
	    Labyrinth.DEFAULT_SIZE = 15;
	    Agent agent = new Agent( p );
	    SokobanMainFrame frame = new SokobanMainFrame( agent, getLanguage() );
	    frame.setVisible(true); 
	  }
	}