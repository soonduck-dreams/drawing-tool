package cose457.drawingtool.view.viewmodelvisitor;

import cose457.drawingtool.viewmodel.RectangleShapeViewModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeRenderer implements ShapeViewModelVisitor {

    private final GraphicsContext gc;

    public ShapeRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void visit(RectangleShapeViewModel viewModel) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(
                viewModel.x.get(),
                viewModel.y.get(),
                viewModel.width.get(),
                viewModel.height.get()
        );
    }
}
