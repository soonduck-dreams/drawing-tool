package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.ImageShape;
import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableValue;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class ImageViewModel extends ShapeViewModel {

    public final Observable<String> imagePath = new ObservableValue<>(null);

    public ImageViewModel(ImageShape model) {
        super(model);
        this.imagePath.set(model.getImagePath());
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
