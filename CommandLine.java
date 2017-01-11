package yeqing_CSCI201L_Assignment1;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import java.util.InputMismatchException;

public class CommandLine {
	private static int NumOfTeams;
	private static Vector <String>TeamList = new Vector<String>();
	private static Vector<Integer>PlayOrder = new Vector<Integer>();
	private static Vector<String>Scores = new Vector<String>();
	private static Set<String> chosen = new HashSet<String>();
	private static Vector<String> bet = new Vector<String>();
	//These are from the input
	private static Vector<String>ContentList = new Vector<String>();
	private static Vector<String>Categories = new Vector<String>();
	private static Vector<String>Credits =  new Vector<String>();
	private static Vector<String>Questions = new Vector<String>();
	private static Vector<String>CategoryA = new Vector<String>();
	private static Vector<String>CategoryB = new Vector<String>();
	private static Vector<String>CategoryC = new Vector<String>();
	private static Vector<String>CategoryD = new Vector<String>();
	private static Vector<String>CategoryE = new Vector<String>();
	private static Vector<String>FinalQuestion = new Vector<String>();
	//This function will report the current situation
	public static void report(){
		System.out.println("Here are the updated scores:");
		for(int i=0; i<NumOfTeams; i++){
			System.out.println(Scores.get(i*2) + " : " + Scores.get(i*2+1));
		}
	}
	//This function will load the file
	public static void readFile(String address) throws FileNotFoundException {
		String line = null;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(address));
			while((line = br.readLine()) != null) {
				//System.out.println(line);
				ContentList.add(line);
			}
			//System.out.println("The size of ContentList is "+ContentList.size());
			for(int i=0; i<ContentList.size(); i++){
				//System.out.println(ContentList.get(i));	
			}
			String phrase = ContentList.get(0);
			String[] tokens = phrase.split("::");	
			for(int i=0; i<tokens.length; i++){
				Categories.add(tokens[i]);
			}
			boolean check = false;
			for(int i=0; i<Categories.size(); i++){
				String temp = Categories.get(i);
				for(int j=i+1; j<Categories.size(); j++){
					String temp2 = Categories.get(j);
					if(temp.compareTo(temp2)==0){
						check = true;
					}
				}
			}
			if(Categories.size()!=5){
				System.out.println("The number of categories is not correct. Ending the program.") ;
				System.exit(0);
			}
			if(check){
				System.out.println("There is repeatation in the categories. Ending the program.");
				System.exit(0);
			}
			CategoryA.add(Categories.get(0));
			CategoryB.add(Categories.get(1));
			CategoryC.add(Categories.get(2));
			CategoryD.add(Categories.get(3));
			CategoryE.add(Categories.get(4));
			
		//This part is for points for each questions
			String points = ContentList.get(1);
			String[] tokensForPoint = points.split("::");
			
