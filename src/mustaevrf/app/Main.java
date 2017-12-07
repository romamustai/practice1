package mustaevrf.app;

import mustaevrf.app.core.Window;
import mustaevrf.app.sandbox.Example;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Window window = new Window("Practice", 960,640);
        window.addLayer(new Example("Layer-one", Color.orange));
        window.addLayer(new Example("Layer-two", Color.cyan));
    }
}
