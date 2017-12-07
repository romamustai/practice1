package mustaevrf.app.sandbox;

import mustaevrf.app.events.Dispatcher;
import mustaevrf.app.events.Event;
import mustaevrf.app.events.EventHandler;
import mustaevrf.app.events.types.MouseMotionEvent;
import mustaevrf.app.events.types.MousePressedEvent;
import mustaevrf.app.events.types.MouseReleasedEvent;
import mustaevrf.app.layers.Layer;

import java.awt.*;
import java.util.Random;


public class Example extends Layer{

    private String name;
    private Color color;
    private Rectangle box;
    private boolean dragging = false;
    private int px, py;
    private static final Random random = new Random();

    public Example(String name, Color color){
        this.name = name;
        this.color = color;

        box = new Rectangle(random.nextInt(100) + 150, random.nextInt(100) + 250, 120, 240);

    }

    @Override
    public void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onPressed((MousePressedEvent) e));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMoved((MouseMotionEvent) e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onReleased((MouseReleasedEvent) e));
    }

    @Override
    public void onRender(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(box.x, box.y, box.width, box.height);

        graphics.setColor(Color.WHITE);
        graphics.drawString(name, box.x + 15, box.y + 35);
    }

    private boolean onPressed(MousePressedEvent event){
        if (box.contains(new Point(event.getX(), event.getY())))
            dragging = true;
        return dragging;
    }

    private boolean onReleased(MouseReleasedEvent event){
        dragging = false;
        return dragging;
    }

    private boolean onMoved(MouseMotionEvent event){
        if (dragging){
            box.x += event.getX() - px;
            box.y += event.getY() - py;
        }
        px = event.getX();
        py = event.getY();
        return dragging;
    }

}
