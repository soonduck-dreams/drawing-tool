package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.RectangleShape;
import cose457.drawingtool.view.viewmodelvisitor.ShapeViewModelVisitor;

public class RectangleShapeViewModel extends ShapeViewModel {

    public RectangleShapeViewModel(RectangleShape model) {
        super(model);
    }
}
