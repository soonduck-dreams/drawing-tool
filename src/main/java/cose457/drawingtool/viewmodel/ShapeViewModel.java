package cose457.drawingtool.viewmodel;

import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableValue;
import cose457.drawingtool.view.viewmodelvisitor.ShapeViewModelVisitor;

public abstract class ShapeViewModel {

    public final Observable<Double> x = new ObservableValue<>(0.0);
    public final Observable<Double> y = new ObservableValue<>(0.0);
    public final Observable<Double> width = new ObservableValue<>(0.0);
    public final Observable<Double> height = new ObservableValue<>(0.0);

    public final Observable<Integer> zOrder = new ObservableValue<>(0);
    public final Observable<Boolean> selected = new ObservableValue<>(false);

    public ShapeViewModel(double x, double y, double width, double height) {
        this.x.set(x);
        this.y.set(y);
        this.width.set(width);
        this.height.set(height);
    }

    public abstract void accept(ShapeViewModelVisitor visitor);
}
