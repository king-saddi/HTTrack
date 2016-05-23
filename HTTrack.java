import java.net.*;
import java.util.ArrayList;
import java.io.*;


public class HTTrack {
	
    public static void main(String[] args) throws Exception {

        URL index = new URL("http://paulgraham.com/index.html");
        
        
        String content = htmlToString(index);
        ArrayList<String> allLinks = new ArrayList<String>();
        
        allLinks = getLinks(content.toString());
        
        for(String link: allLinks){
        	
        	ArrayList<String> recursedLinks = new ArrayList<String>();
        	
        	
        	URL next = new URL("http://" +index.getHost() + "/" +link);
        	System.out.println(next.getFile());
        	
        	recursedLinks = getLinks(htmlToString(next));
        	for(String rLink: recursedLinks){
        		if (!allLinks.contains(rLink)){
        			//allLinks.add(rLink);
        		}
        	}
        	System.out.println(recursedLinks);
        	
        }
        
        System.out.println(allLinks);
        

    }
    
    // Given a url, this method reads the contents and 
    // returns a string with the html contents for parsing purposes
    public static String htmlToString(URL link){
    	
        BufferedReader in;
        StringBuilder content = new StringBuilder();
        
		try {
			in = new BufferedReader(new InputStreamReader(link.openStream()));
	        String inputLine;
			
	        while ((inputLine = in.readLine()) != null){
	        	content.append(inputLine);
	        }
	        
	        in.close();
        
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
    }
    
    // Create the appropriate file and directory, and save the url
    public static void save(URL link){
        BufferedReader in;
		try {
		in = new BufferedReader(new InputStreamReader(link.openStream()));


        String inputLine;
        
        FileOutputStream fop = null;
		File file;
		file = new File("C:/oracle/oracle.html");
		fop = new FileOutputStream(file);
		

		if (!file.exists()) {
			file.createNewFile();
		}
		
        while ((inputLine = in.readLine()) != null){
        	fop.write(inputLine.getBytes());
        }
        
        
        in.close();
        fop.flush();
        fop.close();
        
        
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    
    // This method returns a list of the relative links the page contains
    // by searching for href references that contain html and don't contain http(meaning absolute)
    public static ArrayList<String> getLinks(String content){
    	
		int start = 0;
		int stop = 0;
        ArrayList<String> result = new ArrayList<String>();
    		//Search for the href tag
    		while((start = content.indexOf("href=\"", stop)) != -1) {
    			
    			stop = content.indexOf("\"", start +6 );
    			String url = content.substring(start +6, stop);
    			if ((url.contains("html") == true) && (url.contains("http") != true)) { 
    				result.add(url);
	    		}
    		}
    	
    	return result;
    }
}
