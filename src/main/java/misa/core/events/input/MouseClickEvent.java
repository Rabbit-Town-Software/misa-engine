package misa.core.events.input;

public class MouseClickEvent extends InputEvent
{
    private final int button;
    private final int x, y;

    public MouseClickEvent(int button, int x, int y)
    {
        this.button = button;
        this.x = x;
        this.y = y;
    }

    public int getButton()
    {
        return button;
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
