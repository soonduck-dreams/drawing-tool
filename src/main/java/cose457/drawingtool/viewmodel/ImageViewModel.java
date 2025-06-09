package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.ImageShape;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class ImageViewModel extends ShapeViewModel {

    public ImageViewModel(ImageShape model) {
        super(model);
    }

    public String getImagePath() {
        return ((ImageShape) model).getImagePath();
    }


    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
