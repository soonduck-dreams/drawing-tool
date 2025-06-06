package cose457.drawingtool.command;

import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;

import java.util.List;

/**
 * Command for selecting all shapes within a given rectangular area.
 */
public class SelectShapesInAreaCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private List<ShapeViewModel> previousSelection;

    public SelectShapesInAreaCommand(CanvasViewModel canvasViewModel,
                                     double x, double y,
                                     double width, double height) {
        this.canvasViewModel = canvasViewModel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void execute() {
        previousSelection = canvasViewModel.getShapeViewModels().stream()
                .filter(ShapeViewModel::isSelected)
                .toList();
        canvasViewModel.clearSelection();
        double x2 = x + width;
        double y2 = y + height;
        for (ShapeViewModel vm : canvasViewModel.getShapeViewModels()) {
            double vx = vm.getX();
            double vy = vm.getY();
            double vw = vm.getWidth();
            double vh = vm.getHeight();

            double sx1 = Math.min(vx, vx + vw);
            double sy1 = Math.min(vy, vy + vh);
            double sx2 = Math.max(vx, vx + vw);
            double sy2 = Math.max(vy, vy + vh);

            if (sx1 >= x && sy1 >= y && sx2 <= x2 && sy2 <= y2) {
                vm.setSelected(true);
            }
        }
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        canvasViewModel.clearSelection();
        for (ShapeViewModel vm : previousSelection) {
            vm.setSelected(true);
        }
        canvasViewModel.notifyListeners();
    }
}
