package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.EllipseShape;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class EllipseViewModel extends ShapeViewModel {

    public EllipseViewModel(EllipseShape model) {
        super(model);
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
