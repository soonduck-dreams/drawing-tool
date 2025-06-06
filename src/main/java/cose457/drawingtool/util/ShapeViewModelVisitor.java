package cose457.drawingtool.util;

import cose457.drawingtool.viewmodel.*;

public interface ShapeViewModelVisitor {

    void visit(RectangleViewModel viewModel);
    void visit(EllipseViewModel viewModel);
    void visit(LineViewModel viewModel);
    void visit(TextViewModel viewModel);
    void visit(ImageViewModel viewModel);
}
