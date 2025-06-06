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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.function.Consumer;

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

        propertyPanel.setSpacing(8);
        propertyPanel.setStyle("-fx-padding:10;-fx-border-color:#cccccc;-fx-background-color:#f8f8f8;");

        shapeTypeComboBox.getSelectionModel().select("Rectangle");

        btnSelect.selectedProperty().addListener((obs, o, n) -> {
            isDragging = false;
            isSelecting = false;
            isMoving = false;
        });

        shapeTypeComboBox.valueProperty().addListener((obs, o, n) -> {
            btnSelect.setSelected(false);
        });

        btnBringToFront.setOnAction(e -> canvasViewModel.bringSelectedToFront());
        btnSendToBack.setOnAction(e -> canvasViewModel.sendSelectedToBack());
        btnBringForward.setOnAction(e -> canvasViewModel.bringSelectedForward());
        btnSendBackward.setOnAction(e -> canvasViewModel.sendSelectedBackward());

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
                    case LINE -> gc.strokeLine(startX, startY, curX, curY);
                    case TEXT, IMAGE -> gc.strokeRect(x, y, width, height);
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
                ShapeType type = getSelectedShapeType();
                if (type == ShapeType.LINE) {
                    double width = endX - startX;
                    double height = endY - startY;
                    if (width != 0 || height != 0) {
                        canvasViewModel.addShape(type, startX, startY, width, height);
                    }
                } else {
                    double width = Math.abs(endX - startX);
                    double height = Math.abs(endY - startY);
                    if (width > 0 || height > 0) {
                        double x = Math.min(startX, endX);
                        double y = Math.min(startY, endY);
                        canvasViewModel.addShape(type, x, y, width, height);
                    }
                }
            }
        });

        canvasViewModel.addListener(vms -> {
            redrawCanvas(vms);
            updatePropertyPanel(vms);
        });
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

    private void updatePropertyPanel(List<ShapeViewModel> shapeViewModels) {
        propertyPanel.getChildren().clear();

        List<ShapeViewModel> selected = shapeViewModels.stream()
                .filter(ShapeViewModel::isSelected)
                .toList();

        if (selected.isEmpty()) {
            propertyPanel.getChildren().add(new Label("No shape selected"));
            return;
        }

        double[] bounds = calculateBounds(selected);
        double[] current = Arrays.copyOf(bounds, 4);

        if (selected.size() == 1) {
            Label title = new Label("Shape");
            title.setStyle("-fx-font-weight: bold;");
            propertyPanel.getChildren().add(title);
        } else {
            Label title = new Label("여러 개의 도형");
            title.setStyle("-fx-font-weight: bold;");
            propertyPanel.getChildren().add(title);
        }

        addField("X", bounds[0], v -> { current[0] = v; applyBounds(current); });
        addField("Y", bounds[1], v -> { current[1] = v; applyBounds(current); });
        addField("W", bounds[2], v -> { current[2] = v; applyBounds(current); });
        addField("H", bounds[3], v -> { current[3] = v; applyBounds(current); });

        Color initialColor = selected.get(0).getFillColor();
        addColorField(initialColor);
    }

    private void applyBounds(double[] b) {
        canvasViewModel.setSelectedShapesBounds(b[0], b[1], b[2], b[3]);
    }

    private HBox addField(String name, double value, Consumer<Double> updater) {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(name + ":");
        TextField field = new TextField(String.format("%.1f", value));
        field.setPrefWidth(70);
        Runnable commit = () -> {
            try {
                double v = Double.parseDouble(field.getText());
                updater.accept(v);
            } catch (NumberFormatException ignore) {
                field.setText(String.format("%.1f", value));
            }
        };
        field.setOnAction(e -> commit.run());
        field.focusedProperty().addListener((obs, o, n) -> { if (!n) commit.run(); });
        row.getChildren().addAll(label, field);
        propertyPanel.getChildren().add(row);
        return row;
    }

    private HBox addColorField(Color value) {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Color:");
        ColorPicker picker = new ColorPicker(value);
        picker.setOnAction(e -> applyFillColor(picker.getValue()));
        row.getChildren().addAll(label, picker);
        propertyPanel.getChildren().add(row);
        return row;
    }

    private void applyFillColor(Color color) {
        canvasViewModel.setSelectedShapesFillColor(color);
    }

    private double[] calculateBounds(List<ShapeViewModel> vms) {
        double minX = vms.stream()
                .mapToDouble(vm -> Math.min(vm.getX(), vm.getX() + vm.getWidth()))
                .min().orElse(0);
        double minY = vms.stream()
                .mapToDouble(vm -> Math.min(vm.getY(), vm.getY() + vm.getHeight()))
                .min().orElse(0);
        double maxX = vms.stream()
                .mapToDouble(vm -> Math.max(vm.getX(), vm.getX() + vm.getWidth()))
                .max().orElse(0);
        double maxY = vms.stream()
                .mapToDouble(vm -> Math.max(vm.getY(), vm.getY() + vm.getHeight()))
                .max().orElse(0);
        return new double[]{minX, minY, maxX - minX, maxY - minY};
    }

}
