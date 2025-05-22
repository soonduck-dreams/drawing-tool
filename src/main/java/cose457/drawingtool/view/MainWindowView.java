package cose457.drawingtool.view;

import cose457.drawingtool.model.ShapeType;
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

    @FXML
    public void initialize() {

    }

}
