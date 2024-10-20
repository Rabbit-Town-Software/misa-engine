package core;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InputHandler
{

    public static void setupKeyBindings(JComponent component, String key, Runnable onPress, Runnable onRelease)
    {
        // Key pressed
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key + "Press");
        component.getActionMap().put(key + "Press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPress.run();
            }
        });

        // Key released
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + key), key + "Release");
        component.getActionMap().put(key + "Release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRelease.run();
            }
        });
    }
}
