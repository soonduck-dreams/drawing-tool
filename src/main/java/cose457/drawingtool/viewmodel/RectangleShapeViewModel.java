package cose457.drawingtool.viewmodel;

import cose457.drawingtool.view.viewmodelvisitor.ShapeViewModelVisitor;

public class RectangleShapeViewModel extends ShapeViewModel {

    public RectangleShapeViewModel(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
