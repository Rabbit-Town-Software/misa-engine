package misa.core.events.input;

public class KeyPressEvent extends InputEvent
{
    private final int keyCode;

    public KeyPressEvent(int keyCode)
    {
        this.keyCode = keyCode;
    }

    public int getKeyCode()
    {
        return keyCode;
    }
}
