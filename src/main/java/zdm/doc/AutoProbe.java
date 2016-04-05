package zdm.doc;

import java.util.ArrayList;
import java.util.List;

import zdm.doc.parser.FoundItemParser;
import zdm.doc.persist.Persister;
import zdm.doc.wrapper.FoundItem;
import zdm.utils.http.HttpHeader;
import zdm.utils.http.NetUtils;

public class AutoProbe {
	
	private static final String url = "http://search.smzdm.com";
	
	public static List<FoundItem> found(String keyword, String section) throws Exception {
		List<FoundItem> r = new ArrayList<FoundItem>();
		try {
			String link = url+  "?c="+section + "&s="+keyword;
			System.out.println("looking for :" + link);
			HttpHeader header = new HttpHeader();
			header.addParam("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
			header.addParam("Referer", "http://search.smzdm.com/?c=home&s=macbook");
			header.addParam("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			header.addParam("Accept-Language", "zh-CN,zh;q=0.8");
			header.addParam("Cookie", "__jsluid=866575b22f70d10e50d933c88f9d3411;__jsl_clearance=1459854000.308|0|bogaNWw49Alg1AzlYoj1FX2YNnA%3D;");
			String rStr = NetUtils.httpGet("http://search.smzdm.com/?c=home&s=macbook",header);
//			String rStr = NetUtils.httpGet(url,header);
			List<FoundItem> temp = FoundItemParser.parse(rStr);
			
			System.out.println("found [" + temp.size() +"] items");
			for(FoundItem item : temp){
				if(!item.getTime().contains("-")){
					r.add(item);
				}
			}
			System.out.println("found [" +r.size()+ "] today new");
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}

}
