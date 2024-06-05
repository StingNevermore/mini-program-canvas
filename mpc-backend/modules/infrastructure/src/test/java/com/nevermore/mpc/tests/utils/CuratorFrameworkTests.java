package com.nevermore.mpc.tests.utils;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.CuratorCacheListenerBuilder;
import org.apache.zookeeper.KeeperException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Consumer;

import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.getCurator;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.setupTestingCluster;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.tearDownTestingCluster;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.waitZkSync;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author nevermore
 * @since 0.0.1
 */
public class CuratorFrameworkTests {


    @BeforeAll
    public static void setup() throws Exception {
        setupTestingCluster();
    }

    @Test
    public void testOperations() throws Exception {
        String path = "/mpc/test";
        String data = "hello";
        getCurator().create().creatingParentsIfNeeded().forPath(path, data.getBytes());

        byte[] bytes = getCurator().getData().forPath(path);
        assertNotNull(bytes);
        assertEquals(data, new String(bytes));

        getCurator().setData().forPath(path, "world".getBytes());

        getCurator().delete().forPath(path);


        assertThrows(KeeperException.NoNodeException.class, () -> getCurator().getData().forPath(path));
    }

    @Test
    public void testCuratorCache() throws Exception {
        ListenerAssertionResultHolder holder = new ListenerAssertionResultHolder(true);
        String path = "/mpc/test";
        CuratorCache cache = CuratorCache.build(getCurator(), path);
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(assertAsExpectedCreate(holder))
                .forChanges(assertAsExpectedChanges(holder))
                .forDeletes(assertAsExpectedDeletes(holder))
                .build();
        cache.listenable().addListener(listener);
        cache.start();

        if (getCurator().checkExists().forPath(path) == null) {
            getCurator().create().creatingParentsIfNeeded().forPath(path, "hello".getBytes());
        }

        getCurator().setData().forPath(path, "world".getBytes());
        waitZkSync();

        Optional<ChildData> childData = cache.get(path);
        assertTrue(childData.isPresent());
        assertEquals("world", new String(childData.get().getData()));

        getCurator().delete().forPath(path);
        waitZkSync();


        if (!holder.result) {
            throw new RuntimeException();
        }
    }

    private static Consumer<ChildData> assertAsExpectedCreate(ListenerAssertionResultHolder holder) {
        return node -> {
            try {
                assertEquals("/mpc/test", node.getPath());
                assertEquals("hello", new String(node.getData()));
            } catch (Exception e) {
                holder.result = false;
                throw e;
            }

        };
    }

    private static CuratorCacheListenerBuilder.ChangeListener assertAsExpectedChanges(ListenerAssertionResultHolder holder) {
        return (oldNode, node) -> {
            try {
                assertEquals("/mpc/test", node.getPath());
                assertEquals("hello", new String(oldNode.getData()));
                assertEquals("world", new String(node.getData()));
            } catch (Exception e) {
                holder.result = false;
                throw e;
            }
        };
    }

    private static Consumer<ChildData> assertAsExpectedDeletes(ListenerAssertionResultHolder holder) {
        return node -> {
            try {
                assertEquals("/mpc/test", node.getPath());
                assertThrows(KeeperException.NoNodeException.class, () -> getCurator().getData().forPath(node.getPath()));
            } catch (Exception e) {
                holder.result = false;
                throw e;
            }
        };
    }

    private static class ListenerAssertionResultHolder {
        private boolean result;

        public ListenerAssertionResultHolder(boolean result) {
            this.result = result;
        }
    }

    @AfterAll
    public static void tearDown() throws Exception {
        tearDownTestingCluster();
    }
}
