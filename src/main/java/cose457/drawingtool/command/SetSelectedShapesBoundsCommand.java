package cose457.drawingtool.command;

import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import cose457.drawingtool.model.ShapeModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command for resizing and moving currently selected shapes
 * based on a new bounding box.
 */
public class SetSelectedShapesBoundsCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final double newX;
    private final double newY;
    private final double newWidth;
    private final double newHeight;

    private Map<ShapeViewModel, double[]> previousBounds;

    public SetSelectedShapesBoundsCommand(CanvasViewModel canvasViewModel,
                                          double newX, double newY,
                                          double newWidth, double newHeight) {
        this.canvasViewModel = canvasViewModel;
        this.newX = newX;
        this.newY = newY;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public void execute() {
        List<ShapeViewModel> selected = canvasViewModel.getShapeViewModels().stream()
                .filter(ShapeViewModel::isSelected)
                .toList();
        if (selected.isEmpty()) return;

        previousBounds = new HashMap<>();

        double minX = selected.stream()
                .mapToDouble(vm -> Math.min(vm.getX(), vm.getX() + vm.getWidth()))
                .min().orElse(0);
        double minY = selected.stream()
                .mapToDouble(vm -> Math.min(vm.getY(), vm.getY() + vm.getHeight()))
                .min().orElse(0);
        double maxX = selected.stream()
                .mapToDouble(vm -> Math.max(vm.getX(), vm.getX() + vm.getWidth()))
                .max().orElse(0);
        double maxY = selected.stream()
                .mapToDouble(vm -> Math.max(vm.getY(), vm.getY() + vm.getHeight()))
                .max().orElse(0);

        double oldWidth = maxX - minX;
        double oldHeight = maxY - minY;

        for (ShapeViewModel vm : selected) {
            ShapeModel m = vm.getModel();
            previousBounds.put(vm, new double[]{m.getX(), m.getY(), m.getWidth(), m.getHeight()});

            double sx1 = Math.min(m.getX(), m.getX() + m.getWidth());
            double sy1 = Math.min(m.getY(), m.getY() + m.getHeight());
            double sx2 = Math.max(m.getX(), m.getX() + m.getWidth());
            double sy2 = Math.max(m.getY(), m.getY() + m.getHeight());

            double nx1 = (oldWidth == 0) ? newX + (sx1 - minX) : newX + (sx1 - minX) / oldWidth * newWidth;
            double ny1 = (oldHeight == 0) ? newY + (sy1 - minY) : newY + (sy1 - minY) / oldHeight * newHeight;
            double nx2 = (oldWidth == 0) ? newX + (sx2 - minX) : newX + (sx2 - minX) / oldWidth * newWidth;
            double ny2 = (oldHeight == 0) ? newY + (sy2 - minY) : newY + (sy2 - minY) / oldHeight * newHeight;

            double newSX = m.getWidth() >= 0 ? nx1 : nx2;
            double newSY = m.getHeight() >= 0 ? ny1 : ny2;
            double newW = m.getWidth() >= 0 ? nx2 - nx1 : nx1 - nx2;
            double newH = m.getHeight() >= 0 ? ny2 - ny1 : ny1 - ny2;

            m.setX(newSX);
            m.setY(newSY);
            m.setWidth(newW);
            m.setHeight(newH);
        }
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        if (previousBounds == null) return;
        for (Map.Entry<ShapeViewModel, double[]> entry : previousBounds.entrySet()) {
            ShapeModel m = entry.getKey().getModel();
            double[] b = entry.getValue();
            m.setX(b[0]);
            m.setY(b[1]);
            m.setWidth(b[2]);
            m.setHeight(b[3]);
        }
        canvasViewModel.notifyListeners();
    }
}
