/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.preprocessor.arffPreprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * 
 */
public class sortArff {
    
    private static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
    public int compare(String str1, String str2) {
        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
        if (res == 0) {
            res = str1.compareTo(str2);
        }
        return res;
    }
};
    
	public static void main(String[] args) throws FileNotFoundException, IOException{
            
            String inputFile="Other Stuff/bearbeiteTweets.txt";
            String outputFile="Other Stuff/sortedTweets";
            
            FileOutputStream fop=null;
            File file;
		
            String wholeText="";
		
            //load
	    BufferedReader br = new BufferedReader(new FileReader(inputFile));
	    try {
                List<String> sb=new ArrayList();
	        String line = br.readLine();
                
                //skip first lines
                boolean finished=false;
                while(!finished)
                {
                    if(line !=null)
                    {
                        if(line.startsWith("\"")||line.startsWith("\'"))
                        {
                            finished=true;
                        }
                    }
                    else
                    {
                        finished=true;
                    }
                }

	        while (line != null) {
                    //line=ap.preprocess(line);
                    
                    if (line.length()>0){
                        sb.add(line);
                    }
                    line = br.readLine();
                    
	        }
                
                Collections.sort(sb,ALPHABETICAL_ORDER);
                
                String lastString="";
                for (String s:sb)
                {
                    if (lastString.length()!=0)
                    {
                        if (!lastString.equals(s))
                        {
                            wholeText += s+System.lineSeparator();                            
                        }
                    }
                        
                    
                    lastString=s;
                }
	    } finally {
	        br.close();
	    }
            
                    
            boolean finished=false;
            while (!finished)
            {
                finished=true;
            }
		
		//saving
		try {
			 
			file = new File(outputFile);
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = wholeText.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    
        }
}
