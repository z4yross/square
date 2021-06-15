/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.squares;

import uniltiranyu.Agent;
import uniltiranyu.examples.games.squares.isi2021.squareAgentProgram.SquaresAgentProgram;

/**
 *
 * @author Jonatan
 */
public class SquaresMain  {
  public static void main( String[] argv ){
    // Reflection
    Agent w_agent =  new Agent( new SquaresAgentProgram(Squares.WHITE)); // blue
    Agent b_agent =  new Agent( new SquaresAgentProgram(Squares.BLACK)); // red
    SquaresMainFrame frame = new SquaresMainFrame( w_agent, b_agent );
    frame.setVisible(true);
  }
}