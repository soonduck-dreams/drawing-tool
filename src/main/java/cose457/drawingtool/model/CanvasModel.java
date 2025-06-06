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

    /** Update the zOrder field of all shapes based on their index */
    private void updateZOrders() {
        for (int i = 0; i < shapes.size(); i++) {
            shapes.get(i).setZOrder(i);
        }
    }

    public void bringToFront(List<ShapeModel> targets) {
        List<ShapeModel> ordered = shapes.stream()
                .filter(targets::contains)
                .toList();
        shapes.removeAll(ordered);
        shapes.addAll(ordered);
        updateZOrders();
        notifyListeners();
    }

    public void sendToBack(List<ShapeModel> targets) {
        List<ShapeModel> ordered = shapes.stream()
                .filter(targets::contains)
                .toList();
        shapes.removeAll(ordered);
        shapes.addAll(0, ordered);
        updateZOrders();
        notifyListeners();
    }

    public void bringForward(List<ShapeModel> targets) {
        for (int i = shapes.size() - 2; i >= 0; i--) {
            ShapeModel current = shapes.get(i);
            if (targets.contains(current)) {
                ShapeModel next = shapes.get(i + 1);
                if (!targets.contains(next)) {
                    Collections.swap(shapes, i, i + 1);
                }
            }
        }
        updateZOrders();
        notifyListeners();
    }

    public void sendBackward(List<ShapeModel> targets) {
        for (int i = 1; i < shapes.size(); i++) {
            ShapeModel current = shapes.get(i);
            if (targets.contains(current)) {
                ShapeModel prev = shapes.get(i - 1);
                if (!targets.contains(prev)) {
                    Collections.swap(shapes, i, i - 1);
                }
            }
        }
        updateZOrders();
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
