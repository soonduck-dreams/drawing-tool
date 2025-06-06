package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.TextShape;
import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableValue;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class TextViewModel extends ShapeViewModel {

    public final Observable<String> text = new ObservableValue<>(null);

    public TextViewModel(TextShape model) {
        super(model);
        this.text.set(model.getText());
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
