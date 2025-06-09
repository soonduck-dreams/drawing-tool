package cose457.drawingtool.command;

import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import cose457.drawingtool.model.ShapeModel;

/**
 * Command for temporarily translating selected shapes without affecting the undo stack.
 */
public class TranslateSelectedShapesCommand implements Command {
    private final CanvasViewModel canvasViewModel;
    private final double dx;
    private final double dy;

    public TranslateSelectedShapesCommand(CanvasViewModel canvasViewModel, double dx, double dy) {
        this.canvasViewModel = canvasViewModel;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        if (dx == 0 && dy == 0) return;
        for (ShapeViewModel vm : canvasViewModel.getShapeViewModels()) {
            if (vm.isSelected()) {
                ShapeModel m = vm.getModel();
                m.setX(m.getX() + dx);
                m.setY(m.getY() + dy);
            }
        }
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        if (dx == 0 && dy == 0) return;
        for (ShapeViewModel vm : canvasViewModel.getShapeViewModels()) {
            if (vm.isSelected()) {
                ShapeModel m = vm.getModel();
                m.setX(m.getX() - dx);
                m.setY(m.getY() - dy);
            }
        }
        canvasViewModel.notifyListeners();
    }
}
