package cose457.drawingtool.command;

import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import cose457.drawingtool.model.ShapeModel;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Command for changing the fill color of currently selected shapes.
 */
public class SetSelectedShapesFillColorCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final Color newColor;
    private Map<ShapeViewModel, Color> previousColors;

    public SetSelectedShapesFillColorCommand(CanvasViewModel canvasViewModel, Color newColor) {
        this.canvasViewModel = canvasViewModel;
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        previousColors = new HashMap<>();
        for (ShapeViewModel vm : canvasViewModel.getShapeViewModels()) {
            if (vm.isSelected()) {
                ShapeModel m = vm.getModel();
                previousColors.put(vm, m.getFillColor());
                m.setFillColor(newColor);
            }
        }
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        if (previousColors == null) return;
        for (Map.Entry<ShapeViewModel, Color> entry : previousColors.entrySet()) {
            ShapeModel m = entry.getKey().getModel();
            m.setFillColor(entry.getValue());
        }
        canvasViewModel.notifyListeners();
    }
}
