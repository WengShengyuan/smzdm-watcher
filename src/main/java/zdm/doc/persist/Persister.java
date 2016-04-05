package zdm.doc.persist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import zdm.doc.parser.FoundItemParser;
import zdm.doc.wrapper.FoundItem;

public class Persister {
	private static final String filePath = "temp.lst";
	
	@SuppressWarnings("unchecked")
	public static List<FoundItem> read() throws FileNotFoundException, IOException{
		File f = new File(filePath);
		if(!f.exists())
			return null;
		FileReader reader = new FileReader(filePath);
		List<String> strings  = IOUtils.readLines(reader);
		List<FoundItem> wrappers = FoundItemParser.parse(strings);
		System.out.println("found ["+wrappers.size()+"] today new already sent");
		reader.close();
		return wrappers;
	}
	
	
	public static void write(List<FoundItem> items) throws IOException{
		FileWriter writer = new FileWriter(filePath, false);
		for(FoundItem f : items){
			String str = f.toString();
			IOUtils.write(str+"\n", writer);
		}
		writer.close();
	}
}
