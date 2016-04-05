package zdm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import zdm.doc.AutoProbe;
import zdm.doc.persist.Persister;
import zdm.doc.wrapper.FoundItem;
import zdm.utils.weixin.WinxinPoster;

public class Core {
	
	Timer timer;
	
	private Long interval;
	private String keyword;
	private String section;
	
	public Core(Long interval, String key, String sec){
		this.interval = interval;
		this.keyword= key;
		this.section = sec;
	}
	
	public void start(){
		
		timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				
				try {
					
					System.out.println("****************[- "+new Date()+" - scanning - ]****************");
					
					List<FoundItem> probed =  AutoProbe.found(keyword, section);
					List<FoundItem> readed = Persister.read();
					
					List<FoundItem> added = new ArrayList<FoundItem>();
					if(readed != null){
						for(FoundItem itemInProbed : probed){
							boolean inFile = false;
							for(FoundItem itemInReaded : readed){
								if(itemInProbed.getHref().equals(itemInReaded.getHref())){
									inFile = true;
									break;
								}
							}
							if(!inFile){
								added.add(itemInProbed);
							}
						}
					} else {
						added = probed;
					}
					
					
					boolean allSuccess = true;
					for(FoundItem addedItem : added){
						String text = String.format("TIME[%s],TITLE[%s],URL[%s]", addedItem.getTime(), addedItem.getTitle(), addedItem.getHref());
						boolean result = WinxinPoster.post(text);
						result &= allSuccess;
					}
					
					if(allSuccess){
						if (readed!=null) {
							readed.addAll(added);
						} else {
							readed = new ArrayList<FoundItem>();
							readed.addAll(added);
						}
					}
					
					Persister.write(readed);
					
					System.out.println("****************[ - scan finished - ]****************");
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, 0, interval);
		
	}

}
