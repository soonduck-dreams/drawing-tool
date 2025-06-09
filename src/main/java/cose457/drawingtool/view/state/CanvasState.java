package cose457.drawingtool.view.state;

import javafx.scene.input.MouseEvent;

public interface CanvasState {
    void onMousePressed(MouseEvent e);
    void onMouseDragged(MouseEvent e);
    void onMouseReleased(MouseEvent e);
}
