import java.util.Scanner;

import javax.swing.JFrame;

public class Controller {
	public static void main(String[] args) {
		
		/* 'success' instantiated to false -- becomes true upon success */
		boolean success = false;
		Scanner input = new Scanner(System.in);

		/* creates a new instance of 'puzzle' */
		Puzzle puzzle = new Puzzle();
		
		JFrame window = new JFrame();
		Graphix g = new Graphix(puzzle);
		window.setContentPane(g);
		window.setSize(800, 600);
		window.setVisible(true);
		
		g.drawBoard(new Board(10, 11, 3));

		while (!success) {

			/* print puzzle */
			//puzzle.displayPuzzle();

			/* Print the bag from which to draw from */
			/*System.out.println("Select Operator from current Bag: ");
			puzzle.puzzleBag.printBag();*/

			/* Receive user input for operator */
			//char chosenOperator = input.next().charAt(0);
			char chosenOperator = ' ';
			while(chosenOperator == ' '){
				chosenOperator = g.getCurrSelectedOperator();
			}
			System.out.println("Left the while loop");
			/* Error check */
			if (!puzzle.puzzleBag.checkBag(chosenOperator)) {
				System.err.println("Operator not in bag!");
				continue;
			}

			/* Receive user input for which board */
			System.out.println("Select a board: ");
			int chosenBoard = input.nextInt();

			/* Error check */
			if (chosenBoard < 0 || chosenBoard > 2 || puzzle.subArr[chosenBoard].isFull()) {
				System.err.println("Invalid board!");
				continue;
			}

			/* Receive user input for which location */
			System.out.print("Select a location: ");
			int chosenLocation = input.nextInt();

			/* Error check */
			if (chosenLocation < 0 || chosenLocation >= puzzle.subArr[chosenBoard].blocks.length) {
				System.err.println("Location out of bounds!");
				continue;
			}

			/* Error check */
			if (!puzzle.subArr[chosenBoard].addBlock(chosenOperator, chosenLocation)) {
				continue;
			}

			/* Remove the next instance of that operator */
			puzzle.puzzleBag.removeNextInstance(chosenOperator);

			/* Only check for success when everything is full */
			if (puzzle.isPuzzleFull()) {
				success = puzzle.isCorrect();
				if (!success) {
					puzzle.puzzleBag.resetBag();
					puzzle.resetAllBoards();
					System.out.println("Incorrect answer!");
					System.out.println("-----------------");
				} else {
					/* print boards */
					puzzle.displayPuzzle();
					System.out.println("Puzzle solved!");
				}	
			}
		}
	}
}