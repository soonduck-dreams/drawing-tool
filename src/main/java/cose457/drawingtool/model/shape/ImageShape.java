package cose457.drawingtool.model.shape;

import cose457.drawingtool.model.ShapeModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageShape extends ShapeModel {

    protected String imagePath;

    public ImageShape(double x, double y, double width, double height, int zOrder, String imagePath) {
        super(x, y, width, height, zOrder);
        this.imagePath = imagePath;
    }
}
