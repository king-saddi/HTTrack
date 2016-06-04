import java.net.*;
import java.util.ArrayList;
import java.io.*;


public class HTTrack {
	
    public static void main(String[] args) throws Exception {

        URL index = new URL("http://paulgraham.com/index.html");
        String content = htmlToString(index);
        ArrayList<String> allLinks = new ArrayList<String>();
        
        String directoryToSave = "C:/Test/";
        save(directoryToSave, index);
        
        ArrayList<String> repeat = new ArrayList<String>();
        allLinks = getLinks(content.toString(), repeat);
        System.out.println(allLinks);
        int maxCrawlDepth = 2;
        crawl(index,allLinks, directoryToSave, 0, maxCrawlDepth);
        
    }
    
    public static void crawl(URL index, ArrayList<String> allLinks, String directory, int currentDepth, int maxDepth) throws Exception{
    	if(currentDepth<maxDepth){
	    	for(String link: allLinks){
	        	
	        	
	        	ArrayList<String> recursedLinks = new ArrayList<String>();
	        	
	        	
	        	URL current = new URL("http://" +index.getHost() + "/" +link);
	        	
	        	
	        	recursedLinks = getLinks(htmlToString(current), allLinks);
	        	
	        	if(currentDepth<maxDepth){
	        		save(directory, current);
	        		System.out.println("The current depth is:" + currentDepth);
	        		System.out.println("The current string is:" +current.toString());
	        		System.out.println(recursedLinks);
	        		crawl(index, allLinks, directory, currentDepth+1, maxDepth);
	        	}
	        }
    	}
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
    public static void save(String directory, URL link){
        BufferedReader in;
		try {
		in = new BufferedReader(new InputStreamReader(link.openStream()));


        String inputLine;
        
        FileOutputStream fop = null;
		File file;
		file = new File(directory + link.getHost() + link.getFile());
		file.getParentFile().mkdir();
		file.createNewFile();
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
    public static ArrayList<String> getLinks(String content, ArrayList<String> repeat){
    	
		int start = 0;
		int stop = 0;
        ArrayList<String> result = new ArrayList<String>();
    		//Search for the href tag
    		while((start = content.indexOf("href=\"", stop)) != -1) {
    			
    			stop = content.indexOf("\"", start +6 );
    			String url = content.substring(start +6, stop);
    			if(!repeat.contains(url)){
	    			if ((url.contains("html") == true) && (url.contains("http") != true)) { 
	    				result.add(url);
		    		}
    			}
    		}
    	
    	return result;
    }
}
