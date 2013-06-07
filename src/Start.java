import org.w3c.dom.Element;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class Start {
	
	public Start() throws Exception
	{
		String searchName;
		Scanner terminalInput = new Scanner(System.in);
		String urlString, urlPt;
		
		
		Element eElement;
		
		System.out.print("Enter the person you are looking for: ");
	
		//read input
		searchName = terminalInput.nextLine();

		//convert name for request
		searchName = searchName.replace(". ", "_");
		searchName = searchName.replace(" ", "_");
		
		System.out.println(searchName);
		
		
		urlString = "http://dblp.uni-trier.de/search/author?xauthor=" + searchName;
		

		
		NodeList nList = getXmlDoc(urlString).getElementsByTagName("author");
		

		
		if(nList.getLength()==0)
		{
			System.out.println("Author not found!");
			System.exit(0);
		}
		
		
		eElement = (Element) nList.item(0);
		urlPt = eElement.getAttribute("urlpt");
		
		//get CoAuthors
		urlString="http://dblp.uni-trier.de/rec/pers/" + urlPt + "/xc";
		
		
		nList = getXmlDoc(urlString).getElementsByTagName("author");
		
		
		System.out.println("CoAuthors are:");
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
			 
			Node nNode = nList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				eElement = (Element) nNode;
	 
				System.out.println(eElement.getAttribute("urlpt"));
				
			}
		}
	}
	

	public static void main(String[] args) throws Exception 
	{
		new Start();
		
	}
	
	public static Document getXmlDoc(String urlString) throws Exception
	{
		URL url = new URL(urlString);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		
		InputStream xml = connection.getInputStream();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.parse(xml);
		
		return doc;
	}
	
	public String[] getAuthors(int number)
	{
		
		
		return null;
	}
	
}
