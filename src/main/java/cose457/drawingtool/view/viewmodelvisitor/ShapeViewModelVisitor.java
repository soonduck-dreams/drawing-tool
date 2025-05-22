package cose457.drawingtool.view.viewmodelvisitor;

import cose457.drawingtool.viewmodel.RectangleShapeViewModel;

public interface ShapeViewModelVisitor {
    void visit(RectangleShapeViewModel viewModel);
}
