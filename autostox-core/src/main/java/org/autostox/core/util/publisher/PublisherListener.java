package org.autostox.core.util.publisher;

public interface PublisherListener<T> {

    default void onStart() {}

    default void onItem(T item) {}

    default void onEnd(Throwable error) {}
}
