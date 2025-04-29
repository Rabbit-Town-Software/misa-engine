package misa.core;

import misa.core.events.EventManager;

/**
 * TimeSystem handles the simulation of in-game time progression,
 * including pausing, resetting, and checking time-based triggers.
 * <p>
 * It is designed to be flexible for RPGs, simulations, and games
 * needing a consistent day/night cycle or timed events.
 */
@SuppressWarnings("unused")
public class TimeSystem
{
    // In-game time units
    private int hours, minutes, seconds, days;

    // Conversion rate: how fast in-game time passes relative to real time
    private final float progressionRate;

    // Whether time progression is currently paused
    private boolean isPaused;

    /**
     * Constructs a new TimeSystem instance.
     *
     * @param rate Progression rate: how many real-world seconds = 1 in-game second.
     */
    public TimeSystem(float rate, EventManager eventManager)
    {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.days = 0;
        this.progressionRate = rate;
        this.isPaused = false;

        // (Future integration: EventManager could be used to trigger Lua or gameplay events)
    }

    /**
     * Updates the in-game time based on the real-time delta and progression rate.
     *
     * @param realTimeDelta The amount of real-world time (in seconds) that has elapsed since the last update.
     */
    public void update(float realTimeDelta)
    {
        if (isPaused) return;

        // Calculate how much in-game time has passed
        float inGameTimeDelta = realTimeDelta * progressionRate;
        seconds += (int) inGameTimeDelta;

        // Handle overflow from seconds → minutes → hours → days
        minutes += seconds / 60;
        seconds %= 60;

        hours += minutes / 60;
        minutes %= 60;

        days += hours / 24;
        hours %= 24;
    }

    /**
     * Sets the current in-game time to the specified hour, minute, and second.
     *
     * @param h The hour to set (0-23).
     * @param m The minute to set (0-59).
     * @param s The second to set (0-59).
     * @throws IllegalArgumentException if the provided time values are invalid.
     */
    public void setTime(int h, int m, int s)
    {
        if (h < 0 || h > 23 || m < 0 || m > 59 || s < 0 || s > 59)
        {
            throw new IllegalArgumentException("Invalid time value.");
        }

        this.hours = h;
        this.minutes = m;
        this.seconds = s;
    }

    /**
     * Gets the current in-game time as a formatted string (HH:MM:SS).
     *
     * @return The current in-game time as a string.
     */
    public String getCurrentTime()
    {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Pauses or resumes time progression.
     *
     * @param paused True to pause time progression, false to resume it.
     */
    public void setPaused(boolean paused)
    {
        this.isPaused = paused;
    }

    /**
     * Checks whether the time progression is currently paused.
     *
     * @return True if paused, false otherwise.
     */
    public boolean isPaused()
    {
        return this.isPaused;
    }

    /**
     * Resets the in-game time to 00:00:00 and clears the day count.
     * Also unpauses the system.
     */
    public void reset()
    {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.days = 0;
        this.isPaused = false;
    }

    /**
     * Triggers a callback when the current in-game hour matches a specified target hour.
     * <p>
     * (This check must be manually called by the user during update cycles.)
     *
     * @param targetHour The hour to monitor (0-23).
     * @param callback   The code to run when the target hour is reached.
     */
    public void onTimeChange(int targetHour, Runnable callback)
    {
        if (hours == targetHour)
        {
            callback.run();
        }
    }
}
