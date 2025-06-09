package cose457.drawingtool.command;

import cose457.drawingtool.model.CanvasModel;
import cose457.drawingtool.model.ShapeModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteShapesCommand implements Command {

    private final CanvasModel canvasModel;
    private final List<ShapeModel> targets;
    private List<ShapeModel> previousOrder;

    public DeleteShapesCommand(CanvasModel canvasModel, List<ShapeModel> targets) {
        this.canvasModel = canvasModel;
        this.targets = targets;
    }

    @Override
    public void execute() {
        previousOrder = new ArrayList<>(canvasModel.get());
        for (ShapeModel shape : targets) {
            canvasModel.removeShape(shape);
        }
    }

    @Override
    public void undo() {
        if (previousOrder != null) {
            canvasModel.set(previousOrder);
        }
    }
}
