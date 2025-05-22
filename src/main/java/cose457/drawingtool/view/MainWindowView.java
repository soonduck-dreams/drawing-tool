package cose457.drawingtool.view;

import cose457.drawingtool.model.ShapeType;
import cose457.drawingtool.view.draghandler.RectangleTool;
import cose457.drawingtool.view.draghandler.ShapeTool;
import cose457.drawingtool.view.viewmodelvisitor.ShapeRenderer;
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
import java.util.Map;

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

    @FXML
    private VBox propertyPanel;

    private GraphicsContext gc;

    private final CanvasViewModel canvasViewModel = new CanvasViewModel();
    private ShapeRenderer shapeRenderer;

    private final Map<ShapeType, ShapeTool> shapeToolMap = Map.of(
            ShapeType.RECTANGLE, new RectangleTool()
    );
    private ShapeTool currentShapeTool;

    @FXML
    public void initialize() {
        initGraphics();
        initShapeToolSelection();
        initButtonActions();
        initCanvasEvents();
    }

    private void initGraphics() {
        gc = drawCanvas.getGraphicsContext2D();
        shapeRenderer = new ShapeRenderer(gc);
        canvasViewModel.shapes.addListener(this::redrawCanvas);
    }

    private void initShapeToolSelection() {
        // 콤보박스 → ViewModel 연동
        shapeTypeComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    ShapeType shapeType = ShapeType.valueOf(newVal.toUpperCase());
                    canvasViewModel.currentShapeType.set(shapeType);
                });
        // ViewModel → currentShapeTool 연동
        canvasViewModel.currentShapeType.addListener(shapeType -> {
            currentShapeTool = shapeToolMap.get(shapeType);
        });
        // 초기 선택
        shapeTypeComboBox.getSelectionModel().select("Rectangle");
        currentShapeTool = shapeToolMap.get(ShapeType.RECTANGLE);
    }

    private void initButtonActions() {
        btnBringToFront.setOnAction(e -> System.out.println("Bring to Front"));
        btnSendToBack.setOnAction(e -> System.out.println("Send to Back"));
        btnBringForward.setOnAction(e -> System.out.println("Bring Forward"));
        btnSendBackward.setOnAction(e -> System.out.println("Send Backward"));
        btnDelete.setOnAction(e -> System.out.println("Delete"));
        btnSelect.setOnAction(e -> System.out.println("Select Mode"));
    }

    private void initCanvasEvents() {
        drawCanvas.setOnMousePressed(e -> {
            if (currentShapeTool != null) currentShapeTool.onMousePressed(e.getX(), e.getY());
        });
        drawCanvas.setOnMouseDragged(e -> {
            redrawCanvas(canvasViewModel.shapes.get());
            if (currentShapeTool != null) {
                currentShapeTool.onMouseDragged(e.getX(), e.getY(), gc);
            }
        });
        drawCanvas.setOnMouseReleased(e -> {
            if (currentShapeTool != null) {
                ShapeViewModel shape = currentShapeTool.onMouseReleased(e.getX(), e.getY());
                if (shape != null) {
                    canvasViewModel.shapes.add(shape);
                }
            }
            redrawCanvas(canvasViewModel.shapes.get());
        });
    }


    private void redrawCanvas(List<ShapeViewModel> shapes) {
        gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

        for (ShapeViewModel shape : shapes) {
            shape.accept(shapeRenderer);
        }
    }
}
