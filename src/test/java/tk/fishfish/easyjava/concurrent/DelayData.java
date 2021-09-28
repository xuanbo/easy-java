package tk.fishfish.easyjava.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Delayed对象
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class DelayData<T> implements Delayed {

    /**
     * 数据
     */
    private final T item;

    /**
     * 过期时间
     */
    private final long expireTimestamp;

    public DelayData(T item, long expireTimestamp) {
        this.item = item;
        this.expireTimestamp = expireTimestamp;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diffTime = expireTimestamp - System.currentTimeMillis();
        return unit.convert(diffTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.expireTimestamp - ((DelayData<T>) o).getExpireTimestamp());
    }

    public T getItem() {
        return item;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }

}

