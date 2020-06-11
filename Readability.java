import java.util.Scanner;
import java.io.File;
public class Readability {
	String text;
	double words;
	double sentences;
	double characters;
	double syllables;
	double polly;
	
	//Getters and Setters
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getWords() {
		return words;
	}
	public void setWords(double words) {
		this.words = words;
	}
	public double getSentences() {
		return sentences;
	}
	public void setSentences(double sentences) {
		this.sentences = sentences;
	}
	public double getCharacters() {
		return characters;
	}
	public void setCharacters(double characters) {
		this.characters = characters;
	}
	public double getSyllables() {
		return syllables;
	}
	public void setSyllables(double syllables) {
		this.syllables = syllables;
	}
	public double getPolly() {
		return polly;
	}
	public void setPolly(double polly) {
		this.polly = polly;
	}
	
	//Constructor
	public Readability(String text) {
		this.text = text;
	}
	//Instance Methods
	public double ari() {
		double charWords = this.getCharacters()/this.getWords();
		double wordSen = this.getWords()/this.getSentences();
		return 4.71 * charWords + 0.5 * wordSen - 21.43;
	}
	public double fk() { 
		double wordSen = this.getWords()/this.getSentences(); 
		double sylWord = this.getSyllables()/this.getWords();
		return (0.39*wordSen) + (11.8 * sylWord) - 15.59;

	}
	public double smog() { 
		double rad = Math.pow(this.getPolly() * (30/this.getSentences()), 0.5);
		return (rad * 1.043) + 3.1291;
	}
	public double cl() {
		double l = (this.getCharacters()/this.getWords()) *100;
		double s = (this.getSentences()/this.getWords()) *100;
		
		return (.0588*l) - (0.296*s) - 15.8;
	}
	//Static Method
	public static int age(double score) {
		score = Math.floor(score);
		if(score == 1) {
			return 6;
		}
		if(score == 2) {
			return 7;
		}
		if(score == 3) {
			return 9;
		}
		if(score == 4) {
			return 10;
		}
		if(score == 5) {
			return 11;
		}
		if(score == 6) {
			return 12;
		}
		if(score == 7) {
			return 13;
		}
		if(score == 8) {
			return 14;
		}
		if(score == 9) {
			return 15;
		}
		if(score == 10) {
			return 16;
		}
		if(score == 11) {
			return 17;
		}
		if(score == 12) {
			return 18;
		}
		if(score == 13) {
			return 24;
		}
		else {
			return 25;
		}
	}
	
	public static void main(String[] args) {
		//Read a file in from command line arguments
		File file = new File(args[0]);
		try {
			Scanner scan = new Scanner(file);
			String input = "";
			String temp = "";
			while (scan.hasNext()) {
				temp = scan.nextLine();
				input += temp;	
			}
			//Logic to get words, sentences and characters
			Readability record = new Readability(input);
		    record.text = input;
		    String [] sentences =record.getText().split("[?!.]");
		    record.sentences = sentences.length;
		    String ttext = record.getText().replaceAll("(?<=\\d),(?=\\d)", "");
			String [] words = ttext.split(("([\\W\\s]+)"));
			record.words = words.length;
			String str = "";
			String[] a = record.getText().split(" ");
			for (int i = 0; i < a.length; i++) {
				str += a[i];
			}
			record.characters = str.length();
			//Logic to get syllables and polysyllables
			int syllables = 0;
			int poly = 0;
			for (int i = 0; i < words.length; i++) {
				int vowels = 0;
				if(words[i].charAt(words[i].length()-1) == 'e') { 
		        	words[i] = words[i].substring(0,words[i].length()-1);
		        }
				boolean[] isVowel = new boolean[words[i].length()];
				for (int j = 0; j < isVowel.length; j++) {
					if(words[i].substring(j,j+1).matches("[AEIOUaeiou]")) {
						isVowel[j] = true;
					}
					
				}
				for (int j = 0; j < isVowel.length; j++) {
					if(j < isVowel.length-1) {
						if(isVowel[j] && isVowel[j+1]) {
							isVowel[j+1] = false;
						}
					}
					if(isVowel[j]) {
						vowels++;
					}
				}
				if (vowels == 0) {
					vowels =1;
				}
				syllables += vowels;
				if(vowels > 2) {
					poly++;
				}
				
			}
			record.polly = poly;
			record.syllables = syllables;
			
			
			
			//Dialogue and decisions:
			Scanner sc = new Scanner(System.in);
			System.out.println("the text is: ");
			System.out.println(record.getText());
			System.out.println();
			System.out.println("Words: " + record.getWords());
			System.out.println("Sentences: " + record.getSentences());
			System.out.println("Characters: " + record.getCharacters());
			System.out.println("Syllables: " + record.getSyllables());
			System.out.println("Polysyllables: " + record.getPolly());
			System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
			String choice = sc.next();
			System.out.println();
			if(choice.equals("ARI")) {
				System.out.println("Automated Readability Index: " + record.ari()
				+ " (about " + age(record.ari()) + " year olds)." );
			}
			if(choice.equals("FK")) {
				System.out.println("Flesch–Kincaid readability tests: " + record.fk()
				+ " (about " + age(record.fk()) + " year olds)." );
			}
			else if(choice.equals("SMOG")) {
				System.out.println("Simple Measure of Gobbledygook: " + record.smog()
				+ " (about " + age(record.smog()) + " year olds)." );
			}
			else if(choice.equals("CL")) {
				System.out.println("Coleman–Liau index: " + record.cl()
				+ " (about " + age(record.cl()) + " year olds)." );
			}
			else {
				System.out.println("Automated Readability Index: " + record.ari()
				+ " (about " + age(record.ari()) + " year olds)." );
				
				System.out.println("Flesch–Kincaid readability tests: " + record.fk()
				+ " (about " + age(record.fk()) + " year olds)." );
				
				System.out.println("Simple Measure of Gobbledygook: " + record.smog()
				+ " (about " + age(record.smog()) + " year olds)." );
				
				System.out.println("Coleman–Liau index: " + record.cl()
				+ " (about " + age(record.cl()) + " year olds)." );
				
				double avg = age(record.ari()) + age(record.fk()) + age(record.smog()) +age(record.cl());
				avg = avg/4;
				
				System.out.println("This text should be understood in average by "+ avg + " year olds.");
			}
			
			
			
			
		}
		catch(Exception e) {
			
		}
	}
}

