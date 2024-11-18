package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

public class Main extends Application {
	final static int NUM_CARDS = 4;
	final static int DECK_SIZE = 52;
	
	static double total_money_won;
	static double won_this_spin;
	static double total_money_spent;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();
			root.setAlignment(Pos.CENTER);
			root.setSpacing(12);
			
			total_money_won = 0;
			
			// Startup cards
			HBox showing_cards = new HBox(new ImageView(new Image("file:assets/13.png")),
						new ImageView(new Image("file:assets/13.png")),
						new ImageView(new Image("file:assets/13.png")),
						new ImageView(new Image("file:assets/13.png")));
			showing_cards.setAlignment(Pos.CENTER);
			showing_cards.setSpacing(10);
			
			// Amount inserted
			TextField inserted = new TextField();
			HBox amount_inserted = new HBox(new Text("Amount Inserted: $  "),
											inserted);
			amount_inserted.setAlignment(Pos.CENTER);
			
			
			// Amount won this spin
			Text this_won = new Text("0.00");
			HBox amount_won_this = new HBox(new Text("Amount Won This Spin: $  "),
											this_won);
			amount_won_this.setAlignment(Pos.CENTER);
			
			
			// Total won
			Text total_won = new Text("0.00");
			HBox amount_won_total = new HBox(new Text("Total Amount Won: $  "),
											total_won);
			amount_won_total.setAlignment(Pos.CENTER);
			
			// Total spent
			Text total_spent = new Text("0.00");
			HBox amount_spent_total = new HBox(new Text("So Far You've Spent: $  "),
					total_spent);
			amount_spent_total.setAlignment(Pos.CENTER);
			
			// Instructions
			Text instructions = new Text("Insert an amount to play.");
			
			// Spin button
			Button spin = new Button("Spin");
			spin.setOnAction(e -> {
				String user_input = inserted.getText();
				double spent_on_this_spin =  0;
				// Tries to parse the user's input but does nothing if it is un-parsable
				try {
					spent_on_this_spin =  Double.parseDouble(user_input);
				} catch(Exception e1) {
					instructions.setText("Error. Try a different amount.");
					return;
				}
				total_money_spent += spent_on_this_spin;
				won_this_spin = spent_on_this_spin;
				showing_cards.getChildren().clear();
				showing_cards.getChildren().addAll(getCards());
				this_won.setText(String.format("%.2f", won_this_spin));
				total_won.setText(String.format("%.2f", total_money_won));
				total_spent.setText(String.format("%.2f", total_money_spent));
				if((int)(won_this_spin) == 0) {
					instructions.setText("No luck. Play again!");
				} else if((int)(spent_on_this_spin * 2) == (int)(won_this_spin)){
					instructions.setText("Sweet! DOUBLE WIN x 2!!");
				} else if((int)(spent_on_this_spin * 3) == (int)(won_this_spin)) {
					instructions.setText("Sweet! Triple WIN x 3!!");
				} else {
					instructions.setText("Jackpot! Quadruple WIN x 4!!");
				}
			});
			
			// Add all to root
			root.getChildren().addAll(showing_cards, 
					amount_inserted, 
					amount_won_this,
					amount_won_total,
					amount_spent_total,
					spin, 
					instructions);
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ImageView [] getCards() {
		// Declares needed variables for images
		String card_path;
		Image card;
		ImageView [] card_view = new ImageView[NUM_CARDS];
		
		
		// Declares needed variables for counting pairs
		int num_pairs = 0;
		ArrayList<Integer> card_indexes = new ArrayList<Integer>();
		
		// Loop to generated needed number of cards
		for(int i = 0; i < NUM_CARDS; i++) {
			// Get random number between 1 and deck size
			int rand_index = (int)(Math.random() * DECK_SIZE + 1);
			
			// Generates a random card to show
			card_path = "file:assets/" + rand_index + ".png";
			card = new Image(card_path);
			card_view[i] = new ImageView(card);
			
			// Check for pairs
			if(card_indexes.contains(rand_index)) {
				num_pairs = (num_pairs == 0) ? (num_pairs+2) : (num_pairs+1);
			}
			card_indexes.add(rand_index);
		}
		
		won_this_spin = won_this_spin * num_pairs;
		total_money_won += won_this_spin;
		
		return card_view;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
