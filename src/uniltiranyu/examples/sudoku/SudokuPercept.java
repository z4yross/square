package uniltiranyu.examples.sudoku;
import java.awt.Graphics;

import uniltiranyu.Percept;
import uniltiranyu.simulate.util.SimpleLanguage;

public class SudokuPercept extends Percept{
  public SudokuPercept( Board board, SudokuLanguage language ) {
    set( SudokuLanguage.SOLVED, new Boolean( board.solved() ) );
    int DIGITS = SudokuLanguage.DIGITS;
    int k = 0;
    for( int i=0; i<DIGITS; i++ ){
        for( int j=0; j<DIGITS; j++ ){
            set(language.getPercept(k),
                         new Integer( board.get(i, j) ) );
            k++;
        }
    }
  }

  public void draw( Graphics g, int x, int y, int CELL_SIZE, SimpleLanguage language ){
    if( ((Boolean)get(language.getPercept(0))).booleanValue() ){ g.drawLine(x,y,x+CELL_SIZE,y); }
    if( ((Boolean)get(language.getPercept(1))).booleanValue() ){ g.drawLine(x+CELL_SIZE,y,x+CELL_SIZE,y+CELL_SIZE); }
    if( ((Boolean)get(language.getPercept(2))).booleanValue() ){ g.drawLine(x,y+CELL_SIZE,x+CELL_SIZE,y+CELL_SIZE); }
    if( ((Boolean)get(language.getPercept(3))).booleanValue() ){ g.drawLine(x,y,x,y+CELL_SIZE); }
  }
}
