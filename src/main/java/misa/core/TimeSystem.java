package misa.core;

import misa.core.events.EventManager;
import misa.scripting.LuaEventHandler;
import org.luaj.vm2.LuaValue;
import misa.core.events.gameplay.TimeChangeEvent;

/**
 * Represents a time system for a game, allowing the simulation of in-game time progression,
 * pausing, resetting, and triggering Lua-based events based on time changes.
 * <p>
 * This class integrates with Lua scripting to provide customizable event-driven
 * behavior for time-based mechanics in games.
 */
@SuppressWarnings("unused")
public class TimeSystem
{
    private int hours, minutes, seconds, days;     // In-game time units
    private final float progressionRate;           // Real-time to in-game time conversion rate
    private boolean isPaused;                      // Whether the time progression is paused
    private final LuaEventHandler luaEventHandler; // Lua event handler for time-based triggers
    private final EventManager eventManager;       // Reference to the EventManager

    /**
     * Constructs a new TimeSystem instance.
     *
     * @param rate           The progression rate: real-time seconds per in-game second.
     * @param luaEventHandler A LuaEventHandler instance for triggering Lua scripts on events.
     */
    public TimeSystem(float rate, LuaEventHandler luaEventHandler, EventManager eventManager)
    {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.days = 0;
        this.progressionRate = rate;
        this.isPaused = false;
        this.luaEventHandler = luaEventHandler;
        this.eventManager = eventManager;
    }

    /**
     * Updates the in-game time based on the real-time delta and progression rate.
     * Triggers the "onHourChange" Lua event whenever the hour changes.
     *
     * @param realTimeDelta The amount of real-world time (in seconds) that has elapsed.
     */
    public void update(float realTimeDelta)
    {
        if (isPaused) return;

        // Calculate in-game time progression
        float inGameTimeDelta = realTimeDelta * progressionRate;
        seconds += (int) inGameTimeDelta;

        // Handle overflow from seconds to minutes, and so on
        minutes += seconds / 60;
        seconds %= 60;

        hours += minutes / 60;
        minutes %= 60;

        days += hours / 24;
        hours %= 24;

        // Trigger Lua event for hour change
        luaEventHandler.triggerEvent("onHourChange", LuaValue.valueOf(hours));
        eventManager.triggerEvent(new TimeChangeEvent(hours, minutes, seconds));
    }

    /**
     * Sets the in-game time to the specified hour, minute, and second.
     * Validates the input and triggers the "onTimeSet" Lua event.
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

        // Trigger Lua event for time setting
        luaEventHandler.triggerEvent("onTimeSet",
                LuaValue.valueOf(h), LuaValue.valueOf(m), LuaValue.valueOf(s));
    }

    /**
     * Gets the current in-game time as a formatted string (HH:MM:SS).
     *
     * @return A string representation of the current in-game time.
     */
    public String getCurrentTime()
    {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Sets the pause state of the time system.
     *
     * @param paused True to pause time progression, false to resume.
     */
    public void setPaused(boolean paused)
    {
        this.isPaused = paused;
    }

    /**
     * Checks whether the time system is currently paused.
     *
     * @return True if time progression is paused, false otherwise.
     */
    public boolean isPaused()
    {
        return this.isPaused;
    }

    /**
     * Resets the in-game time to 00:00:00 and clears the day count.
     * Unpauses the system and triggers the "onReset" Lua event.
     */
    public void reset()
    {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.days = 0;
        this.isPaused = false;

        // Trigger Lua event for reset
        luaEventHandler.triggerEvent("onReset");
    }

    /**
     * Allows registration of a custom callback for when a specific hour is reached.
     * This method checks if the current hour matches the target hour and executes the callback if true.
     *
     * @param targetHour The hour to monitor (0-23).
     * @param callback   The callback to execute when the target hour is reached.
     */
    public void onTimeChange(int targetHour, Runnable callback)
    {
        if (hours == targetHour) callback.run();
    }
}
