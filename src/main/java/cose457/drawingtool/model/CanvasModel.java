package cose457.drawingtool.model;

import cose457.drawingtool.util.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CanvasModel implements Observable<List<ShapeModel>> {

    private final List<ShapeModel> shapes = new ArrayList<>();
    private final List<Consumer<List<ShapeModel>>> listeners = new ArrayList<>();

    @Override
    public List<ShapeModel> get() {
        return shapes;
    }

    @Override
    public void set(List<ShapeModel> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
        notifyListeners();
    }

    public void addShape(ShapeModel shape) {
        shapes.add(shape);
        notifyListeners();
    }

    public void removeShape(ShapeModel shape) {
        shapes.remove(shape);
        notifyListeners();
    }

    @Override
    public void addListener(Consumer<List<ShapeModel>> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Consumer<List<ShapeModel>> listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (var listener : listeners) {
            listener.accept(
                    Collections.unmodifiableList(shapes)
            );
        }
    }
}
