package misa.core.events.gameplay;

import misa.core.events.EventListener;

@SuppressWarnings("unused")
public class TimeChangeListener implements EventListener<TimeChangeEvent>
{
    @Override
    public void handleEvent(TimeChangeEvent event)
    {
        // Handle the time change event, such as logging or updating game mechanics
        System.out.println("Time has changed: " + event.hours() + ":" + event.minutes() + ":" + event.seconds());

        // You can add custom logic here, such as triggering specific game events based on time
    }
}
