package cose457.drawingtool.command;

import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import cose457.drawingtool.model.ShapeModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Command for moving all currently selected shapes by a delta.
 */
public class MoveSelectedShapesCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final double dx;
    private final double dy;
    private Map<ShapeViewModel, double[]> previousPositions;

    public MoveSelectedShapesCommand(CanvasViewModel canvasViewModel, double dx, double dy) {
        this.canvasViewModel = canvasViewModel;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        previousPositions = new HashMap<>();
        for (ShapeViewModel vm : canvasViewModel.getShapeViewModels()) {
            if (vm.isSelected()) {
                previousPositions.put(vm, new double[]{vm.getModel().getX(), vm.getModel().getY()});
                ShapeModel m = vm.getModel();
                m.setX(m.getX() + dx);
                m.setY(m.getY() + dy);
            }
        }
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        if (previousPositions == null) return;
        for (Map.Entry<ShapeViewModel, double[]> entry : previousPositions.entrySet()) {
            ShapeModel m = entry.getKey().getModel();
            double[] pos = entry.getValue();
            m.setX(pos[0]);
            m.setY(pos[1]);
        }
        canvasViewModel.notifyListeners();
    }
}
