package cose457.drawingtool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javafx.scene.paint.Color;

@AllArgsConstructor
@Getter
@Setter
public abstract class ShapeModel {

    protected double x, y, width, height;
    protected int zOrder;
    protected Color fillColor;
}
