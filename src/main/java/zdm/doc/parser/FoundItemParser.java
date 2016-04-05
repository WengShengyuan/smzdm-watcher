package zdm.doc.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zdm.doc.wrapper.FoundItem;

public class FoundItemParser {

	public static List<FoundItem> parse(String HTML){
		Document doc = Jsoup.parse(HTML);
		Elements lis = doc.select(".search-list");
		List<FoundItem> r = new ArrayList<FoundItem>();
		for(Element item : lis){
			try {
				Elements titleE = item.select(".search-list-right>.list-title>a");
				Elements detailE = item.select(".search-list-right>.list-detail");
				Elements dateE = item.select(".search-list-right>.list-stuff>.list-stuff-left>.list-time");
				
				if(titleE.size() >0 && detailE.size() >0 && dateE.size()>0){
					String title = titleE.get(0).attr("title");
					String href = titleE.get(0).attr("href");
					String detail = detailE.get(0).html();
					String time = dateE.get(0).html();
					FoundItem f = new FoundItem(title, detail, href, time);
					r.add(f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return r;
	}
	
	public static List<FoundItem> parse(List<String> list){
		List<FoundItem> r = new ArrayList<FoundItem>();
		for(String line : list){
			FoundItem item = new FoundItem(line);
			r.add(item);
		}
		return r;
	}
	
}
