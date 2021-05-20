package uniltiranyu.examples.labyrinth.teseo;
import speco.array.Array;
import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.examples.labyrinth.LabyrinthPercept;

public class TeseoLabyrinth extends Labyrinth {

  protected LabyrinthPercept getPercept( int x, int y ){
    return new TeseoPercept( structure[x][y] );
  }

  public TeseoLabyrinth( Array<Agent> _agents, int[][] _structure ) {
    super( _agents, _structure );
  }

  public TeseoLabyrinth( Agent agent, int[][] _structure ){
    super( agent, _structure );
  }

  public Labyrinth copy(){
    return new TeseoLabyrinth( agents, structure.clone() );
  }

  public boolean edit( int X, int Y ){
    boolean flag = super.edit(X,Y);
    if( !flag ){
      X -= LabyrinthDrawer.MARGIN;
      Y -= LabyrinthDrawer.MARGIN;
      int x = X/LabyrinthDrawer.CELL_SIZE;
      int y = Y/LabyrinthDrawer.CELL_SIZE;
      if( 0<=x && x<LabyrinthDrawer.DIMENSION && 0<=y && y<LabyrinthDrawer.DIMENSION ){
        structure[x][y] ^= (1 << 4);
        flag = true;
      }else{
        flag = false;
      }
    }
    return flag;
  }

}
