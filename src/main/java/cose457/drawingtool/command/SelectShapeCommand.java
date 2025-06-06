package cose457.drawingtool.command;

import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;

/**
 * Command for selecting a shape. Stores the previously selected shape
 * so the operation can be undone.
 */
public class SelectShapeCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final ShapeViewModel newSelection;
    private ShapeViewModel previousSelection;

    public SelectShapeCommand(CanvasViewModel canvasViewModel, ShapeViewModel newSelection) {
        this.canvasViewModel = canvasViewModel;
        this.newSelection = newSelection;
    }

    @Override
    public void execute() {
        previousSelection = canvasViewModel.getShapeViewModels().stream()
                .filter(ShapeViewModel::isSelected)
                .findFirst()
                .orElse(null);
        canvasViewModel.clearSelection();
        if (newSelection != null) {
            newSelection.setSelected(true);
        }
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        canvasViewModel.clearSelection();
        if (previousSelection != null) {
            previousSelection.setSelected(true);
        }
        canvasViewModel.notifyListeners();
    }
}
