package main.java;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Crawler {
	
	public String crawl (String domain, String page, int port) {
		
		String temp, data = ""; // the variable that stores data from the server
		
		try (Socket server = new Socket(domain, port); // tries to connect to a server
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
				PrintWriter toServer = new PrintWriter(server.getOutputStream());
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));) {
			
			toServer.println("GET " + page + " HTTP/1.1");
			toServer.println("HOST: " + domain);
			toServer.println("CONNECTION: close");
			toServer.println();
			toServer.flush();
			
			while ((temp = fromServer.readLine()) != null) { // this loop is used to store the data from the server
				data += temp + "\n";
			}
		} catch (Exception e) { // catches any errors 
			e.printStackTrace(); // prints the error received
		}
		return data; // returns the data from the server
	}
	
	public static void main(String[] args) {
		Crawler test = new Crawler();
		String data;
		
		data = test.crawl("www.scp-wiki.net", "/scp-1250", 80);
	}
}