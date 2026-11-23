package org.autostox.core.util.publisher;

import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public final class TappingSubscriber<T> implements Flow.Subscriber<T> {

    private final AtomicBoolean done = new AtomicBoolean();

    private final Flow.Subscriber<? super T> downstream;
    private final PublisherListener<? super T> listener;

    private Flow.Subscription subscription;

    public TappingSubscriber(Flow.Subscriber<? super T> downstream, PublisherListener<? super T> listener) {
        this.downstream = downstream;
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        listener.onStart();
        downstream.onSubscribe(new TappingSubscription());
    }

    @Override
    public void onNext(T item) {

        if (done.get()) {
            return;
        }

        listener.onItem(item);

        downstream.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        if (terminate()) {
            listener.onEnd(throwable);
            downstream.onError(throwable);
        }
    }

    @Override
    public void onComplete() {
        if (terminate()) {
            listener.onEnd(null);
            downstream.onComplete();
        }
    }

    private boolean terminate() {
        return done.compareAndSet(false, true);
    }

    private final class TappingSubscription implements Flow.Subscription {

        private TappingSubscription() {}

        @Override
        public void request(long n) {
            subscription.request(n);
        }

        @Override
        public void cancel() {
            if (terminate()) {
                listener.onEnd(null);
            }
            subscription.cancel();
        }
    }
}
