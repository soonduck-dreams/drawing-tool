package cose457.drawingtool.command;

import cose457.drawingtool.model.CanvasModel;
import cose457.drawingtool.model.ShapeModel;

import java.util.ArrayList;
import java.util.List;

public class ChangeZOrderCommand implements Command {
    public enum Type {
        BRING_TO_FRONT,
        SEND_TO_BACK,
        BRING_FORWARD,
        SEND_BACKWARD
    }

    private final CanvasModel canvasModel;
    private final List<ShapeModel> targets;
    private final Type type;
    private List<ShapeModel> previousOrder;

    public ChangeZOrderCommand(CanvasModel canvasModel, List<ShapeModel> targets, Type type) {
        this.canvasModel = canvasModel;
        this.targets = targets;
        this.type = type;
    }

    @Override
    public void execute() {
        previousOrder = new ArrayList<>(canvasModel.get());
        switch (type) {
            case BRING_TO_FRONT -> canvasModel.bringToFront(targets);
            case SEND_TO_BACK -> canvasModel.sendToBack(targets);
            case BRING_FORWARD -> canvasModel.bringForward(targets);
            case SEND_BACKWARD -> canvasModel.sendBackward(targets);
        }
    }

    @Override
    public void undo() {
        if (previousOrder != null) {
            canvasModel.set(previousOrder);
        }
    }
}
