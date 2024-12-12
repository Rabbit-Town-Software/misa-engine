package misa.testing.timesystem;

import misa.core.TimeSystem;
import misa.scripting.LuaEventHandler;
import misa.scripting.LuaManager;

public class TimeSystemLuaTest
{
    public static void main(String[] args)
    {
        System.out.println("Running TimeSystem with Lua Integration Tests...");

        // 1. Initialize Lua scripting
        LuaManager luaManager = new LuaManager();
        LuaEventHandler luaEventHandler = new LuaEventHandler(luaManager);

        // 2. Register Lua event scripts
        luaEventHandler.registerEventHandler("onHourChange",
                "function onHourChange(hour) print('Lua: Hour Changed to: ' .. hour) end");
        luaEventHandler.registerEventHandler("onReset",
                "function onReset() print('Lua: Time system has been reset!') end");
        luaEventHandler.registerEventHandler("onTimeSet",
                "function onTimeSet(hour, minute, second) print('Lua: Time set to ' .. hour .. ':' .. minute .. ':' .. second) end");


        // 3. Initialize TimeSystem with Lua integration
        TimeSystem clock = new TimeSystem(3, luaEventHandler); // 3 real seconds = 1 in-game minute
        System.out.println("Initial Time: " + clock.getCurrentTime());

        // 4. Simulate time updates and test Lua script execution
        clock.update(60);  // Simulate 60 seconds of real time
        System.out.println("After 60 seconds of real time: " + clock.getCurrentTime());

        clock.update(300);  // Simulate 300 seconds of real time
        System.out.println("After 300 more seconds of real time: " + clock.getCurrentTime());

        // 5. Test Lua script on reset
        clock.reset();  // Should trigger the "onReset" Lua script
        System.out.println("After reset: " + clock.getCurrentTime());

        // 6. Test hour change Lua script
        clock.setTime(23, 50, 0);  // Set time close to midnight
        System.out.println("Set Time to: " + clock.getCurrentTime());

        clock.update(600);  // Simulate 10 minutes (600 seconds) of real time
        System.out.println("After 10 minutes of real time: " + clock.getCurrentTime());

        System.out.println("All Lua integration tests complete!");
    }
}
