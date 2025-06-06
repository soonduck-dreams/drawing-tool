package cose457.drawingtool.view;

import cose457.drawingtool.model.ShapeType;
import cose457.drawingtool.util.ShapeRenderer;
import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

import java.util.List;

public class MainWindowView {
    @FXML
    private ToolBar toolbar;

    @FXML private ComboBox<String> shapeTypeComboBox;
    @FXML private Button btnBringToFront;
    @FXML private Button btnSendToBack;
    @FXML private Button btnBringForward;
    @FXML private Button btnSendBackward;
    @FXML private Button btnDelete;
    @FXML private ToggleButton btnSelect;

    @FXML
    private Canvas drawCanvas;
    private GraphicsContext gc;

    @FXML
    private VBox propertyPanel;

    private CanvasViewModel canvasViewModel = new CanvasViewModel();

    private double startX, startY;
    private boolean isDragging = false;
    private boolean isSelecting = false;
    private boolean isMoving = false;
    private double lastX, lastY;

    @FXML
    public void initialize() {
        gc = drawCanvas.getGraphicsContext2D();

        btnSelect.selectedProperty().addListener((obs, o, n) -> {
            isDragging = false;
            isSelecting = false;
            isMoving = false;
        });

        shapeTypeComboBox.valueProperty().addListener((obs, o, n) -> {
            btnSelect.setSelected(false);
        });

        drawCanvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            if (btnSelect.isSelected()) {
                if (canvasViewModel.hasSelectedShapeAt(startX, startY)) {
                    isMoving = true;
                    lastX = startX;
                    lastY = startY;
                } else {
                    isSelecting = true;
                }
            } else {
                isDragging = true;
            }
        });

        drawCanvas.setOnMouseDragged(e -> {
            double curX = e.getX(), curY = e.getY();
            if (btnSelect.isSelected()) {
                if (isMoving) {
                    double dx = curX - lastX;
                    double dy = curY - lastY;
                    canvasViewModel.moveSelectedShapes(dx, dy);
                    lastX = curX;
                    lastY = curY;
                } else if (isSelecting) {
                    redrawCanvas(canvasViewModel.get());
                    double x = Math.min(startX, curX);
                    double y = Math.min(startY, curY);
                    double width = Math.abs(curX - startX);
                    double height = Math.abs(curY - startY);
                    gc.setStroke(javafx.scene.paint.Color.DARKGRAY);
                    gc.setLineDashes(4);
                    gc.strokeRect(x, y, width, height);
                    gc.setLineDashes(0);
                }
            } else if (isDragging) {
                double x = Math.min(startX, curX);
                double y = Math.min(startY, curY);
                double width = Math.abs(curX - startX);
                double height = Math.abs(curY - startY);

                redrawCanvas(canvasViewModel.get());

                gc.setStroke(javafx.scene.paint.Color.DARKGRAY);
                gc.setLineDashes(4);
                ShapeType type = getSelectedShapeType();
                switch (type) {
                    case RECTANGLE -> gc.strokeRect(x, y, width, height);
                    case ELLIPSE -> gc.strokeOval(x, y, width, height);
                }
                gc.setLineDashes(0);
            }
        });

        drawCanvas.setOnMouseReleased(e -> {
            double endX = e.getX(), endY = e.getY();
            if (btnSelect.isSelected()) {
                if (isMoving) {
                    isMoving = false;
                } else if (isSelecting) {
                    isSelecting = false;
                    double width = Math.abs(endX - startX);
                    double height = Math.abs(endY - startY);
                    if (width == 0 && height == 0) {
                        canvasViewModel.selectShapeAt(endX, endY);
                    } else {
                        double x = Math.min(startX, endX);
                        double y = Math.min(startY, endY);
                        canvasViewModel.selectShapesInArea(x, y, width, height);
                    }
                }
            } else if (isDragging) {
                isDragging = false;
                double width = Math.abs(endX - startX);
                double height = Math.abs(endY - startY);
                if (width > 0 || height > 0) {
                    double x = Math.min(startX, endX);
                    double y = Math.min(startY, endY);
                    ShapeType type = getSelectedShapeType();
                    canvasViewModel.addShape(type, x, y, width, height);
                }
            }
        });

        canvasViewModel.addListener(this::redrawCanvas);
    }

    private ShapeType getSelectedShapeType() {
        String selected = shapeTypeComboBox.getSelectionModel().getSelectedItem();
        if (selected == null) return ShapeType.RECTANGLE; // 기본값 지정
        try {
            return ShapeType.valueOf(selected.toUpperCase());
        } catch (Exception e) {
            return ShapeType.RECTANGLE; // 잘못된 값이면 기본값
        }
    }

    private void redrawCanvas(List<ShapeViewModel> shapeViewModels) {
        gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
        for (var shapeViewModel : shapeViewModels) {
            shapeViewModel.accept(new ShapeRenderer(gc));
        }
    }

}
