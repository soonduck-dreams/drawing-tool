package cose457.drawingtool.model.shape;

import cose457.drawingtool.model.ShapeModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextShape extends ShapeModel {

    protected String text;

    public TextShape(double x, double y, double width, double height, int zOrder, String text) {
        super(x, y, width, height, zOrder);
        this.text = text;
    }
}
