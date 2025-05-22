package cose457.drawingtool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class ShapeModel {

    protected double x, y, width, height;
    protected int zOrder;
}
