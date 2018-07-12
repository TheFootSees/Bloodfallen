package net.albedo.bloodfallen.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {
	
	public static String getStringFromInputStream(InputStream in){
		
		InputStreamReader is = new InputStreamReader(in);
		StringBuilder sb = new StringBuilder();
		String read;
		BufferedReader br = new BufferedReader(is);
		
		try {
			
			read = br.readLine();
			
			while(read != null) {
				sb.append(read);
				read = br.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public static void copyInputStreamToFile(InputStream in, File file) {
		
	    try {
	    	
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        
	        while((len = in.read(buf)) > 0){
	            out.write(buf, 0, len);
	        }
	        
	        out.close();
	        in.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void writeFile(File file, String text){
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void appendFile(File file, String text){
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] readFile(String s){
		try {
				String sCurrentLine;
				BufferedReader br = new BufferedReader(new FileReader(s));
				String list = "";
				while ((sCurrentLine = br.readLine()) != null) {
					list = list+","+(sCurrentLine);
				}
				
				br.close();
				
				return list.split(",");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static String[] readFile(File file){
		try {
				String sCurrentLine;
				BufferedReader br = new BufferedReader(new FileReader(file));
				String list = "";
				while ((sCurrentLine = br.readLine()) != null) {
					list = list+","+(sCurrentLine);
				}
				
				br.close();
				
				return list.split(",");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	public static String readFileFull(File file){
		try {
				String sCurrentLine;
				BufferedReader br = new BufferedReader(new FileReader(file));
				String list = "";
				while ((sCurrentLine = br.readLine()) != null) {
					list = list+"\r\n"+(sCurrentLine);
				}
				
				br.close();
				
				return list;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
