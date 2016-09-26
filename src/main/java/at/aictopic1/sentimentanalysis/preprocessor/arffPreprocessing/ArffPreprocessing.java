package at.aictopic1.sentimentanalysis.preprocessor.arffPreprocessing;


import at.aictopic1.sentimentanalysis.preprocessor.arffPreprocessing.ArffPreprocessing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ArffPreprocessing {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
            
            String inputFile="Other Stuff/train_neutral.arff";
            String outputFile="Other Stuff/train_neutral_2.arff";
            
            FileOutputStream fop=null;
            File file;
		
            String wholeText="";
            ArffPreprocessor ap = new ArffPreprocessor("en");
		
		
            //load
	    BufferedReader br = new BufferedReader(new FileReader(inputFile));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
                
                boolean finished=false;
                while(!finished)
                {
                    if(line !=null)
                    {
                        if(line.startsWith("\"")||line.startsWith("\'"))
                        {
                            finished=true;
                        }
                        else
                        {
                            sb.append(line);
                            sb.append(System.lineSeparator());
                            line=br.readLine();
                        }
                    }
                    else
                    {
                        finished=true;
                    }
                }

	        while (line != null) {
                    
                    line=ap.preprocess(line);
                    
                    if (line.length()>0){
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                    line = br.readLine();
	        }
	        wholeText = sb.toString();
	    } finally {
	        br.close();
	    }
	    
	    //System.out.println(wholeText);
		
		//old load
		/*
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream("input.txt");
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        
		        // entferne nummer
		        char[] stringArray= line.toCharArray();
		        String newLine= "";
		        
		        int stage=0;
		        String valueOfField="";
		        
		        for (int i=0; i<stringArray.length;i++)
		        {
		        	switch (stage)
		        	{
		        	case 0:
		        		if (String.valueOf(stringArray[i]).equals(",") )
		        		{
		        			stage=1;
		        		}
		        		break;
		        	case 1:
		        		if (String.valueOf(stringArray[i]).equals("0") )
		        		{
		        			valueOfField="neg";
		        		}
		        		else
		        		{
			        		if (String.valueOf(stringArray[i]).equals("1") )
			        		{
			        			valueOfField="pos";
			        		}
			        		else
			        		{
			        			System.out.println("error in switchCase case 1");
			        			return;
			        		}
		        		}
	        			stage=2;
		        		break;
		        	case 2:
		        		stage=3;
		        		break;	
		        	case 3:
		        		if (String.valueOf(stringArray[i]).equals(",") )
		        		{
		        			stage=4;
		        		}
		        		break;
		        	case 4:
		        		switch (stringArray[i] )
		        		{
		        		case ';':
		        		case '\'':
		        		case '\\':
		        		case '\"':
		        			newLine+="\\";
		        			break;
		        		}
		        		newLine+=String.valueOf(stringArray[i]).toString();
		        		break;
		        	default:
		        		System.out.println( stage);
		        		System.out.println("ERROR switch case");
		        		return;
		        	
		        	}
		        }
		        
		        newLine='\"'+newLine+"\", "+valueOfField;
		        
		        wholeText+=newLine+"\n";
		        
		        System.out.println("Load: " + newLine);
		        
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		
		*/
		
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
