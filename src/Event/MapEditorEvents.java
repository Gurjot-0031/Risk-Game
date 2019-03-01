package Event;

import java.io.File;

public class MapEditorEvents implements IEvent {
	String eventInfo;
	File eventFile;
	String eventData;
	
	public void setEventFile(File eventFile) {
		this.eventFile = eventFile;
	}
	
	public File getEventFile() {
		return eventFile;
	}
	
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}
	
	public String getEventInfo() {
		return this.eventInfo;
	}
	
	public void setEventData(String eventData) {
		this.eventData = eventData;
	}
	
	public String getEventData() {
		return this.eventData;
	}
}
