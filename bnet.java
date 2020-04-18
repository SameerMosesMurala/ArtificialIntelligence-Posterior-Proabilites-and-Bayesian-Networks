import java.util.*;

public class bnet {
	//the given events in the problem
	static String[] events = new String[] {"B", "E", "A", "J", "M"};
	//Probabilities of all the events in the problem
	//Given in the bayesian network diagram
	static double probBTrue = 0.001;
	static double probETrue = 0.002;
	static double probAGivenBTrueAndETrue = 0.95;
	static double probAGivenBTrueAndEFalse = 0.94;
	static double probAGivenBFalseAndETrue  = 0.29;
	static double probAGivenBFalseAndEFalse = 0.001;
	static double probJGivenATrue = 0.90;
	static double probJGivenAFalse = 0.05;
	static double probMGivenATrue = 0.70;
	static double probMGivenAFalse = 0.01;
	public static void main(String[] args) 
	{
		//variable to denote whether the given is present in the input command 
		boolean given=false;
		//hash map to store the variables present before the given statement
		Map<String, Boolean> present_num_Variables = new HashMap<String, Boolean>();
		//Store the variables present not present before the given statement
		ArrayList<String> not_present_num_Variables = new ArrayList<String>();
		//hash map to store the variables present after the given statement
		Map<String, Boolean> present_den_Variables = new HashMap<String, Boolean>();
		//Store the variables present not present after the given statement
		ArrayList<String> not_present_den_Variables = new ArrayList<String>();
		//loop for all the arguments in the input arguments
		for(int i=0;i<args.length;i++) 
		{
			//check if given is present in the arguments 
			if(args[i].equals("given")) {
				given  = true; 
				continue;
			}
			//Convert the first character of the argument to string
			String char_string=Character.toString(args[i].charAt(0));
			boolean assign_truth_value=false;
			//assign its truth value
			if(args[i].charAt(1)=='t')
			{
				assign_truth_value=true;
			}
			//add it to the variable holding the variables that are present
			present_num_Variables.put(char_string, assign_truth_value);
			//if the given statement is present then add to the variable holding the events present after the given statement
			if(given)
			{
				present_den_Variables.put(char_string, assign_truth_value);
			}
		}
		//iterate through all the variables present in the problem statement and check if they are present in the present_variables hash map
		//else add them to variable not_present_varaible variable to assign values to them later
		for(int i=0;i<events.length;i++)
		{
			if(!present_num_Variables.containsKey(events[i]))
			{
				not_present_num_Variables.add(events[i]);
			}
			if(!present_den_Variables.containsKey(events[i]))
			{
				not_present_den_Variables.add(events[i]);
			}
		}
		//call the assign_values method to assign values to the unassigned variables and calculate their probability
		double result=assign_values(present_num_Variables,not_present_num_Variables);
		//if given is present then repeat the same for the variable holding the events present after the given statement
		if(given)
		{
			double result_den=assign_values(present_den_Variables,not_present_den_Variables);
			//P(x|y)=p(x,y)/p(y)
			result=result/result_den;
		}
		System.out.println("Probability for the given set of events are"+result);
	}
	//method to assign values to the unassigned variables and call the compute_probability to calculate 
	//the final probability
	public static double assign_values(Map<String, Boolean> present_num_Variables_1,ArrayList<String> not_present_num_Variables_1 )
	{
		//call this method recursively until all the variables have been assigned a value
		if(not_present_num_Variables_1.isEmpty())
		{
			return computeProbability(present_num_Variables_1.get("B"), present_num_Variables_1.get("E"), present_num_Variables_1.get("A"), present_num_Variables_1.get("J"), present_num_Variables_1.get("M"));
		}
		//get the first unassigned variable
		String first = not_present_num_Variables_1.get(0);
		ArrayList<String> rest = new ArrayList<String>();
		//store the rest of the unassigned variables in another array string
		for(int i=1; i<not_present_num_Variables_1.size();i++) 
		{
			rest.add(not_present_num_Variables_1.get(i));
		}
		//assign true value for that and recursively call the assign_values method to assign 
		//values to rest of the unassigned variables 
		present_num_Variables_1.put(first, true);
		double not_present_true = assign_values(present_num_Variables_1, rest);
		//assign false value for that and recursively call the assign_values method to assign 
		//values to rest of the unassigned variables 
		present_num_Variables_1.put(first, false);
		double not_present_false = assign_values(present_num_Variables_1, rest);
		//return the sum of both values for the unassigned variables 
		return not_present_true + not_present_false;
	}
	public static double computeProbability(boolean b,boolean e,boolean a,boolean j,boolean m)
	{
		//create variables to store the probability value for each event
		double prob_result = 0;
		double burglary_result;
		double earthquake_result;
		double alarm_result;
		double john_calls_result;
		double mary_calls_result;
		//Burglary is an independent event
		if(b)
		{
			burglary_result=probBTrue;
		}
		else
		{
			burglary_result=(1-probBTrue);
		}
		//earthquake is an independent event
		if(e)
		{
			earthquake_result=probETrue;
		}
		else
		{
			earthquake_result=(1-probETrue);
		}
		//john_calls and mary_calls are dependent on the event Alarm
		if(a)
		{
			if(j)john_calls_result=probJGivenATrue;
			else john_calls_result=(1-probJGivenATrue);
			if(m)mary_calls_result=probMGivenATrue;
			else mary_calls_result=(1-probMGivenATrue);
		}
		else
		{
			if(j)john_calls_result=probJGivenAFalse;
			else john_calls_result=(1-probJGivenAFalse);
			if(m)mary_calls_result=probMGivenAFalse;
			else mary_calls_result=(1-probMGivenAFalse);
		}
		//Alarm event is dependent on the events Burglary and Earthquake
		if(b)
		{
			if(e)
			{
				if(a)alarm_result=probAGivenBTrueAndETrue;
				else alarm_result=(1-probAGivenBTrueAndETrue);
			}
			else
			{
				if(a)alarm_result=probAGivenBTrueAndEFalse;
				else alarm_result=(1-probAGivenBTrueAndEFalse);
			}
		}
		else
		{
			if(e)
			{
				if(a)alarm_result=probAGivenBFalseAndETrue;
				else alarm_result=(1-probAGivenBFalseAndETrue);
			}
			else
			{
				if(a)alarm_result=probAGivenBFalseAndEFalse;
				else alarm_result=(1-probAGivenBFalseAndEFalse);
			}
		}
		//Return the final result using bayesian formula for networks 
		return prob_result=burglary_result*earthquake_result*alarm_result*john_calls_result*mary_calls_result;
		
	}
}
