import java.io.BufferedWriter;
import java.io.FileWriter;

public class compute_a_posteriori {
	public static void main(String[] args) 
	{
		//variable to store the input observations
		String input_observations;
		//Taking input from the command line
		if(args.length>0)
		{	
			input_observations=args[0];
		}
		else
		{
			input_observations="";
		}
		//length of observations
		int input_obs_length=input_observations.length();
		//Prior probabilities of each bag
		double[] h_bag_prob = new double[] {0.1, 0.2, 0.4, 0.2, 0.1};
		//Probabilities of cherry in each bag
		double[] h_bag_cherry = new double[] {1.0, 0.75, 0.5, 0.25, 0};
		//Probabilities of lime in each bag
		double[] h_bag_lime = new double[] {0, 0.25, 0.5, 0.75, 1.0};
		//Probability of next candy being choosen is Cherry 
		double prob_next_cherry=0;
		//Probability of next candy being choosen is Cherry
		double prob_next_lime=0;
		//String builder to print the result
		StringBuffer sb = new StringBuffer();
		sb.append("Observation sequence Q: "+input_observations+"\r\n");
		sb.append("Length of Q:"+input_observations.length()+"\r\n");
		//iterate for each observation in the input observations
		for(int i=0;i<=input_obs_length;i++)
		{ 
			char input_obs;
			//if there are no observations
			if(i==0)
			{
				input_obs='N';
			}
			else
			{
				input_obs=input_observations.charAt(i-1);
			}
			sb.append("After Observation "+i+" = "+ input_obs+":\r\n");
			//iterate for each of the 5 bags
			for(int j=0;j<5; j++) 
			{
				if(i>0)
				{	
					//calculate the posterior probability of bag being 
					//choosen if the candy choosen was cherry 
				if(input_obs=='C')
				{
					h_bag_prob[j] = (h_bag_prob[j]*h_bag_cherry[j])/prob_next_cherry;
				}
				//calculate the posterior probability of bag being 
				//choosen if the candy choosen was lime 
				if(input_obs=='L') 
				{
					h_bag_prob[j] = (h_bag_prob[j]*h_bag_lime[j])/prob_next_lime;
				}
				}
			}
			for(int j=0;j<5; j++)
			{
				sb.append("P(h"+(j+1)+" | Q) = "+h_bag_prob[j]+"\r\n");
			}
			prob_next_cherry=0;
			prob_next_lime=0;
			//Calculate the new probabilties of next candy being choosen is cherry
			for(int j=0;j<5; j++)
			{
				prob_next_cherry += h_bag_prob[j]*h_bag_cherry[j];
			}
			//Calculate the new probabilties of next candy being choosen is cherry
			for(int j=0;j<5; j++)
			{
				prob_next_lime += h_bag_prob[j]*h_bag_lime[j];
			}
			sb.append("Probability that the next candy we pick will be C, given Q:"+prob_next_cherry+"\r\n");
			sb.append("Probability that the next candy we pick will be L, given Q:"+prob_next_lime+"\r\n");
		}
		//Print the result in a file results.txt
		try {
		String output_value=sb.toString();
		BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
		writer.write(output_value);
		writer.newLine();
		writer.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	}


