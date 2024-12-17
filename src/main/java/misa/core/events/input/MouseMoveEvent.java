package misa.core.events.input;

public class MouseMoveEvent extends InputEvent
{
    private final int x, y;

    public MouseMoveEvent(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
