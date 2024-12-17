package misa.core.events.input;

public class KeyReleaseEvent extends InputEvent
{
    private final int keyCode;

    public KeyReleaseEvent(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public int getKeyCode()
    {
        return keyCode;
    }
}
