package dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	

	
	public static  void main(String args[])
	{
		
		startApplication();
	}
	
	
	public static void startApplication()
	{
		boolean isApplicationClosed=true;
		boolean isGameInProgress=false;
		System.out.println("Dashboard Started. Enter Command to continue");
		Scanner userInputScanner=new Scanner(System.in);
		Map<String,ArrayList<String>> gameRecord = new HashMap<String,ArrayList<String>>();
		while (isApplicationClosed)
		{
			String userInput=userInputScanner.nextLine();
			String teamOne="";
			String teamTwo="";
			if (isGameInProgress || userInput.matches("Start:\\s['].*[']\\svs\\.\\s['].*[']"))
			{
				if (userInput.matches("Start:\\s['].*[']\\svs\\.\\s['].*[']"))
				{
						isGameInProgress=true;
						Matcher teamOneRegexMatcher=Pattern.compile("['].*[']\\s").matcher(userInput);
						teamOneRegexMatcher.find();
						Matcher teamTwoRegexMatcher=Pattern.compile("\\.\\s['].*[']").matcher(userInput);
						teamTwoRegexMatcher.find();
						teamOne=teamOneRegexMatcher.group().trim().replaceAll("'", "");
						teamTwo=teamTwoRegexMatcher.group().replace(".", "").trim().replaceAll("'", "");
						gameRecord.put(teamOne,new ArrayList<String>());
						gameRecord.put(teamTwo,new ArrayList<String>());
				}
				else if (userInput.matches("[0-9][0-9]\\s['].*[']\\s.*"))
				{
					Matcher teamNameRegexMatcher=Pattern.compile("['].*[']\\s").matcher(userInput);
					teamNameRegexMatcher.find();
					Matcher minuteRegexMatcher=Pattern.compile("[0-9]{1,2}").matcher(userInput);
					minuteRegexMatcher.find();
					Matcher scorerRegexMatcher=Pattern.compile("[']\\s.*").matcher(userInput);
					scorerRegexMatcher.find();
					String teamName=teamNameRegexMatcher.group().replaceAll("'", "");
					ArrayList<String>record=gameRecord.get(teamName);
					if (record==null)
						record=new ArrayList<>();
					record.add(minuteRegexMatcher.group());
					record.add(scorerRegexMatcher.group().replace("'", "").trim());
					gameRecord.put(teamName,record);
				}
				else if (userInput.equals("print"))
				{
					getScore(gameRecord);
					
				}
				else if (userInput.equals("End"))
				{
					isGameInProgress=false;
					gameRecord.clear();
					
				}
				else 
				{
					System.out.println("input error - please type 'print' for game details");
				}	
			}
			else
			{
				if (!(userInput.matches("Start:\\s['].*[']\\svs\\.\\s['].*[']") || userInput.matches("[0-9][0-9]\\s['].*[']\\s.*") || userInput.equals("print") || userInput.equals("End")))
					System.out.println("input error - please start a game through typing 'Start: '<Name of Home Team>' vs. '<Name of Away Team>'");
				else
				System.out.println("No game currently in progress");

			}
		}
		userInputScanner.close();
	}
	
	
	public static void getScore(Map<String,ArrayList<String>> gameRecord)
	{
		String teamOne=(String) gameRecord.keySet().toArray()[0];
		String teamTwo=(String) gameRecord.keySet().toArray()[1];
		String scorersOfTeamOne="";
		String scorersOfTeamTwo="";
		int teamOneScore=0;
		int teamTwoScore=0;
		for (int i=1;i<gameRecord.get(teamOne).size();i=i+2)
		{
			scorersOfTeamOne=scorersOfTeamOne+gameRecord.get(teamOne).get(i)+" "+gameRecord.get(teamOne).get(i-1)+"'";
			teamOneScore++;
		}
		for (int i=1;i<gameRecord.get(teamTwo).size();i=i+2)
		{
			scorersOfTeamTwo=scorersOfTeamTwo+gameRecord.get(teamTwo).get(i)+" "+gameRecord.get(teamTwo).get(i-1)+"'";
			teamTwoScore++;
		}
		if (!(scorersOfTeamOne.equals("")))
			scorersOfTeamOne=" ("+scorersOfTeamOne+")";
		if (!(scorersOfTeamTwo.equals("")))
			scorersOfTeamTwo=" ("+scorersOfTeamTwo+")";
		System.out.println(teamOne+" "+teamOneScore+scorersOfTeamOne+" vs. "+teamTwo+" "+teamTwoScore+scorersOfTeamTwo);
		
	}
	
	
	

}
