package cose457.drawingtool.view.draghandler;

import cose457.drawingtool.viewmodel.RectangleShapeViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleTool implements ShapeTool {
    private double startX, startY;

    @Override
    public void onMousePressed(double x, double y) {
        startX = x; startY = y;
    }

    @Override
    public void onMouseDragged(double x, double y, GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeRect(
                Math.min(startX, x), Math.min(startY, y),
                Math.abs(x - startX), Math.abs(y - startY)
        );
    }

    @Override
    public ShapeViewModel onMouseReleased(double x, double y) {
        double w = Math.abs(x - startX);
        double h = Math.abs(y - startY);
        if (w < 2 || h < 2) return null;
        return new RectangleShapeViewModel(
                Math.min(startX, x), Math.min(startY, y), w, h
        );
    }
}
