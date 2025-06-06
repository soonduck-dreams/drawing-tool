package cose457.drawingtool.view;

import cose457.drawingtool.command.AddShapeCommand;
import cose457.drawingtool.factory.ShapeModelFactory;
import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.model.ShapeType;
import cose457.drawingtool.util.ShapeRenderer;
import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ShapeViewModel;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
    @FXML private Button btnSelect;

    @FXML
    private Canvas drawCanvas;
    private GraphicsContext gc;

    @FXML
    private VBox propertyPanel;

    private CanvasViewModel canvasViewModel = new CanvasViewModel();

    private double startX, startY;
    private boolean isDragging = false;

    @FXML
    public void initialize() {
        gc = drawCanvas.getGraphicsContext2D();

        drawCanvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            isDragging = true;
        });

        drawCanvas.setOnMouseDragged(e -> {
            if (!isDragging) return;
            double curX = e.getX(), curY = e.getY();
            double x = Math.min(startX, curX);
            double y = Math.min(startY, curY);
            double width = Math.abs(curX - startX);
            double height = Math.abs(curY - startY);

            // 미리보기용으로 전체 클리어 → 기존 도형도 다시 그리기
            redrawCanvas(canvasViewModel.get()); // 아래에 구현

            // 현재 도형 타입별 미리보기
            gc.setStroke(javafx.scene.paint.Color.DARKGRAY);
            gc.setLineDashes(4);
            ShapeType type = getSelectedShapeType();
            switch (type) {
                case RECTANGLE:
                    gc.strokeRect(x, y, width, height);
                    break;
                case ELLIPSE:
                    gc.strokeOval(x, y, width, height);
                    break;
                // 기타 도형
            }
            gc.setLineDashes(0); // 대시 초기화
        });

        drawCanvas.setOnMouseReleased(e -> {
            if (!isDragging) return;
            isDragging = false;
            double endX = e.getX(), endY = e.getY();
            double x = Math.min(startX, endX);
            double y = Math.min(startY, endY);
            double width = Math.abs(endX - startX);
            double height = Math.abs(endY - startY);

            // 도형 타입에 따라 Model 생성
            ShapeType type = getSelectedShapeType(); // ComboBox 등에서 선택된 값
            ShapeModel model;
            switch (type) {
                case RECTANGLE:
                    model = ShapeModelFactory.rectangle()
                            .x(x).y(y).width(width).height(height).zOrder(0).build();
                    break;
                case ELLIPSE:
                    model = ShapeModelFactory.ellipse()
                            .x(x).y(y).width(width).height(height).zOrder(0).build();
                    break;
                // 기타 도형 생략
                default:
                    return;
            }

            // Command 생성 및 실행
            AddShapeCommand command = new AddShapeCommand(canvasViewModel.getCanvasModel(), model);
            canvasViewModel.executeCommand(command);
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
