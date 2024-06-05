package com.nevermore.mpc.tests.utils;

import org.apache.commons.io.FileUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;

import java.io.Closeable;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;
import static java.util.stream.Collectors.toSet;

/**
 * @author nevermore
 * @since 0.0.1
 */
public class ZkTestingClusterTestsBase {
    private static CuratorFramework curator;
    private static TestingCluster cluster;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void setupTestingCluster(Collection<Integer> ports) throws Exception {
        Set<InstanceSpec> specs = ports.stream().map(port -> {
            File tempDirectory = new File("build/zookeeper/" + port);
            if (!tempDirectory.exists()) {
                tempDirectory.mkdirs();
            }
            return new InstanceSpec(tempDirectory,
                    port, -1, -1, true, -1);

        }).collect(toSet());

        TestingCluster cluster = new TestingCluster(specs);
        cluster.start();

        CuratorFramework curator = CuratorFrameworkFactory.newClient(cluster.getConnectString(), new RetryOneTime(1));
        curator.start();

        ZkTestingClusterTestsBase.curator = curator;
        ZkTestingClusterTestsBase.cluster = cluster;
    }

    public static void setupTestingCluster() throws Exception {
        List<Integer> negativePorts = Stream.generate(() -> -1).limit(3).toList();
        setupTestingCluster(negativePorts);
    }

    public static void tearDownTestingCluster() throws Exception {
        closeQuietly(curator);
        closeQuietly(cluster);
        cleanup();
    }

    public static CuratorFramework getCurator() {
        return curator;
    }

    public static TestingCluster getCluster() {
        return cluster;
    }

    public static void waitZkSync() {
        try {
            sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void cleanup() {
        FileUtils.deleteQuietly(new File("build/zookeeper"));
    }

    private static void closeQuietly(Closeable closeable) throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }
}
