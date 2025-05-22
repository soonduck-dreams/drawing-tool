package cose457.drawingtool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class RectangleShape extends ShapeModel {

    public RectangleShape(double x, double y, double width, double height, int zOrder) {
        super(x, y, width, height, zOrder);
    }
}
