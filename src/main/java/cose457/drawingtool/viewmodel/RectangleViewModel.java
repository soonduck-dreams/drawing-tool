package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.RectangleShape;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class RectangleViewModel extends ShapeViewModel {

    public RectangleViewModel(RectangleShape model) {
        super(model);
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
