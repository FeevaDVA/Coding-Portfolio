package GameOfLife;

public class GameThread implements Runnable {

	private CellFrame game;
	public GameThread(CellFrame g) {
		game = g;
	}
	
	//the thread for running the game will continue to run as long as the game state is true/running
	public void run() {
		while(game.getGameState()) {
			game.start();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
