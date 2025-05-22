package cose457.drawingtool.view.draghandler;

import cose457.drawingtool.viewmodel.ShapeViewModel;
import javafx.scene.canvas.GraphicsContext;

public interface ShapeTool {

    void onMousePressed(double x, double y);
    void onMouseDragged(double x, double y, GraphicsContext gc);
    ShapeViewModel onMouseReleased(double x, double y);
}
