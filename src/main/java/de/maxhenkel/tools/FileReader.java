package de.maxhenkel.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;

import de.maxhenkel.car.Main;

@Deprecated
public class FileReader {

	public static String read(String name){
		InputStream inputstream = FileReader.class.getResourceAsStream("/assets/" +Main.MODID +"/lang/desc/" +name +".desc");
		
		List<String> strings=new ArrayList<String>();
		try {
			strings = IOUtils.readLines(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuffer sb=new StringBuffer();
		
		for(String s:strings){
			sb.append(s);
			sb.append("\n");
		}
		
		return sb.toString().trim();
	}
	
}
