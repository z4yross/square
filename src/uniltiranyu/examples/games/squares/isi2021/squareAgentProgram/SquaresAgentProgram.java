package uniltiranyu.examples.games.squares.isi2021.squareAgentProgram;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import nsgl.agents.examples.games.squares.Squares;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;


public class SquaresAgentProgram implements AgentProgram{

	private String color;
	private Integer N;

	private Square board[];

	private Integer lstW;
	private Stack<Integer> stack;
	private LinkedList<Integer> weights[];

	protected String lines[] = {Squares.LEFT, Squares.RIGHT, Squares.TOP, Squares.BOTTOM};

	private Integer zones;
	private boolean start;

	public SquaresAgentProgram(String color){
        this.color = color;        
    }

	@Override
	public Action compute(Percept p) {
		if(N == null) {
			N = Integer.parseInt((String) p.get(Squares.SIZE));

			weights = new LinkedList[N * N + 2];

			for(int i = 0; i < weights.length; i++)
				weights[i] = new LinkedList<Integer>();

			board = init(p);
			zones = 0;
		}

		if (p.get(Squares.TURN).equals(color) ){
			zones = 0;
			ArrayList<Integer> changes = scan(p);

			// System.out.println(zones + "\n" + "--------------" + color + "-------------");

			for(Integer i: changes)	backtrack(i);
			countZones(p);

			if(!start && changes.size() > 6) start = true;

			boolean chkIDX = zones % 2 == 0 && weights[3].size() <= 2; 
			int i = chkIDX ? 2 : 0;

			// System.out.println(zones + "\n" + "---------------------------");

			while(true){
				if(i < 0 || i >= weights.length) i = 0;

				if(weights[i].size() != 0){
					Integer idx = weights[i].getFirst();

					int x = idx % N;
					int y = (int) Math.floor(idx / N); 

					String best = board[idx].best();
					if(best == null){
						weights[i--].removeFirst();
						i--;
					}

					else return new Action(y + ":" + x + ":" + best);
				}
				i++;
			}
		}
		return new Action(Squares.PASS);
	}

	private void countZones(Percept p){
		boolean check[] = new boolean[board.length];
		for(int i = 0; i < board.length; i++){
			if(!check[i]){
				check[i] = true;

				Square sqr = board[i];
				if(sqr.conected.size() >= 1){
					for(int j = 0; j < sqr.conected.size(); j++){
						Integer nxt = sqr.conected.get(j);
						check[nxt] = true;
					}

					int x = i % N;
					int y = (int) Math.floor(i / N); 

					boolean isEmpt = p.get(y + ":" + x).equals(Squares.SPACE);
					this.zones += isEmpt && sqr.w > 2 ? 1 : 0;

					// if(isEmpt && sqr.w > 2) {
					// 	System.out.println("i:" + i + " arr: " + sqr.conected + " w: " + sqr.w + " sz: " + sqr.conected.size());
					// }
				}
			}
		}
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
			
			Square sq = board[actual];
			checked[actual] = true;

			if(sq.lns == 2) {
				lstW = ++weight;
				this.stack.push(actual);

				if(!sq.lines[3]) rc.push(sq.b);
				if(!sq.lines[2]) rc.push(sq.t);
				if(!sq.lines[1]) rc.push(sq.r);
				if(!sq.lines[0]) rc.push(sq.l);
			}
		}

		// if(stack.contains(61) || stack.contains(62)) System.out.println(stack);

		ArrayList<Integer> lst = new ArrayList<>(this.stack);
		
		while(!stack.empty()){
			Integer a = stack.pop();

			weights[board[a].w].remove(a);
			
			board[a].w = lstW;
			weights[lstW].addLast(a);

			board[a].conected = new ArrayList<>(lst);
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
