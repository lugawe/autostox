package org.autostox.core.util.publisher;

import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.function.Function;

public final class Publishers {

    private Publishers() {}

    public static <T, R> Flow.Publisher<R> map(Flow.Publisher<T> source, Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(mapper);
        return subscriber -> source.subscribe(new MappingSubscriber<>(subscriber, mapper));
    }

    public static <T> Flow.Publisher<T> tap(Flow.Publisher<T> source, PublisherListener<? super T> listener) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(listener);
        return subscriber -> source.subscribe(new TappingSubscriber<>(subscriber, listener));
    }
}
