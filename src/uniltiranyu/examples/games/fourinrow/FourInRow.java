/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.fourinrow;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.Agent;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.Clock;
import uniltiranyu.simulate.Environment;
import uniltiranyu.simulate.gui.EnvironmentView;

/**
 *
 * @author Jonatan
 */
public class FourInRow extends Environment{
    public static String PASS = "PASS";
    public static String TURN = "play";
    public static String TIME = "time";
    public static String WHITE = "white";
    public static String BLACK = "black";
    public static String SPACE = "space";
    public static String SIZE = "size";
    protected Board board = null;
    protected Clock clock;
    
    protected static Array<Agent> init( Agent white, Agent black ){
        Array<Agent> a = new Array<Agent>();
        a.add(white);
        a.add(black);
        return a;
    }
    
    public FourInRow( Agent white, Agent black ){
        super( init( white, black ) );
    }
    

    public void init(Board b, Clock c){
        clock = c;
        board = b;
    }

    @Override
    public Percept sense(Agent agent) {
        return new FourInRowPercept(board, clock);
    }

    @Override
    public boolean act(Agent agent, Action action){
        int w = board.check();
        if(board.full() ||  w != 0 ){
            agent(0).die();
            agent(1).die();            
            if( w > 0 ){
               updateViews(EnvironmentView.DONE + ": White wins");
            }else{
               if( w < 0 ){
                  updateViews(EnvironmentView.DONE + ": Black wins");
               }else{
                  updateViews(EnvironmentView.DONE + ": Draw");
               }
            }
        }        
        
        if(clock.white_turn()){
            if( agent != agent(0)){
                updateViews("Working");
                return false;                
            }
            if(clock.white_time() <= 0 ){
                agent(0).die();
                agent(1).die();            
                updateViews(EnvironmentView.DONE + ": Black wins");
            }
        }else{
            if( agent != agent(1)){
                updateViews("Working");
                return false;                
            }
            if(clock.black_time() <= 0 ){
                agent(0).die();
                agent(1).die();            
                updateViews(EnvironmentView.DONE + ": White wins");
            }
        }
        String[] code = action.getCode().split(":");
        int i = Integer.parseInt(code[0]);
        int j = Integer.parseInt(code[1]);
        if( code[2].equals(WHITE) ){
            if( clock.white_turn()  && board.play(i, j, 1)){
                clock.swap();
                updateViews("Working");
                return true;
            }
            clock.swap();
            updateViews("Working");
            return false;
        }else{
            if( !clock.white_turn()  && board.play(i, j, -1)){
                clock.swap();
                updateViews("Working");
                return true;
            }
            clock.swap();
            updateViews("Working");
            return false;
        }
    }

    @Override
    public void init(Agent agent) {
    }

    @Override
    public Array<Action> actions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
