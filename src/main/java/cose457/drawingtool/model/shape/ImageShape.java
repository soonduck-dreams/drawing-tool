package cose457.drawingtool.model.shape;

import cose457.drawingtool.model.ShapeModel;
import lombok.Getter;
import lombok.Setter;
import javafx.scene.paint.Color;

@Getter
@Setter
public class ImageShape extends ShapeModel {

    protected String imagePath;

    public ImageShape(double x, double y, double width, double height, int zOrder, Color fillColor, String imagePath) {
        super(x, y, width, height, zOrder, fillColor);
        this.imagePath = imagePath;
    }
}
