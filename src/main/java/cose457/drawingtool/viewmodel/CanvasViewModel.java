package cose457.drawingtool.viewmodel;

import cose457.drawingtool.command.Command;
import cose457.drawingtool.factory.ShapeViewModelFactory;
import cose457.drawingtool.model.CanvasModel;
import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.util.ObservableList;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CanvasViewModel {
    @Getter
    private final CanvasModel canvasModel = new CanvasModel();

    @Getter
    private final ObservableList<ShapeViewModel> shapes = new ObservableList<>();

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public CanvasViewModel() {
        for (ShapeModel model : canvasModel.getShapes()) {
            shapes.add(new ShapeViewModelFactory().create(model));
        }
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        syncFromModel();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
            syncFromModel();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
            syncFromModel();
        }
    }

    private void syncFromModel() {
        List<ShapeModel> models = canvasModel.getShapes();
        List<ShapeViewModel> vms = new ArrayList<>();
        ShapeViewModelFactory factory = new ShapeViewModelFactory();
        for (ShapeModel model : models) {
            vms.add(factory.create(model));
        }
        shapes.setAll(vms);
    }

}
