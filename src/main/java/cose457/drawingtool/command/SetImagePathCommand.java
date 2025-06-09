package cose457.drawingtool.command;

import cose457.drawingtool.model.shape.ImageShape;
import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.ImageViewModel;

/**
 * Command to change the image path of an ImageShape.
 */
public class SetImagePathCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final ImageViewModel viewModel;
    private final String newPath;
    private String previousPath;

    public SetImagePathCommand(CanvasViewModel canvasViewModel, ImageViewModel viewModel, String newPath) {
        this.canvasViewModel = canvasViewModel;
        this.viewModel = viewModel;
        this.newPath = newPath;
    }

    @Override
    public void execute() {
        ImageShape shape = (ImageShape) viewModel.getModel();
        previousPath = shape.getImagePath();
        shape.setImagePath(newPath);
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        if (previousPath == null) return;
        ImageShape shape = (ImageShape) viewModel.getModel();
        shape.setImagePath(previousPath);
        canvasViewModel.notifyListeners();
    }
}
