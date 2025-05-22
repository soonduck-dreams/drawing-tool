package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableValue;
import lombok.Getter;

public abstract class ShapeViewModel {

    @Getter
    protected final ShapeModel model;

    public final Observable<Double> x = new ObservableValue<>(0.0);
    public final Observable<Double> y = new ObservableValue<>(0.0);
    public final Observable<Double> width = new ObservableValue<>(0.0);
    public final Observable<Double> height = new ObservableValue<>(0.0);

    public final Observable<Integer> zOrder = new ObservableValue<>(0);
    public final Observable<Boolean> selected = new ObservableValue<>(false);

    public ShapeViewModel(ShapeModel model) {
        this.model = model;

        // Model → Observable 초기화
        this.x.set(model.getX());
        this.y.set(model.getY());
        this.width.set(model.getWidth());
        this.height.set(model.getHeight());
        this.zOrder.set(model.getZOrder());
    }

}