			for(int i=0; i<tokensForPoint.length; i++){
				Credits.add(tokensForPoint[i]);
			}
			if(Credits.size()!=5){
				System.out.println("The number of points creditted to questions is not correct. Ending the program.");
				System.exit(0);
			}
			boolean check2 = false;
			for(int i=0; i<Credits.size(); i++){
				String temp = Credits.get(i);
				for(int j=i+1; j<Credits.size(); j++){
					String temp2 = Credits.get(j);
					if(temp.compareTo(temp2)==0){
						check2 = true;
					}
				}
			}
			if(check2){
				System.out.println("There is repeatation in the points creditted to questions. Ending the program.");
				System.exit(0);
			}
			String questions;
			int j=2;
			//System.out.println("Size of contentlist"+ContentList.size());
			while(j<ContentList.size()) {
				questions = ContentList.get(j);
				if(!(questions.startsWith("::"))){
					if(j<3){
						System.out.println("The format is not correct. Ending the program.");
						System.exit(0);
					}
					Questions.setElementAt(Questions.get(j-3)+questions, j-3);	
				}else{//there is only one line
					Questions.add(questions);
				}
				//System.out.println("Now the j is"+j);
				j++;
			}
			if(Questions.size()!=26){
				System.out.println(Questions.size()+"The total number of questions is wrong. Ending the program.");
				System.exit(0);
			}
			for(int i=0; i<26; i++){
				//System.out.println(Questions.get(i));
			}
			//token3[1] is the type of the question
			//token3[2] is the credit
			//token3[3] is the question
			//token3[4] is the answer
			boolean check4 = false;
			for(int i = 0; i <26; i++){
				String thisLine = Questions.get(i);
				//System.out.println(thisLine);
				String[] token3 = thisLine.split("::");
				

				int chec = token3.length;
				if(chec != 4 && chec !=5 ){
					System.out.println("The number of :: is not correct. Ending the program.");
					System.exit(0);
				}

				if(token3[1].equals("FJ")){
					if(check4){
						System.out.println("There already is a final question. Ending the program.");
						System.exit(0);
					}
					int count = token3.length;
					if(count != 4){
						System.out.println("in"+token3[3]+", the number of :: is"+count);
						System.out.println("The number of :: is not correct. Ending the program.");
						System.exit(0);
					}
					FinalQuestion.add(token3[2]);
					FinalQuestion.add(token3[3]);
					check4 = true;
				}
				else if(token3[1].equals(CategoryA.get(0))){
					int count = token3.length;
					if(count !=5){
						System.out.println("in"+token3[3]+", the number of :: is"+count);
						System.out.println("The number of :: is not correct. Ending the program.");
						System.exit(0);
					}
					boolean check5 = false;
					for(int n=0; n<Credits.size(); n++){
						if(token3[2].equals(Credits.get(n))){
							check5 = true;
						}	
					}
					if(!check5){
						System.out.println("The credits granted to this question does not match the credit table."+token3[3]);
						System.out.println("Ending the program");
						System.exit(0);
					}
					int round = CategoryA.size()/4;
					boolean find = false;
					for(int m =1; m<=round; m++){
						if(token3[2].equals(CategoryA.get(m*4-2))){
							find = true;
						}
					}
					if(!find){
						CategoryA.add(token3[1]);
						CategoryA.add(token3[2]);
						CategoryA.add(token3[3]);
						CategoryA.add(token3[4]);
					}else{	
						System.out.println("The credits granted to this question has already appear in this Category.");
						System.out.println("Ending the program");
						System.exit(0);
					}
				}else if(token3[1].equals(CategoryB.get(0))){
					int count = token3.length;
					if(count != 5){
						System.out.println("in"+token3[3]+", the number of :: is"+count);
						System.out.println("The number of :: is not correct. Ending the program.");
						System.exit(0);
					}
					boolean check5 = false;
					for(int n=0; n<Credits.size(); n++){
						if(token3[2].equals(Credits.get(n))){
							check5 = true;
						}	
					}
					if(!check5){
						System.out.println("The credits granted to this question does not match the credit table."+token3[3]);
						System.out.println("Ending the program");
						System.exit(0);
					}
					int round = CategoryB.size()/4;
					boolean find = false;
					for(int m =1; m<=round; m++){
						if(token3[2].equals(CategoryB.get(m*4-2))){
							find = true;
						}
					}
					if(!find){
						CategoryB.add(token3[1]);
						CategoryB.add(token3[2]);
						CategoryB.add(token3[3]);
						CategoryB.add(token3[4]);
					}else{
						System.out.println("The credits granted to this question has already appear in this Category.");
						System.out.println("Ending the program");
						System.exit(0);
					}	
				}else if(token3[1].equals(CategoryC.get(0))){
					int count = token3.length;
					if(count != 5){
						System.out.println("in"+token3[3]+", the number of :: is"+count);
						System.out.println("The number of :: is not correct. Ending the program.");
						System.exit(0);
					}
					boolean check5 = false;
					for(int n=0; n<Credits.size(); n++){
						if(token3[2].equals(Credits.get(n))){
							check5 = true;
						}	
					}
					if(!check5){
						System.out.println("The credits granted to this question does not match the credit table."+token3[3]);
						System.out.println("Ending the program");
						System.exit(0);
					}
					int round = CategoryC.size()/4;
					boolean find = false;
					for(int m =1; m<=round; m++){
						if(token3[2].equals(CategoryC.get(m*4-2))){
							find = true;
						}
					}
					if(!find){
						CategoryC.add(token3[1]);
						CategoryC.add(token3[2]);
						CategoryC.add(token3[3]);
						CategoryC.add(token3[4]);
					}else{
						System.out.println("The credits granted to this question has already appear in this Category.");
						System.out.println("Ending the program");
						System.exit(0);	
					}
				}else if(token3[1].equals(CategoryD.get(0))){
					int count = token3.length;
					if(count != 5){
						System.out.println("in"+token3[3]+", the number of :: is"+count);
						System.out.println("The number of :: is not correct. Ending the program.");
						System.exit(0);
					}
					boolean check5 = false;
					for(int n=0; n<Credits.size(); n++){
						if(token3[2].equals(Credits.get(n))){
							check5 = true;
						}	
					}
					if(!check5){
						System.out.println("The credits granted to this question does not match the credit table."+token3[3]);
						System.out.println("Ending the program");
						System.exit(0);
					}
					int round = CategoryD.size()/4;
					boolean find = false;
					for(int m =1; m<=round; m++){
						if(token3[2].equals(CategoryD.get(m*4-2))){
							find = true;
						}
					}
					if(!find){
						CategoryD.add(token3[1]);
						CategoryD.add(token3[2]);
						CategoryD.add(token3[3]);
						CategoryD.add(token3[4]);
					}else{
						System.out.println("The credits granted to this question has already appear in this Category.");
						System.out.println("Ending the program");
						System.exit(0);
						
					}

				}else if(token3[1].equals(CategoryE.get(0))){
					int count = token3.length;
					if(count != 5){
						System.out.println("in"+token3[3]+", the number of :: is"+count);
						System.out.println("The number of :: is not correct. Ending the program.");
						System.exit(0);
					}
					boolean check5 = false;
					for(int n=0; n<Credits.size(); n++){
						if(token3[2].equals(Credits.get(n))){
							check5 = true;
						}	
					}
					if(!check5){
						System.out.println("The credits granted to this question does not match the credit table."+token3[3]);
						System.out.println("Ending the program");
						System.exit(0);
					}
					int round = CategoryE.size()/4;
					boolean find = false;
					for(int m =1; m<=round; m++){
						if(token3[2].equals(CategoryE.get(m*4-2))){
							find = true;
						}
					}
					if(!find){
						CategoryE.add(token3[1]);
						CategoryE.add(token3[2]);
						CategoryE.add(token3[3]);
						CategoryE.add(token3[4]);
					}else{
						System.out.println("The credits granted to this question has already appear in this Category.");
						System.out.println("Ending the program");
						System.exit(0);
						
					}

				}else{//This situation does not belong to any category
					System.out.println("The number of :: is not correct or the category of question is not correct. "
							+ "Ending the program.");
					System.exit(0);
				}
			}	
			if(!check4){
				System.out.println("There does not exist a final question. Ending the program.");
				System.exit(0);
			}
		}catch(FileNotFoundException fnfe){
			System.out.println(fnfe.getMessage());
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException ioe){
					System.out.println(ioe.getMessage());
				}
			}
		}
	}//readFile

	//This function will return a random number from 1,2,3,4
	public static int randInt(int max){
		Random rand = new Random();
		//min at this time is 1 
		int randomNum = rand.nextInt((max-1)+1)+1;
		return randomNum;
	}
	//This function will return the question selected by the user
	public static String findQuestion(String category, String score){
		if(category.equals(Categories.get(0).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryA.get(i*4-2))){
					return CategoryA.get(i*4-1);
				}
			}	
		}else if(category.equals(Categories.get(1).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryB.get(i*4-2))){
					return CategoryB.get(i*4-1);
				}
			}		
		}else if(category.equals(Categories.get(2).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryC.get(i*4-2))){
					return CategoryC.get(i*4-1);
				}
			}	
		}else if(category.equals(Categories.get(3).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryD.get(i*4-2))){
					return CategoryD.get(i*4-1);
				}
			}	
		}else if(category.equals(Categories.get(4).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryE.get(i*4-2))){
					return CategoryE.get(i*4-1);
				}
			}		
		}
		System.out.println("There is an error finding the question.");
		return null;
	}
	//This function will return the answer selected by the user
	public static String findAnswer(String category, String score){
		if(category.equals(Categories.get(0).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryA.get(i*4-2))){
					return CategoryA.get(i*4);
				}
			}	
		}else if(category.equals(Categories.get(1).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryB.get(i*4-2))){
					return CategoryB.get(i*4);
				}
			}		
		}else if(category.equals(Categories.get(2).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryC.get(i*4-2))){
					return CategoryC.get(i*4);
				}
			}	
		}else if(category.equals(Categories.get(3).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryD.get(i*4-2))){
					return CategoryD.get(i*4);
				}
			}	
		}else if(category.equals(Categories.get(4).toLowerCase())){
			for(int i=1; i<6; i++){
				if(score.equals(CategoryE.get(i*4-2))){
					return CategoryE.get(i*4);
				}
			}		
		}
		System.out.println("There is an error finding the answer.");
		return null;
	}
	//This function will check if the answer is a valid answer
	public static boolean checkAnswer(String input, String answer){
		String[] tokens = input.split(" ");	
		String[] tokens2 = answer.split(" ");
		if(tokens[0].toLowerCase().startsWith("w")){
			if(tokens[1].toLowerCase().equals("is")
					|| tokens[1].toLowerCase().equals("are")){	
				if(tokens[2].toLowerCase().equals("the")){
					String compactUserAnswer = "";
					String compactAnswer = "";
					for(int i=3; i<tokens.length; i++){
						compactUserAnswer+=tokens[i].toLowerCase();
					}
					//System.out.println("compact user anwer is"+ compactUserAnswer);
					for(int i=0; i<tokens2.length; i++){
						compactAnswer+=tokens2[i].toLowerCase();
					}
					//System.out.println("compact answer is "+compactAnswer);
					if(compactUserAnswer.equals(compactAnswer)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			System.out.println("Warning: Please enter in the format of a question");
			return false;
		}
	}
	/*This function will check if the user input 'exit' or 'replay'
	 * It will return 1 if the user input 'exit' at sometime
	 * It will return 2 if the user input 'replay' at some time
	 * It will return 0 by default
	 * */
	public static int checkSituation(Scanner input)throws NumberFormatException{
		String li;
		while(chosen.size()<25){	
			outerloop:
			for(int i=0; i<NumOfTeams; i++){
				if(chosen.size()>=25){
					break outerloop;
				}
				System.out.println("The # of questions has been answered:"+chosen.size());
				System.out.println("The team will go next will be "+ TeamList.get(PlayOrder.get(i)-1));
				System.out.println("Please choose a Category:");
				//checkCategory
				li = input.nextLine();
				if(li.equals("exit")){
					return 1;
				}
				if(li.equalsIgnoreCase("replay")){
					return 2;
				}
				String cate;
				String score = null;
				while(!checkCategory(li)){
					System.out.println("Please choose a Category:");
					li = input.nextLine();
					if(li.equals("exit")){
						return 1;
					}
					if(li.equalsIgnoreCase("replay")){
						return 2;
					}
				}
				cate = li;
				boolean found = false;			
				while(!found){
					System.out.println("Please enter the dollar value of the question you wish to answer");
					li = input.nextLine();
					if(li.equals("exit")){
						return 1;
					}
					if(li.equalsIgnoreCase("replay")){
						return 2;
					}
					for(int k=0; k<Credits.size(); k++){
						if(li.equals(Credits.get(k))){
							found = true;	
							score = li;
						}
					}
					if(!found){
						System.out.println("That dollar value does not exit.");
					}
					if(score!=null && chosen.contains(cate+score)){
						System.out.println("That question has already been chosen. Please choose another one.");
						found = false;
					}
				}
				score = li;
				chosen.add(cate+score);
				String currQ = findQuestion(cate.toLowerCase(), score);
				String currA = findAnswer(cate.toLowerCase(), score);
				System.out.println(currQ);
				System.out.println("Please enter your answer. Remeber to pose it as a question.");
				li = input.nextLine();
				if(li.equals("exit")){
					return 1;
				}
				if(li.equalsIgnoreCase("replay")){
					return 2;
				}
				if(checkAnswer(li, currA)){
					System.out.println("You get the answer correct! "+score+" will be added to you total.");
					String tmp1 = Scores.get(2*i+1);
					int tmp2 = Integer.parseInt(tmp1) + Integer.parseInt(score);
					String tmp3 = Integer.toString(tmp2);
					Scores.setElementAt(tmp3, 2*i+1);
					report();
				}else{
					System.out.println("Please enter your answer. Remeber to pose it as a question.");
					li = input.nextLine();
					if(li.equals("exit")){
						return 1;
					}
					if(li.equalsIgnoreCase("replay")){
						return 2;
					}
					if(checkAnswer(li, currA)){
						System.out.println("You get the answer correct! "+score+" will be added to you total.");	
						String tmp1 = Scores.get(2*i+1);
						//System.out.println("Current score is "+tmp1);
						int tmp2 = Integer.parseInt(tmp1) + Integer.parseInt(score);
						String tmp3 = Integer.toString(tmp2);
						//System.out.println("After adding credits: "+tmp3);
						Scores.setElementAt(tmp3, 2*i+1);
						report();
					}else{
						System.out.println("Sorry, you failed this question.");
						String tmp1 = Scores.get(2*i+1);
						//System.out.println("Current score is "+tmp1);
						int tmp2 = Integer.parseInt(tmp1) - Integer.parseInt(score);
						String tmp3 = Integer.toString(tmp2);
						//System.out.println("After adding credits: "+tmp3);
						Scores.setElementAt(tmp3, 2*i+1);
						report();
					}
				}
			}//for int i<NumOfTeams
		}//while => all questions have been answered
		int a = FinalJeopardy(input);
		if(a==2){
			return 2;
		}
		if(a==1){
			return 1;
		}
		return 0;
	}
	//This function will determine whether the user has inputed a valid category
	public static boolean checkCategory(String userInput){
		Vector<String>newCategories = new Vector<String>();
		for(int i=0; i<Categories.size(); i++){
			String get = Categories.get(i);
			newCategories.add(get.toLowerCase());
		}
		boolean check = false;
		String newInput = userInput.toLowerCase();
		for(int i=0; i<newCategories.size(); i++){
			if(newInput.equals(newCategories.get(i))){
				check = true;
			}
		}
		return check;
	}
	
	public static void setUp(){
		Scores.setSize(2*NumOfTeams);
		for(int i=0; i<NumOfTeams; i++){
			Scores.add(i*2,TeamList.get(PlayOrder.get(i)-1));
			Scores.add(i*2+1, "0");
		}
		for(int i=0; i<Scores.size(); i++){
			//System.out.println(Scores.get(i)+" ");
		}
	}
	
	public static int FinalJeopardy(Scanner input)throws NumberFormatException{
		String li = null;
		System.out.println("Now that all the questions have been chosen. It's time for Final Jeopardy!");
		report();
		System.out.println("Team with scores less than zero cannot bet over 0 dollars.");
		
		for(int i=0; i<NumOfTeams; i++){
			boolean checkk = false;
			while(!checkk){
				System.out.println("Team "+Scores.get(i*2)+" Please give a dollar amount from your total that you would like to bet.");
				li = input.nextLine();
				if(li.equals("replay")){
					return 2;
				}
				if(li.equals("exit")){
					return 1;
				}
				//System.out.println(li);
				checkk = checkk(li, i);	
				if(!checkk){
					System.out.println("The bet you have made is not valid. Please try again.");
				}
			}
			//checkk is true now
			bet.add(li);
		}
		System.out.println("The question is:");
		System.out.println(FinalQuestion.get(0));
		for(int i=0; i<NumOfTeams; i++){
			System.out.println("Team "+Scores.get(2*i)+", please enter your answer.");
			li = input.nextLine();
			if(li.equals("replay")){
				return 2;
			}
			if(li.equals("exit")){
				return 1;
			}
			if(checkAnswer(li, FinalQuestion.get(1))){
				//System.out.println("You get the answer correct! "+bet.get(i)+" will be added to you total.");	
				String tmp1 = Scores.get(2*i+1);
				//System.out.println("Current score is "+tmp1);
				int tmp2 = Integer.parseInt(tmp1) + Integer.parseInt(bet.get(i));
				String tmp3 = Integer.toString(tmp2);
				//System.out.println("After adding credits: "+tmp3);
				Scores.setElementAt(tmp3, 2*i+1);
				//report();
					
			}else{
				System.out.println("Please enter your anser, remember to pose it as a question.");
				li = input.nextLine();
				if(li.equals("replay")){
					return 2;
				}
				if(li.equals("exit")){
					return 1;
				}
				if(checkAnswer(li, FinalQuestion.get(1))){
					//System.out.println("You get the answer correct! "+bet.get(i)+" will be added to you total.");	
					String tmp1 = Scores.get(2*i+1);
					int tmp2 = Integer.parseInt(tmp1) + Integer.parseInt(bet.get(i));
					String tmp3 = Integer.toString(tmp2);
					Scores.setElementAt(tmp3, 2*i+1);
					//report();	
				}else{
					//System.out.println("Sorry, you failed this question. "+bet.get(i)+" scores will be deducted.");
					String tmp1 = Scores.get(2*i+1);
					//System.out.println("Current score is "+tmp1);
					int tmp2 = Integer.parseInt(tmp1) - Integer.parseInt(bet.get(i));
					String tmp3 = Integer.toString(tmp2);
					//System.out.println("After adding credits: "+tmp3);
					Scores.setElementAt(tmp3, 2*i+1);
					//report();
				}
			}		
		}//for all members to try the Final Jeopardy question
		report();
		int max = 0;
		Vector<Integer> winner = new Vector<Integer>();
		for(int i=0; i<NumOfTeams; i++){
			String a = Scores.get(2*i+1);
			int b = Integer.parseInt(a);
			if(b>max){
				max = b;
				winner.add(2*i);
			}
			if(b==max){
				winner.add(2*i);
			}
		}
		for(int n=0; n<winner.size(); n++){
			System.out.println("And the winner is "+Scores.get(winner.get(n)));
		}
		return 0;
	}
	//This function will return whether the bet is valid
	public static boolean checkk(String lii, int i){
		int a =  Integer.parseInt(lii);
		int b = Integer.parseInt(Scores.get(2*i+1));
		//System.out.println("The money chosen by user: "+a+" The money own by user: "+b);
		if(b<=0){
			if(a>0){
				return false;
			}else{
				return true;
			}
		}
		if(b>0){
			if(a<=b){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	public static void main (String []args) throws FileNotFoundException{
		readFile(args[0]);
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to Jeopardy!");
		System.out.println("Please enter the number of teams that will be playing in this game: ");
		boolean flag = true;
		try{
			while (flag){
				NumOfTeams = in.nextInt();
				if (NumOfTeams!=1 && NumOfTeams!=2 && NumOfTeams!=3 && NumOfTeams!=4){
					System.out.println("Invalid entry; Please try again.");
				}else{
					flag = false;
				}
			}
		    String nothing = in.nextLine();
			for(int i=0; i<NumOfTeams; i++){
				System.out.println("Please choose a name for Team" + (i+1));
				String name = in.nextLine();
				TeamList.add(name);
			}
			System.out.println("Thank you! Setting up the game for you...");
			System.out.println("Ready to play!");
			int a = randInt(NumOfTeams);
			PlayOrder.add(a);
			int b = a;
			while((b+1)<=NumOfTeams){
				PlayOrder.add(b+1);
				b++;
			}
			int c = 1;
			while(c<a){
				PlayOrder.add(c);
				c++;
			}
			setUp();
			
			int ch = checkSituation(in);
			while(ch==2){//this version cannot continue to check if it is replay
				System.out.println("Replay the game.");
				Scores.clear();
				setUp();
				report();
				ch = checkSituation(in);
			}
			if(ch==1){
				System.out.println("Exit the game.");
				System.exit(0);
			}

			//if(ch==0)
		}catch(InputMismatchException ime){
			System.out.println("Occurs an InputMismatchException "+ ime.getMessage());
		}
		
		in.close();
	}//main
}
