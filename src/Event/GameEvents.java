package Event;

import java.io.File;

public class GameEvents implements IEvent {
	String eventInfo;
	File eventFile;
	String eventData;
	
	@Override
	public String getEventInfo() {
		return this.eventInfo;
	}

	@Override
	public String getEventData() {
		return this.eventData;
	}
	
	public void setEventInfo(String info) {
		this.eventInfo = info;
	}
	
	public void setEventData(String data) {
		this.eventData = data;
	}
}
