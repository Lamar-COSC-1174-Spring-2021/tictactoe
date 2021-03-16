


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;
public class TicTacToe extends Application{
	
	private boolean playable = true;
	private boolean turnX = true;
	private Tile[][] board = new Tile[3][3];
	
	private List<Combo> combos = new ArrayList<>();
	
	private Pane root = new Pane();
	
	private Parent createContent(){
		root.setPrefSize(600, 600);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Tile tile = new Tile();
				tile.setTranslateX(j * 200);
				tile.setTranslateY(i * 200);
				
				root.getChildren().add(tile);
				
				board[j][i] = tile;
			}
		}
		//horizontal
		for(int y = 0; y < 3; y++){
			combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
		}
		
		//vertical
		for(int x = 0; x < 3; x++){
			combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
		}
		//diagonal
		combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
		combos.add(new Combo(board[2][0], board[1][1], board[0][2]));
		
		return root;
	}
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
	}
	
	private void checkState(){
		for(Combo combo : combos){
			if(combo.isComplete()){
				playable = false;
				playWinAnimation(combo);
				break;
			}
		}
	}
	
	private void playWinAnimation(Combo combo){
		Line line = new Line();
		line.setStartX(combo.tiles[0].getCenterX());
		line.setStartY(combo.tiles[0].getCenterY());
		line.setEndX(combo.tiles[0].getCenterX());
		line.setEndY(combo.tiles[0].getCenterY());
		
		root.getChildren().add(line);
		
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), 
				new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
				new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
		timeline.play();
		 
		timeline.setOnFinished(e -> {
	            root.getChildren().remove(line);
	            startNewGame();
	        });
	}
	   private void startNewGame() {
	        playable = true;
	        turnX = true;
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                board[j][i].text.setText("");
	            }
	        }
	    }
		    
		    
	   
	   private class Combo {
		private Tile[] tiles;
		public Combo(Tile...tiles){
			this.tiles = tiles;
		}
		
		public boolean isComplete(){
			if(tiles[0].getValue().isEmpty())
				return false;
			
			return tiles[0].getValue().equals(tiles[1].getValue()) && 
					tiles[0].getValue().equals(tiles[2].getValue());
		}
	}
	
	private class Tile extends StackPane {
		private Text text = new Text();
		
		public Tile() {
			Rectangle border = new Rectangle(200, 200);
			border.setFill(null);
			border.setStroke(Color.BLACK);
			
			text.setFont(Font.font(72));
			
			setAlignment(Pos.CENTER);
			getChildren().addAll(border, text);
			
			setOnMouseClicked(event -> { 
				if (!playable)
                    return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;

                    drawX();
                    turnX = false;
                    checkState();
                   /* VBox displayTrashTalkView = new VBox(20);
                    displayTrashTalkView.setPrefSize(600, 600);
                    Label TrashTalkLabel = new Label();
                    TrashTalkLabel.setText("Bleep bloop...malfunction...malfunction");
                    TrashTalkLabel.setFont(new Font("Helvetica Light", 24));
                    displayTrashTalkView.getChildren().add(TrashTalkLabel);
                    root.getChildren().add(displayTrashTalkView);*/
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;

                    drawO();
                    turnX = true;
                    checkState();
                    /*VBox displayTrashTalkView = new VBox(20);
                    displayTrashTalkView.setPrefSize(600, 600);
                    Label TrashTalkLabel = new Label();
                    TrashTalkLabel.setText("I'm sorry, would you like a tissue?");
                    TrashTalkLabel.setFont(new Font("Helvetica Light", 24));
                    displayTrashTalkView.getChildren().addAll(TrashTalkLabel);
                    root.getChildren().add(displayTrashTalkView);*/
                }
            });
        }		
		
		public double getCenterX(){
			return getTranslateX() +100;
		}
		
		public double getCenterY(){
			return getTranslateY() +100;
		}
		
		public String getValue(){
			return text.getText();
		}
		
		private void drawX(){
			text.setText("X");
		}
		
		private void drawO(){
			text.setText("O");
		}
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
