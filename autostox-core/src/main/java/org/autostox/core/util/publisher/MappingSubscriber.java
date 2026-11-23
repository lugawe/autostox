package org.autostox.core.util.publisher;

import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public final class MappingSubscriber<T, R> implements Flow.Subscriber<T> {

    private final Flow.Subscriber<? super R> downstream;
    private final Function<? super T, ? extends R> mapper;
    private final AtomicBoolean done = new AtomicBoolean();

    private Flow.Subscription subscription;

    public MappingSubscriber(Flow.Subscriber<? super R> downstream, Function<? super T, ? extends R> mapper) {
        this.downstream = downstream;
        this.mapper = mapper;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        downstream.onSubscribe(subscription);
    }

    @Override
    public void onNext(T item) {
        if (done.get()) {
            return;
        }

        R mapped;
        try {
            mapped = Objects.requireNonNull(mapper.apply(item), "Mapper returned null");
        } catch (Throwable t) {
            subscription.cancel();
            onError(t);
            return;
        }

        downstream.onNext(mapped);
    }

    @Override
    public void onError(Throwable throwable) {
        if (terminate()) {
            downstream.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (terminate()) {
            downstream.onComplete();
        }
    }

    private boolean terminate() {
        return done.compareAndSet(false, true);
    }
}
