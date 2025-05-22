package cose457.drawingtool.model;

import java.util.ArrayList;
import java.util.List;

public class CanvasModel {

    private final List<ShapeModel> shapes = new ArrayList<>();

    public List<ShapeModel> getShapes() { return shapes; }

    public void addShape(ShapeModel shape) { shapes.add(shape); }
    public void removeShape(ShapeModel shape) { shapes.remove(shape); }
}
