package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application{

	//variables
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static int XMAX = SIZE * 12;
	public static int YMAX = SIZE * 24;
	public static int [][] MESH = new int [XMAX/SIZE][YMAX/SIZE];
	private static Pane groupe = new Pane();
	private static Form object;
	private static Scene scene = new Scene(groupe, XMAX + 150, YMAX);
	public static int score = 0;
	public static int top = 0;
	private static boolean game = true;
	private static Form nextObj = Controller.makeRect();
	private static int linesNo = 0;
	
	//create scene and start game
	public void main (String[] args) { launch(args);}
	@Override
	public void start(Stage stage) throws Exception {
		for (int[] a: MESH) {
			Arrays.fill(a,0);
		}
	
		//Creating score and level text
		Line line = new Line(XMAX, 0, XMAX, YMAX);
		Text scoretext = new Text ("Score: ");
		scoretext.setStyle("-fx-font: 20 arials;");
		scoretext.setY(50);
		scoretext.setX(XMAX + 5);
		Text level = new Text("Lines: ");
		level.setStyle("-fx-font: 20 arials;");
		level.setY(100);
		level.setX(XMAX + 5);
		level.setFill(Color.GREEN);
		groupe.getChildren().addAll(scoretext, line, level);
		
		//Creating first block and the stage
		Form a = nextObj;
		groupe.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPressed(a);
		object = a;
		nextObj = Controller.makeRect();
		stage.setScene(scene);
		stage.setTitle("T E T R I S");
		stage.show();
		
		
		//Timer
		Timer fall = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if(object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
							top++;
						else
							top = 0;
						
						if (top == 2) {
							//GAME OVER
							Text over = new Text("GAME OVER");
							over.setFill(Color.RED);
							over.setStyle("-fx-font: 70 arial;");
							over.setY(250);
							over.setX(10);
							groupe.getChildren().add(over);
							game = false;
							
						}
						
						//Exit
						if (top == 15) {
							System.exit(0);
						}
						
						if (game) {
							MoveDown(object);
							scoretext.setText("Score: " + Integer.toString(score));
							level.setText("Lines: " + Integer.toString(linesNo));
							
						}
						
					}
					
				});
				
			}
			
		};
		fall.schedule(task,  0, 300);
	
	}
	
	private void moveOnKeyPressed (Form form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle (KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					Controller.MoveRight(form);
					break;
				case DOWN:
					MoveDown(form);
					score++;
					break;
				case LEFT:
					Controller.MoveLeft(form);
					break;
				case UP:
					MoveTurn(form);
					break;
				
				
				}
			}
		});
		
	}
	
	private void MoveTurn(Form form) {
		int f = form.form;
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		switch (form.getName()) {
		case "j":
			if (f == 1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			break;
		}
	}
	
	private void RemoveRows(Pane pane) {
		ArrayList<Node> rects = new ArrayList<Node>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		ArrayList<Node> newrects = new ArrayList<Node>();
		int full = 0;
		//check which line is full
		for (int i = 0; i < MESH[0].length; i++) {
			for (int j = 0; i < MESH.length; j++) {
				if(MESH[j][i] == 1)
					full++;
			}
			
			if (full == MESH.length)
				lines.add(i+lines.size());
			full = 0;
			
		}
		
		//Deleting the row
		if(lines.size() > 0)
			do {
				for ( Node node: pane.getChildren()) {
					if (node instanceof Rectangle)
						rects.add(node);
				}
				score+=50;
				linesNo++;
				
			//deleting block on row
			for(Node node: rects) {
				Rectangle a = (Rectangle)node;
				if(a.getY() == lines.get(0)*SIZE) {
					MESH[(int)a.getX()/SIZE][(int)a.getY()/SIZE] = 0;
					pane.getChildren().remove(node);
					
				}	
				else
					newrects.add(node);
			}
				for (Node node: newrects) {
					Rectangle a = (Rectangle)node;
					if (a.getY() < lines.get(0)*SIZE) {
						MESH[(int)a.getX()/SIZE][(int)a.getY()/SIZE] = 0;
						a.setY(a.getY() + SIZE);
					}
					lines.remove(0);
					rects.clear();
					newrects.clear();
					
					for (Node node1: pane.getChildren()) {
						if (node1 instanceof Rectangle)
							rects.add(node1);
					}
					
					for (Node node1: rects) {
						Rectangle a1 = (Rectangle)node1;
						try {
							MESH[(int)a1.getX()/SIZE][(int)a1.getY()/SIZE]=1;
						} catch (ArrayIndexOutOfBoundsException e) {
							
						}
					}
					rects.clear();
				}
				}while(lines.size() > 0);
			}
			
	private void MoveDown(Rectangle rect) {
		if(rect.getY() + MOVE < YMAX)
			rect.setY(rect.getY() + MOVE);
	}
	
	private void MoveRight(Rectangle rect) {
		if(rect.getY() + MOVE < XMAX - SIZE)
			rect.setX(rect.getX() + MOVE);
	}
	
	private void MoveLeft(Rectangle rect) {
		if(rect.getX() - MOVE >= 0)
			rect.setX(rect.getY() - MOVE);
	}
	
	private void MoveUp(Rectangle rect) {
		if(rect.getY() - MOVE > 0)
			rect.setY(rect.getY() - MOVE);
	}
	
	public void MoveDown (Form form) {
		//moving if down is full
		if(form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE || form.d.getY() == YMAX - SIZE || 
				moveA(form) || moveB(form) || moveC(form) || moveD(form)) {
			
			MESH[(int)form.a.getX()/SIZE][(int)form.a.getY()/SIZE] = 1;
			MESH[(int)form.b.getX()/SIZE][(int)form.b.getY()/SIZE] = 1;
			MESH[(int)form.c.getX()/SIZE][(int)form.c.getY()/SIZE] = 1;
			MESH[(int)form.d.getX()/SIZE][(int)form.d.getY()/SIZE] = 1;
			RemoveRows(groupe);
			
			// creating a new block and adding to the scene
			
			Form a = nextObj;
			nextObj = Controller.makeRect();
			object = a;
			groupe.getChildren().addAll(a.a,a.b,a.c,a.d);
			moveOnKeyPressed(a);
			
			
		}
		
		//Moving one block down if down is not full
		if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX && form.d.getY() + MOVE < YMAX) {
			int movea = MESH[(int)form.a.getX()/SIZE][((int)form.a.getY()/SIZE) + 1];
			int moveb = MESH[(int)form.a.getX()/SIZE][((int)form.a.getY()/SIZE) + 1];
			int movec = MESH[(int)form.a.getX()/SIZE][((int)form.a.getY()/SIZE) + 1];
			int moved = MESH[(int)form.a.getX()/SIZE][((int)form.a.getY()/SIZE) + 1];
			
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				form.a.setY(form.a.getY() + MOVE);
				form.b.setY(form.b.getY() + MOVE);
				form.c.setY(form.c.getY() + MOVE);
				form.d.setY(form.d.getY() + MOVE);
			}
				
		}	
		
	}
	
	private boolean moveA(Form form) {
		return(MESH[(int)form.a.getY()/SIZE][((int)form.a.getY()/SIZE)+1] == 1);
	}
	
	private boolean moveB(Form form) {
		return(MESH[(int)form.b.getY()/SIZE][((int)form.b.getY()/SIZE)+1] == 1);
	}
	
	private boolean moveC(Form form) {
		return(MESH[(int)form.c.getY()/SIZE][((int)form.c.getY()/SIZE)+1] == 1);
	}
	
	private boolean moveD(Form form) {
		return(MESH[(int)form.d.getY()/SIZE][((int)form.d.getY()/SIZE)+1] == 1);
	}
	
	
	private boolean cB(Rectangle rect, int x, int y) {
		boolean yb = false;
		boolean xb = false;
		if ( x >= 0)
			xb = rect.getX() + x*MOVE <= XMAX - SIZE;
		if (x < 0)
			xb = rect.getX() + x*MOVE >= 0;
		if (y >= 0)
			yb = rect.getY() + y*MOVE > 0;
		if (y < 0)
			yb = rect.getY() + y*MOVE < YMAX;
		return xb && yb && MESH[((int)rect.getX()/SIZE)+x][((int)rect.getY()/SIZE)-y] == 0;
		
			
	}
	
}