package com.nevermore.mpc.tests.utils;

import org.apache.commons.io.FileUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.InstanceSpec;
import org.apache.curator.test.TestingCluster;

import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nevermore
 * @since 0.0.1
 */
public class ZkTestingClusterTestsBase {
    private static CuratorFramework curator;
    private static TestingCluster cluster;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void setupTestingCluster() throws Exception {
        int n = 3;

        List<InstanceSpec> specs = new ArrayList<>(3);
        for (int i = 0; i < n; i++) {
            File tempDirectory = new File("build/zookeeper/" + i);
            if (!tempDirectory.exists()) {
                tempDirectory.mkdirs();
            }
            InstanceSpec spec = new InstanceSpec(tempDirectory,
                    -1, -1, -1, true, -1);
            specs.add(spec);
        }

        TestingCluster cluster = new TestingCluster(specs);
        cluster.start();

        CuratorFramework curator = CuratorFrameworkFactory.newClient(cluster.getConnectString(), new RetryOneTime(1));
        curator.start();

        ZkTestingClusterTestsBase.curator = curator;
        ZkTestingClusterTestsBase.cluster = cluster;
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

    private static void cleanup() {
        FileUtils.deleteQuietly(new File("build/zookeeper"));
    }

    private static void closeQuietly(Closeable closeable) throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }
}
