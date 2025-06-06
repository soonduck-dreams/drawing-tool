package cose457.drawingtool.viewmodel;

import cose457.drawingtool.command.Command;
import cose457.drawingtool.factory.ShapeViewModelFactory;
import cose457.drawingtool.model.CanvasModel;
import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableList;
import lombok.Getter;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

public class CanvasViewModel implements Observable<List<ShapeViewModel>> {
    @Getter
    private final CanvasModel canvasModel = new CanvasModel();

    @Getter
    private final List<ShapeViewModel> shapeViewModels = new ArrayList<>();
    private final List<Consumer<List<ShapeViewModel>>> listeners = new ArrayList<>();

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    ShapeViewModelFactory shapeViewModelFactory = new ShapeViewModelFactory();

    public CanvasViewModel() {
        canvasModel.addListener(shapeModels -> {
            this.set(shapeModels.stream()
                            .map(shapeModel -> shapeViewModelFactory.create(shapeModel))
                            .toList());
        });
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    @Override
    public List<ShapeViewModel> get() {
        return shapeViewModels;
    }

    @Override
    public void set(List<ShapeViewModel> value) {
        shapeViewModels.clear();
        shapeViewModels.addAll(value);
        notifyListeners();
    }

    @Override
    public void addListener(Consumer<List<ShapeViewModel>> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Consumer<List<ShapeViewModel>> listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (var listener : listeners) {
            listener.accept(shapeViewModels);
        }
    }
}
