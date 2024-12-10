package misa.testing.timesystem;

import misa.core.TimeSystem;
import misa.scripting.LuaEventHandler;
import misa.scripting.LuaManager;

public class TimeSystemTest
{
    public static void main(String[] args)
    {
        System.out.println("Running TimeSystem Tests...");

        // 1. Initialize LuaManager and LuaEventHandler
        LuaManager luaManager = new LuaManager();
        LuaEventHandler luaEventHandler = new LuaEventHandler(luaManager);

        // Optional: Register Lua event handlers
        luaEventHandler.registerEventHandler("onReset",
                "function onReset() print('Lua: Time system has been reset!') end");

        // 2. Initialize the TimeSystem with Lua integration
        TimeSystem clock = new TimeSystem(3, luaEventHandler);  // 3 real seconds = 1 in-game minute
        System.out.println("Initial Time: " + clock.getCurrentTime());

        // 3. Test time progression
        clock.update(1);  // Simulate 1 second of real time
        System.out.println("After 1 second of real time: " + clock.getCurrentTime());

        clock.update(5);  // Simulate 5 seconds of real time
        System.out.println("After 5 more seconds of real time: " + clock.getCurrentTime());

        // 4. Test overflow
        clock.update(60);  // Simulate 60 seconds of real time
        System.out.println("After 60 seconds of real time: " + clock.getCurrentTime());

        // 5. Test setTime method
        clock.setTime(23, 59, 50);  // Set time close to midnight
        System.out.println("Set Time to: " + clock.getCurrentTime());

        clock.update(10);  // Simulate 10 seconds of real time
        System.out.println("After 10 seconds of real time: " + clock.getCurrentTime());

        // 6. Test pause functionality
        clock.setPaused(true);
        clock.update(5);  // Should not update
        System.out.println("After pause (5 seconds real time): " + clock.getCurrentTime());

        clock.setPaused(false);
        clock.update(5);  // Resume updating
        System.out.println("After resume (5 seconds real time): " + clock.getCurrentTime());

        // 7. Test reset
        clock.reset();  // This should trigger the Lua "onReset" event
        System.out.println("After reset: " + clock.getCurrentTime());

        System.out.println("All tests complete!");
    }
}
