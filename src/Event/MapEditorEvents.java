package Event;

import java.io.File;

/**
 * This class is used to pass map editor events from view to controller
 * 
 * @author Team38
 *
 */
public class MapEditorEvents implements IEvent {
	String eventInfo;
	File eventFile;
	String eventData;

	/**
	 * Sets the event file
	 * 
	 * @param eventFile
	 *            input event file
	 */
	public void setEventFile(File eventFile) {
		this.eventFile = eventFile;
	}

	/**
	 * Gets the event file
	 * 
	 * @return Event file
	 */
	public File getEventFile() {
		return eventFile;
	}

	/**
	 * Sets the event information
	 * 
	 * @param eventInfo
	 *            The input event information
	 */
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}

	/**
	 * Get event information
	 * 
	 * @return String event information
	 */
	public String getEventInfo() {
		return this.eventInfo;
	}

	/**
	 * Sets the event data
	 * 
	 * @param eventData
	 *            The input event data
	 */
	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	/**
	 * Gets the event data
	 * 
	 * @return String Event data
	 */
	public String getEventData() {
		return this.eventData;
	}
}
