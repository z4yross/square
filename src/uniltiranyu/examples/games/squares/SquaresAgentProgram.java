package uniltiranyu.examples.games.squares;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;


import speco.array.Array;

public class SquaresAgentProgram implements AgentProgram{

	private String color;
	private Integer N;

	private Square board[];

	private Integer lstW;
	private Stack<Integer> stack;
	private LinkedList<Integer> weights[];

	private Integer count;
	private boolean state;

	protected String lines[] = {Squares.LEFT, Squares.RIGHT, Squares.TOP, Squares.BOTTOM};

	public SquaresAgentProgram(String color){
        this.color = color;        
    }

	@Override
	public Action compute(Percept p) {
		if(N == null) {
			N = Integer.parseInt((String) p.get(Squares.SIZE));

			weights = new LinkedList[N * N + 1];

			for(int i = 0; i < weights.length; i++)
				weights[i] = new LinkedList<Integer>();

			board = init(p);

			state = true;
			count = -1;
		}

		if (p.get(Squares.TURN).equals(color) ){
			// count++;
			// if(!state){
			// 	state = count > N * N * 0.15;
				
			// 	while(true){
			// 		int i = (int) (Math.random() * N);
			// 		int j = (int) (Math.random() * N);
					
			// 		// int sum = 0;

			// 		// sum += (p.get(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.TRUE) ? 1 : 0;
			// 		// sum += (p.get(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.TRUE) ? 1 : 0;
			// 		// // if (sum >= 2) continue;
			// 		// sum += (p.get(i + ":" + j + ":" + Squares.TOP)).equals(Squares.TRUE) ? 1 : 0;
			// 		// // if (sum >= 2) continue;
			// 		// sum += (p.get(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.TRUE) ? 1 : 0;
			// 		// if (sum >= 3) continue;

			// 		// System.out.println("action: " + count + " " + i + " : " + j + " " + color);
					
			// 		// System.out.println(i + ":" + j + ":" + Squares.LEFT);
			// 		boolean can = (p.get(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.FALSE);
			// 		if(can) return new Action(i + ":" + j + ":" + Squares.LEFT);
					
			// 		// System.out.println(i + ":" + j + ":" + Squares.RIGHT);
			// 		can = (p.get(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.FALSE);
			// 		if(can) return new Action(i + ":" + j + ":" + Squares.RIGHT);

			// 		// System.out.println(i + ":" + j + ":" + Squares.TOP);
			// 		can = (p.get(i + ":" + j + ":" + Squares.TOP)).equals(Squares.FALSE);
			// 		if(can) return new Action(i + ":" + j + ":" + Squares.TOP);

			// 		// System.out.println(i + ":" + j + ":" + Squares.BOTTOM);
			// 		can = (p.get(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.FALSE);
			// 		if(can) return new Action(i + ":" + j + ":" + Squares.BOTTOM);
			// 	}
			// }

			// System.out.println("out");


			ArrayList<Integer> changes = scan(p);

			// System.out.println(changes);

			for(Integer i: changes)	backtrack(i);

			int i = 0;
			// int j = 0;
			// for(LinkedList<Integer> lL: weights){
			// 	System.out.println(j + " " + lL);
			// 	j++;
			// }

			while(true){
				if(i < 0 || i >= weights.length) i = 0;

				if(weights[i].size() != 0){
					Integer idx = weights[i].getFirst();

					int x = idx % N;
					int y = (int) Math.floor(idx / N); 

					// System.out.println("action: " + y + " : " + x + " : " + board[idx].best() + " " + weights[i] + "" + color);

					String best = board[idx].best();
					if(best == null){
						weights[i--].removeFirst();
						i--;
					}
					// if (((String) p.get(y + ":" + x + ":" + best)).equals(Squares.FALSE)) ;
					// else weights[i].removeFirst();
					else return new Action(y + ":" + x + ":" + best);
					
					

					// return new Action(1 + ":" + 2 + ":" + Squares.TOP);


					// return dummyAction(p);
				}
				i++;
			}
		}
		return new Action(Squares.PASS);
	}

	private void backtrack(Integer i) {
		this.lstW = 0;
		this.stack = new Stack<>();

		Integer weight = board[i].lns;
		Stack<Integer> rc = new Stack<>();
		boolean checked[] = new boolean[N * N];

		rc.push(i);

		while(!rc.empty()){
			Integer actual = rc.pop();
			if(actual == null || checked[actual]) continue;

			checked[actual] = true;

			Square sq = board[actual];
			if(sq.lns == 2) {
				lstW = ++weight;
				this.stack.push(actual);
	
				// System.out.println("Backtrack: " + i + " lns " + sq.lns + " " + sq.l + " " + sq.r + " " + sq.t + " " + sq.b);
				if(!sq.lines[3]) rc.push(sq.b);
				if(!sq.lines[2]) rc.push(sq.t);
				if(!sq.lines[1]) rc.push(sq.r);
				if(!sq.lines[0]) rc.push(sq.l);
			}
		}

		while(!stack.empty()){
			Integer a = stack.pop();


			weights[board[a].w].remove(a);
			// System.out.print("w: " + board[a].w + " ");
			board[a].w = lstW;
			weights[lstW].addLast(a);
			// System.out.println("analizando: " + i + " con peso " + lstW + " el elemento " + a + " el arreglo " + weights[lstW] + " " + color);

			// board[a].pos = weights[lstW].size() - 1;
		}
	}

	private ArrayList<Integer> scan(Percept p){
		ArrayList<Integer> change = new ArrayList<>();

		for(int i = 0; i < N * N; i++){			
			int x = i % N;
			int y = (int) Math.floor(i / N); 

			boolean pLines[] = {
				((String) p.get(y + ":" + x + ":" + Squares.LEFT)).equals(Squares.TRUE),
				((String) p.get(y + ":" + x + ":" + Squares.RIGHT)).equals(Squares.TRUE),
				((String) p.get(y + ":" + x + ":" + Squares.TOP)).equals(Squares.TRUE),
				((String) p.get(y + ":" + x + ":" + Squares.BOTTOM)).equals(Squares.TRUE),
			};

			boolean compRes = board[i].compare(pLines);

			if(compRes) change.add(i);
		}

		return change;
	}

	private Square[] init(Percept p){
		board = new Square[N * N];

		for(int i = 0; i < N * N; i++){
			int l = i - 1;
			int r = i + 1;
			int t = i - N;
			int b = i + N;

			Integer isL = (l >= 0 && l < N * N) ? l : null; 
			Integer isR = (r >= 0 && r < N * N) ? r : null; 
			Integer isT = (t >= 0 && t < N * N) ? t : null; 
			Integer isB = (b >= 0 && b < N * N) ? b : null; 

			if(i % N == 0) isL = null;
			if(i % N == N - 1) isR = null;

			board[i] = new Square(i, isL ,isR, isT, isB, board, p, N);


			int x = i % N;
			int y = (int) Math.floor(i / N); 

			boolean pLines[] = {
				((String) p.get(y + ":" + x + ":" + Squares.LEFT)).equals(Squares.TRUE),
				((String) p.get(y + ":" + x + ":" + Squares.RIGHT)).equals(Squares.TRUE),
				((String) p.get(y + ":" + x + ":" + Squares.TOP)).equals(Squares.TRUE),
				((String) p.get(y + ":" + x + ":" + Squares.BOTTOM)).equals(Squares.TRUE),
			};

			int w = 0;

			for(boolean bool: pLines) w += bool ? 1: 0;

			weights[w].addLast(i);		
			board[i].w = w;
		}

		return board;
	}

	@Override
	public void init() {}
	
}
