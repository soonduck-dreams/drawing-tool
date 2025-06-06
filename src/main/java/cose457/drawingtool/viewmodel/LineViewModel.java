package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.LineShape;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class LineViewModel extends ShapeViewModel {

    public LineViewModel(LineShape model) {
        super(model);
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
