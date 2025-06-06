package cose457.drawingtool.command;

import cose457.drawingtool.model.shape.TextShape;
import cose457.drawingtool.viewmodel.CanvasViewModel;
import cose457.drawingtool.viewmodel.TextViewModel;

/**
 * Command to modify the text of a TextShape through its view model.
 */
public class SetTextCommand implements Command {

    private final CanvasViewModel canvasViewModel;
    private final TextViewModel viewModel;
    private final String newText;
    private String previousText;

    public SetTextCommand(CanvasViewModel canvasViewModel, TextViewModel viewModel, String newText) {
        this.canvasViewModel = canvasViewModel;
        this.viewModel = viewModel;
        this.newText = newText;
    }

    @Override
    public void execute() {
        TextShape shape = (TextShape) viewModel.getModel();
        previousText = shape.getText();
        shape.setText(newText);
        canvasViewModel.notifyListeners();
    }

    @Override
    public void undo() {
        if (previousText == null) return;
        TextShape shape = (TextShape) viewModel.getModel();
        shape.setText(previousText);
        canvasViewModel.notifyListeners();
    }
}
