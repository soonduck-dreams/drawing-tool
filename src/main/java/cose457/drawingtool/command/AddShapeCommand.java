package cose457.drawingtool.command;

import cose457.drawingtool.model.CanvasModel;
import cose457.drawingtool.model.ShapeModel;

public class AddShapeCommand implements Command {

    private final CanvasModel canvasModel;
    private final ShapeModel shapeModel;

    public AddShapeCommand(CanvasModel canvasModel, ShapeModel shapeModel) {
        this.canvasModel = canvasModel;
        this.shapeModel = shapeModel;
    }

    @Override
    public void execute() {
        canvasModel.addShape(shapeModel);
    }

    @Override
    public void undo() {
        canvasModel.removeShape(shapeModel);
    }
}
