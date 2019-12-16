
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Cache implements CacheInterface {

    private final List<CacheObject> collection;

    Cache() {
        collection = Collections.synchronizedList(new ArrayList<>());
        new Thread(cacheWatcher()).start();
    }

    private Runnable cacheWatcher() {
        return () -> {
            while (true) {
                    long timestamp = new Date().getTime();
                    List<Integer> toBeRemoved = new ArrayList<>();
                    for (int i = 0; i < collection.size(); i++) {
                        CacheObject cacheObject = collection.get(i);
                        if (cacheObject.getExpirationTimestamp() < timestamp) {
                            System.out.println(timestamp + " actual");
                            System.out.println(cacheObject.getExpirationTimestamp() + " to be deleted");
                            System.out.println("index " + i + " should be removed");
                            toBeRemoved.add(i);
                        }
                    }
                    Collections.reverse(toBeRemoved);
                    toBeRemoved.forEach(index -> collection.remove((int) index));
            }
        };
    }

    @Override
    public int add(Object o, long timeout) {
        CacheObject e = new CacheObject(o, timeout);
        collection.add(e);
        System.out.println(e.expirationTimestamp + " " + e.getObject());
        return collection.size() - 1;
    }

    @Override
    public Object get(int id) {
        try {
            CacheObject cacheObject = collection.get(id);
            cacheObject.resetExpirationTimestamp();
            return cacheObject.getObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getTimeout(int id) {
        return collection.get(id).getTimeToLive();
    }

    @Override
    public void delete(int id) {
        collection.remove(id);
    }

    @Override
    public void incrementTimeout(long time) {
        collection.forEach(cacheObject -> cacheObject.incrementExpirationTimestamp(time));
    }

    static class CacheObject {
        private Object object;
        private long expirationTimestamp;
        private long timeToLive;

        CacheObject(Object object, long timeout) {
            this.object = object;
            this.timeToLive = timeout;
            this.expirationTimestamp = new Date().getTime() + timeout;
        }

        Object getObject() {
            return object;
        }

        long getTimeToLive() {
            return timeToLive;
        }

        long getExpirationTimestamp() {
            return expirationTimestamp;
        }

        void resetExpirationTimestamp() {
            this.expirationTimestamp = new Date().getTime() + timeToLive;
        }

        void incrementExpirationTimestamp(long timestamp) {
            expirationTimestamp = expirationTimestamp + timestamp;
        }
    }
}
