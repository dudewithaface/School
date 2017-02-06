import java.util.*;
import java.io.*;

public class Hangman {
	public static void main(String[] args){
		try{
			int[] scores = new int[2];
			while(scores[0] == 0){
				scores = keepPlaying(scores);
			}
			System.out.println("Game Over");
			checkAndPrintHighscore(scores[1]);
		}catch(FileNotFoundException e){
			System.out.println("File not found");
		}catch(NoSuchElementException e){
			System.out.println("Not enough records in highscore.txt");
		}
	}

	public static int[] keepPlaying(int[] score) throws FileNotFoundException{
		Scanner input = new Scanner(System.in);
		String word = getDictionaryWord();
		ArrayList<String> guesses = new ArrayList<String>();
		int guessesLeft = 7;
		while(guessesLeft > 0){
			printGame(word, guesses, guessesLeft, score[1]);
			String tempGuess = input.next().toUpperCase();
			if(hasBeenUsed(tempGuess, guesses))
				System.out.println("You have already guessed that\n");
			else{
				int tempScore = checkIfFound(tempGuess, word);
				guesses.add(tempGuess.substring(0, 1).toUpperCase());
				if(tempScore > 0){
					System.out.println(tempGuess + " was found!\n");
					score[1] += tempScore;
				}else{
					System.out.println("Sorry, there were no " + tempGuess.charAt(0) + "'s\n");
					guessesLeft --;
				}
			}
			if(wordFinished(word, guesses)){
				System.out.println("Good job. The word was " + word + "\n");
				score[1] += 100 + (guessesLeft * 30);
				return score;
			}
		}
		score[0] = 1;
		System.out.println("Sorry, the word was " + word);
		return score;
	}
	
	public static boolean wordFinished(String word, ArrayList<String> guesses){
		int count = 0;
		for(int x = 0; x < word.length(); x ++){
			for(int y = 0; y < guesses.size(); y ++){
				if(word.charAt(x) == guesses.get(y).charAt(0)){
					count ++;
				}
			}
		}
		return (count == word.length());
	}
	
	public static String getDictionaryWord() throws FileNotFoundException{
		int lines = 0;
		File file = new File("dictionary.txt");
		Scanner dictionary = new Scanner(file);
		String word = dictionary.next();
		while(dictionary.hasNext()){
			lines++;
			dictionary.nextLine();
		}
		dictionary.close();
		dictionary = new Scanner(file);
		for(int x = (int)(Math.random() * lines) - 1; x > 0; x --){
			dictionary.nextLine();
		}
		word = dictionary.nextLine().toUpperCase();
		dictionary.close();
		return word;
	}
	
	public static void checkAndPrintHighscore(int score) throws FileNotFoundException, NoSuchElementException{
		File file = new File("highscore.txt");
		Scanner input = new Scanner(System.in);
		Scanner highscore = new Scanner(file);
		int lines = 0;
		String[] old = new String [5];
		for(int x = 0; x < 5; x ++){
			old[x] = highscore.next() + " " + highscore.next();
		}
		int higher = -1;
		for(int x = 0; x < 5; x ++){
			if(score > Integer.parseInt(old[x].substring(old[x].indexOf(" ") + 1))){
				higher = x;
				x = 5;
			}
		}
		if(higher > -1){
			for(int x = 4; x > higher; x --){
				old[x] = old[x - 1];
			}
			System.out.print("Please enter your name: ");
			String name = input.next();
			old[higher] = name + " " + score;
		}
		PrintWriter output = new PrintWriter(file);
		for(int x = 0; x < 5; x ++){
			output.println(old[x]);
			System.out.println(x + ": " + old[x]);
		}
		output.close();
	}
	
	public static boolean hasBeenUsed(String tempGuess, ArrayList<String> guesses){
		boolean found = false;
		for(int x = 0; x < guesses.size(); x ++){
			if(tempGuess.charAt(0) == guesses.get(x).charAt(0))
				found = true;
			x = guesses.size();
		}
		return found;
	}
	
	public static int checkIfFound(String tempGuess, String word) {
		int x = 0;
		for (int y = 0; y < word.length(); y++) {
			if (tempGuess.charAt(0) == word.charAt(y))
				x += 10;
		}
		return x;
	}

	public static void printGame(String word, ArrayList<String> guesses, int guessesLeft, int score) {
		System.out.print("Hidden Word:");
		boolean found = false;
		for (int x = 0; x < word.length(); x++) {
			for (int y = 0; y < guesses.size(); y++) {
				if (word.charAt(x) == guesses.get(y).charAt(0)) {
					found = true;
					y = guesses.size();
				}
			}
			if (found)
				System.out.print(" " + word.charAt(x));
			else
				System.out.print(" _");
			found = false;
		}
		System.out.print("\nIncorrect Guesses:");
		int numberWrong = 0;
		for (int x = 0; x < guesses.size(); x++) {
			boolean inWord = false;
			for(int y = 0; y < word.length(); y ++){
				if(guesses.get(x).charAt(0) == word.charAt(y)){
					inWord = true;
				}
			}
			if(!inWord){
				if(numberWrong > 0){
					System.out.print(",");
				}
				System.out.print(" " + guesses.get(x));
				numberWrong ++;
			}
		}
		System.out.println("\nGuesses Left: " + guessesLeft);
		System.out.println("Score: " + score);
		System.out.print("Enter next guess: ");
	}
}