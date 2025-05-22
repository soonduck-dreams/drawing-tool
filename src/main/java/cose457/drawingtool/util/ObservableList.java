package cose457.drawingtool.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ObservableList<T> {
    private final List<T> list = new ArrayList<>();
    private final List<Consumer<List<T>>> listeners = new ArrayList<>();

    public void add(T item) {
        list.add(item);
        notifyListeners();
    }

    public void remove(T item) {
        list.remove(item);
        notifyListeners();
    }

    public void setAll(List<T> newItems) {
        list.clear();
        list.addAll(newItems);
        notifyListeners();
    }

    public List<T> get() {
        return Collections.unmodifiableList(list);
    }

    public void addListener(Consumer<List<T>> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<List<T>> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Consumer<List<T>> listener : listeners) {
            listener.accept(get());
        }
    }
}