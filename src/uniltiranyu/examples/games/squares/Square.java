package uniltiranyu.examples.games.squares;

import uniltiranyu.Percept;

public class Square {

	private static final String PS[] = {Squares.LEFT, Squares.RIGHT, Squares.TOP, Squares.BOTTOM};

	private Integer i;

	protected Integer l;
	protected Integer r;
	protected Integer t;
	protected Integer b;

	private Integer n;

	private Square board[];
	private Percept p;
	
	protected Integer w;
	protected Integer lns;
	
	protected boolean lines[] = {false, false, false, false};
	protected Integer pos;

	public Square(Integer i, Integer l, Integer r, Integer t, Integer b, Square board[], Percept p, int n){
		this.i = i;
		this.l = l;
		this.r = r;
		this.t = t;
		this.b = b;
		this.n = n;
		
		this.board = board;
		this.p = p;
		
		this.w = 0;
		this.lns = 0;
	}

	public boolean compare(boolean pLines[]){
		boolean res = false;
		int w = 0;

		for(int i = 0; i < 4; i++){
			if(pLines[i] != lines[i]){
				lines[i] = pLines[i];
				res = true;
			}

			w += lines[i] ? 1 : 0;
		}

		this.lns = w;

		return res;
	}

	public String best(){
		Integer arr[] = {0, 1, 2, 3};
		
		Integer bstW = board.length + 10000;
		Integer bstI = null;

		for(Integer idx: arr){
			if(!lines[idx]){
				Integer i = 0;

				if(idx == 0) i = l;
				if(idx == 1) i = r;
				if(idx == 2) i = t;
				if(idx == 3) i = b;

				if(board[i].w < bstW){
					bstW = board[i].w;
					bstI = idx;
				}
			}
		}

		return bstI == null ? null: PS[bstI];
	}
	
}
