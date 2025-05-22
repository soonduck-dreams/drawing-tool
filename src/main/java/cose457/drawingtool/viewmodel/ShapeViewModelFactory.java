package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.RectangleShape;
import cose457.drawingtool.model.ShapeModel;

public class ShapeViewModelFactory {

    public ShapeViewModel create(ShapeModel model) {
        if (model instanceof RectangleShape rectModel) {
            return new RectangleShapeViewModel(rectModel);
        }

        throw new IllegalArgumentException("Unknown shape model type: " + model.getClass().getSimpleName());
    }
}
