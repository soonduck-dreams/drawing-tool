package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.ShapeType;
import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableList;
import cose457.drawingtool.util.ObservableValue;

public class CanvasViewModel {
    public final ObservableList<ShapeViewModel> shapes = new ObservableList<>();
    public final Observable<ShapeType> currentShapeType = new ObservableValue<>(ShapeType.RECTANGLE);
}
