package cose457.drawingtool.viewmodel;

import cose457.drawingtool.command.AddShapeCommand;
import cose457.drawingtool.command.Command;
import cose457.drawingtool.command.SelectShapeCommand;
import cose457.drawingtool.command.MoveSelectedShapesCommand;
import cose457.drawingtool.command.TranslateSelectedShapesCommand;
import cose457.drawingtool.command.SelectShapesInAreaCommand;
import cose457.drawingtool.command.ChangeZOrderCommand;
import cose457.drawingtool.command.SetSelectedShapesBoundsCommand;
import cose457.drawingtool.command.SetTextCommand;
import cose457.drawingtool.viewmodel.TextViewModel;
import cose457.drawingtool.command.SetSelectedShapesFillColorCommand;
import cose457.drawingtool.command.DeleteShapesCommand;
import cose457.drawingtool.factory.ShapeModelFactory;
import cose457.drawingtool.factory.ShapeViewModelFactory;
import cose457.drawingtool.model.CanvasModel;
import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.model.ShapeType;
import cose457.drawingtool.util.Observable;
import lombok.Getter;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

public class CanvasViewModel implements Observable<List<ShapeViewModel>> {
    @Getter
    private final CanvasModel canvasModel = new CanvasModel();

    @Getter
    private final List<ShapeViewModel> shapeViewModels = new ArrayList<>();
    private final List<Consumer<List<ShapeViewModel>>> listeners = new ArrayList<>();

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    private final ShapeViewModelFactory shapeViewModelFactory = new ShapeViewModelFactory();

    public CanvasViewModel() {
        canvasModel.addListener(shapeModels -> {
            this.set(shapeModels.stream()
                            .map(shapeModel -> shapeViewModelFactory.create(shapeModel))
                            .toList());
        });
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    public void clearSelection() {
        for (ShapeViewModel vm : shapeViewModels) {
            vm.setSelected(false);
        }
    }

    public boolean hasSelectedShapeAt(double x, double y) {
        return shapeViewModels.stream()
                .anyMatch(vm -> vm.isSelected() && contains(vm, x, y));
    }

    private List<ShapeModel> getSelectedModels() {
        return shapeViewModels.stream()
                .filter(ShapeViewModel::isSelected)
                .map(ShapeViewModel::getModel)
                .toList();
    }

    public void moveSelectedShapes(double dx, double dy) {
        Command command = new MoveSelectedShapesCommand(this, dx, dy);
        executeCommand(command);
    }

    /**
     * Move selected shapes directly without recording history.
     * This is used for drag previews before the actual command is issued.
     */
    public void translateSelectedShapes(double dx, double dy) {
        Command command = new TranslateSelectedShapesCommand(this, dx, dy);
        command.execute();
    }


    public void bringSelectedToFront() {
        Command command = new ChangeZOrderCommand(canvasModel, getSelectedModels(), ChangeZOrderCommand.Type.BRING_TO_FRONT);
        executeCommand(command);
    }

    public void sendSelectedToBack() {
        Command command = new ChangeZOrderCommand(canvasModel, getSelectedModels(), ChangeZOrderCommand.Type.SEND_TO_BACK);
        executeCommand(command);
    }

    public void bringSelectedForward() {
        Command command = new ChangeZOrderCommand(canvasModel, getSelectedModels(), ChangeZOrderCommand.Type.BRING_FORWARD);
        executeCommand(command);
    }

    public void sendSelectedBackward() {
        Command command = new ChangeZOrderCommand(canvasModel, getSelectedModels(), ChangeZOrderCommand.Type.SEND_BACKWARD);
        executeCommand(command);
    }

    public void deleteSelectedShapes() {
        List<ShapeModel> targets = getSelectedModels();
        if (targets.isEmpty()) return;
        Command command = new DeleteShapesCommand(canvasModel, targets);
        executeCommand(command);
    }

    /**
     * Update the text of the given TextViewModel via a command.
     */
    public void setText(TextViewModel viewModel, String newText) {
        Command command = new SetTextCommand(this, viewModel, newText);
        executeCommand(command);
    }

    public void setSelectedShapesBounds(double x, double y, double width, double height) {
        Command command = new SetSelectedShapesBoundsCommand(this, x, y, width, height);
        executeCommand(command);
    }

    public void setSelectedShapesFillColor(Color color) {
        Command command = new SetSelectedShapesFillColorCommand(this, color);
        executeCommand(command);
    }

    public void selectShapesInArea(double x, double y, double width, double height) {
        Command command = new SelectShapesInAreaCommand(this, x, y, width, height);
        executeCommand(command);
    }

    /**
     * Select the top-most shape that contains the given point.
     */
    public void selectShapeAt(double x, double y) {
        ShapeViewModel target = shapeViewModels.stream()
                .filter(vm -> contains(vm, x, y))
                .max((a, b) -> Integer.compare(a.getZOrder(), b.getZOrder()))
                .orElse(null);

        SelectShapeCommand command = new SelectShapeCommand(this, target);
        executeCommand(command);
    }

    private boolean contains(ShapeViewModel vm, double px, double py) {
        double vx = vm.getX();
        double vy = vm.getY();
        double vw = vm.getWidth();
        double vh = vm.getHeight();

        double x1 = Math.min(vx, vx + vw);
        double y1 = Math.min(vy, vy + vh);
        double x2 = Math.max(vx, vx + vw);
        double y2 = Math.max(vy, vy + vh);

        return px >= x1 && px <= x2 && py >= y1 && py <= y2;
    }

    /**
     * Create a shape model based on user parameters and add it to the canvas
     * through a command. The view only needs to provide the primitive data
     * required to create the shape.
     */
    public void addShape(ShapeType type, double x, double y, double width, double height) {
        ShapeModel model;
        switch (type) {
            case RECTANGLE -> model = ShapeModelFactory.rectangle()
                    .x(x).y(y).width(width).height(height)
                    .fillColor(Color.WHITE)
                    .zOrder(0).build();
            case ELLIPSE -> model = ShapeModelFactory.ellipse()
                    .x(x).y(y).width(width).height(height)
                    .fillColor(Color.WHITE)
                    .zOrder(0).build();
            case LINE -> model = ShapeModelFactory.line()
                    .x(x).y(y).width(width).height(height)
                    .fillColor(Color.BLACK)
                    .zOrder(0).build();
            case TEXT -> model = ShapeModelFactory.text()
                    .x(x).y(y).width(width).height(height)
                    .fillColor(Color.BLACK)
                    .zOrder(0).build();
            case IMAGE -> model = ShapeModelFactory.image()
                    .x(x).y(y).width(width).height(height)
                    .fillColor(Color.TRANSPARENT)
                    .zOrder(0).build();
            default -> {
                return;
            }
        }

        AddShapeCommand command = new AddShapeCommand(canvasModel, model);
        executeCommand(command);
    }

    @Override
    public List<ShapeViewModel> get() {
        return shapeViewModels;
    }

    @Override
    public void set(List<ShapeViewModel> value) {
        shapeViewModels.clear();
        shapeViewModels.addAll(value);
        notifyListeners();
    }

    @Override
    public void addListener(Consumer<List<ShapeViewModel>> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Consumer<List<ShapeViewModel>> listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (var listener : listeners) {
            listener.accept(shapeViewModels);
        }
    }
}
