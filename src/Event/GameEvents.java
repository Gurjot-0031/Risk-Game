package Event;

import java.io.File;

/**
 * This class is used to pass events from view to controller
 *
 * @author Team38
 *
 */
public class GameEvents implements IEvent {
    String eventInfo;
    File eventFile;
    String eventData;

    /**
     * Gets the event information
     */
    @Override
    public String getEventInfo()
    {
        return this.eventInfo;
    }

    /**
     * Gets the event data
     */
    @Override
    public String getEventData()
    {
        return this.eventData;
    }

    /**
     * Sets the event information
     *
     * @param info
     *            Information received from view.
     */
    public void setEventInfo(String info)
    {
        this.eventInfo = info;
    }

    /**
     * Sets the event data
     *
     * @param data
     *            Data received from view
     */
    public void setEventData(String data) {
        this.eventData = data;
    }
}
