package cose457.drawingtool.factory;

import cose457.drawingtool.model.shape.*;
import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.viewmodel.*;

public class ShapeViewModelFactory {

    public ShapeViewModel create(ShapeModel model) {
        if (model instanceof RectangleShape rect) {
            return new RectangleViewModel(rect);
        }
        if (model instanceof EllipseShape ellipse) {
            return new EllipseViewModel(ellipse);
        }
        if (model instanceof LineShape line) {
            return new LineViewModel(line);
        }
        if (model instanceof TextShape text) {
            return new TextViewModel(text);
        }
        if (model instanceof ImageShape image) {
            return new ImageViewModel(image);
        }

        throw new IllegalArgumentException("Unknown shape model type: " + model.getClass().getSimpleName());
    }
}
