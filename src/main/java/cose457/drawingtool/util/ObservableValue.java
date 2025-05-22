package cose457.drawingtool.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ObservableValue<T> implements Observable<T> {

    private T value;
    private final List<Consumer<T>> listeners = new ArrayList<>();

    public ObservableValue(T initialValue) {
        this.value = initialValue;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            notifyListeners();
        }
    }

    @Override
    public void addListener(Consumer<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Consumer<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners() {
        for (Consumer<T> listener : listeners) {
            listener.accept(value);
        }
    }
}
