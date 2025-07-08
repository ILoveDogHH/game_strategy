package jedi.game.cache;

import com.github.benmanes.caffeine.cache.*;
import jedi.game.db.BasicCacheDatabaseDao;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

public class CacheDatasEventCenter {
    /**
     * 数据缓存中心
     */
    private static final LoadingCache<CacheKey, CacheData> caches;
    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    /**
     * 所有的dao缓存数据
     */
    private static ConcurrentHashMap<CacheTable, BasicCacheDatabaseDao>  baseDaos = new ConcurrentHashMap<>();

    private static final Map<CacheTable, CacheDatas> baseCacheDaos = new HashMap<>();



    static {
        // 目前看上去一个cachedata大约20byte 先暂定500M的缓存大小 25000000
        // recordstats定義一個監控策略 caches.stats()返回一個監控對象 從而獲得監控數據
        caches = Caffeine.newBuilder().recordStats().softValues().maximumSize(25000000)
                .expireAfterWrite(Duration.ofMinutes(30)).executor(service)
                .removalListener(new RemovalListener<CacheKey, CacheData>() {
                    // 淘汰监听 可以用来记录为何被淘汰
                    /**
                     * EXPLICIT: 这个原因是，用户造成的，通过调用remove方法从而进行删除。 REPLACED: 更新的时候，其实相当于把老的value给删了。
                     * COLLECTED: 用于我们的垃圾收集器，也就是我们上面减少的软引用，弱引用。 EXPIRED： 过期淘汰。 SIZE:
                     * 大小淘汰，当超过最大的时候就会进行淘汰。
                     */

                    @Override
                    public void onRemoval(CacheKey key, CacheData value, RemovalCause cause) {
                        CacheDatas cacheDatas = baseCacheDaos.get(key.getTable());
                        if (cacheDatas != null) {
                            cacheDatas.removeKey(key);
                        }
                    }
                }).build(new CacheLoader<CacheKey, CacheData>() {
                    // 缓存中不存在key的时候调用
                    @Override
                    public CacheData load(CacheKey key) throws Exception {
                        BasicCacheDatabaseDao dao = baseDaos.get(key.getTable());
                        CacheData data = new CacheData(dao.getList(key.getKey()));
                        return data;
                    }
                    // 缓存剔除存在的key 不存在的key应用loadAll
                    @Override
                    public Map<CacheKey, CacheData> loadAll(Iterable<? extends CacheKey> keys) throws Exception {
                        Map<CacheKey, CacheData> map = new HashMap<>();
                        Set<CacheKey> notExistsKeys = new HashSet<>();
                        keys.forEach(key -> notExistsKeys.add(key));
                        BasicCacheDatabaseDao dao = null;
                        if (notExistsKeys.size() == 0)
                            return map;
                        else
                            dao = baseDaos.get(new ArrayList<>(notExistsKeys).get(0).getTable());
                        if (dao == null)
                            return map;
                        List<Object[]> notExistsKeysList = notExistsKeys.stream().map(m -> m.getKey()).collect(Collectors.toList());
                        // 从数据库中读取缺失数据
                        Map<CacheKey, CacheData> notInCacheDatas = dao.getBatchT(notExistsKeysList);
                        return notInCacheDatas;
                    }
                });
    }






    // 这个方法除了cachedatas类 其余地方不要进行调用
    public static CacheData get(CacheKey key) {
        CacheData cacheData = caches.get(key);
        return cacheData;
    }


    // 对外提供 多值 key的获得 （这里特别注意 传进来的必须是一个表里的 不允许传入多表的keys）
    public static Map<CacheKey, CacheData> getAll(Collection<CacheKey> keys) {
        Map<CacheKey, CacheData> cacheDatas = caches.getAll(keys);
        return cacheDatas;
    }





    public static void registerDao(CacheTable table, BasicCacheDatabaseDao cachedata) {
        baseDaos.put(table, cachedata);
    }


    public static void registerDao(CacheTable table, CacheDatas cachedata) {
        baseCacheDaos.put(table, cachedata);
    }


    public static void invalidateKeys(CacheTable table, Collection<CacheKey> keys) {
        if (keys == null || keys.isEmpty())
            return;
        caches.invalidateAll(keys);
    }



    // 只读取缓存, 不读取数据库
    public static CacheData getIfPresent(CacheKey key) {
        CacheData cacheData = caches.getIfPresent(key);
        return cacheData;
    }





}
