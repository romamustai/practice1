package mustaevrf.app.core;

import mustaevrf.app.events.Event;
import mustaevrf.app.events.types.MouseMotionEvent;
import mustaevrf.app.events.types.MousePressedEvent;
import mustaevrf.app.events.types.MouseReleasedEvent;
import mustaevrf.app.layers.Layer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Window extends Canvas{

    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private JFrame frame;
    private List<Layer> layers = new ArrayList<Layer>();

    public Window(String name, int width, int height){
        setPreferredSize(new Dimension(width, height));
        init(name);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
                onEvent(event);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
                onEvent(event);
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                MouseMotionEvent event = new MouseMotionEvent(e.getX(), e.getY(), true);
                onEvent(event);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                MouseMotionEvent event = new MouseMotionEvent(e.getX(), e.getY(), false);
                onEvent(event);
            }
        });

        render();
    }

    private void init(String name){
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void render(){
        if (bufferStrategy == null){
            createBufferStrategy(3);
        }
        bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0, getWidth(), getHeight());
        onRender(graphics);
        graphics.dispose();
        bufferStrategy.show();

        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {

        }
        EventQueue.invokeLater(() -> render());
    }

    private void onRender(Graphics graphics) {
        for (int i = 0; i < layers.size(); i++)
            layers.get(i).onRender(graphics);
    }

    private void onEvent(Event event){
        for (int i = layers.size() - 1; i >= 0; i--)
            layers.get(i).onEvent(event);

    }

    public void addLayer(Layer layer){
        layers.add(layer);
    }

}
