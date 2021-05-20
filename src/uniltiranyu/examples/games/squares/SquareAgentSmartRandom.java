package uniltiranyu.examples.games.squares;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;

public class SquareAgentSmartRandom implements AgentProgram {
	
	private String color;

	private Integer N;
	private Integer count;

	public SquareAgentSmartRandom(String color){
        this.color = color;        
    }

	@Override
	public Action compute(Percept p) {
		if (p.get(Squares.TURN).equals(color) ){
			count++;
			
		}
		return new Action(Squares.PASS);
	}

	@Override
	public void init() {}
	
}
