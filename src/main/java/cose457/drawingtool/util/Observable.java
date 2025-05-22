package cose457.drawingtool.util;

import java.util.function.Consumer;

public interface Observable<T> {
    T get();
    void set(T value);
    void addListener(Consumer<T> listener);
    void removeListener(Consumer<T> listener);
    void notifyListeners();
}
