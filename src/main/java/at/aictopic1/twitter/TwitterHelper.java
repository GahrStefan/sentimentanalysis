package at.aictopic1.twitter;

import java.util.List;

import static java.lang.Math.log;

/**
 * 
 */
public class TwitterHelper {
    public static  double[] evaluateClassified(List<Tweet> tweets)
    {
        double multiplierSum = 0;
        double sum = 0;
        int positive = 0;
        int negative = 0;
        int neutral = 0;
        for (Tweet t : tweets) {
            double interimValue = t.getClassified();
            double multiplier;
            if (t.getUserFollower()>=500) {
                multiplier = 1;
            } else if(t.getUserFollower()<=1) {
                multiplier = 0.1;
            } else {
                multiplier = log(t.getUserFollower())/log(500);
            }
            multiplierSum += multiplier;
            sum += interimValue*multiplier;

            if (t.getClassification().equals("positive"))
            {
                positive++;
            } else if (t.getClassification().equals("negative")) {
                negative++;
            } else if (t.getClassification().equals("neutral")) {
                neutral++;
            } else {
                System.out.println("\n\n..............\nFAILURE\n..............\n\n");
            }
        }
        double finalValue;
        finalValue = (sum/multiplierSum-1)/2;

        return new double[] {finalValue,positive,neutral,negative};
    }
}
