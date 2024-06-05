package com.nevermore.mpc.tests.utils;

import com.nevermore.mpc.infra.utils.ZkBasedNodeResource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.nevermore.mpc.infra.utils.ObjectMapperUtils.fromYaml;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.getCurator;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.setupTestingCluster;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.tearDownTestingCluster;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.waitZkSync;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author nevermore
 * @since 0.0.1
 */
@SuppressWarnings("resource")
public class ZkBasedNodeResourceTests {


    @BeforeAll
    public static void setup() throws Exception {
        setupTestingCluster();
    }

    @Test
    public void testGet() throws Exception {
        String path = "/mpc/database";

        String data = """
                url: jdbc:database
                username: root
                password: password
                """;
        getCurator().create().creatingParentsIfNeeded().forPath(path);
        getCurator().setData().forPath(path, data.getBytes());

        ZkBasedNodeResource<DatabaseConfig> zkBasedNodeResource =
                ZkBasedNodeResource.<DatabaseConfig>newBuilder(path)
                        .withDefaultResourceSupplier(() -> new DatabaseConfig("", "", ""))
                        .withDeserializer((bytes, stat) -> fromYaml(new String(bytes), DatabaseConfig.class))
                        .withCuratorFactory(ZkTestingClusterTestsBase::getCurator)
                        .build();
        DatabaseConfig databaseConfig = zkBasedNodeResource.get();
        assertNotNull(databaseConfig);
        assertEquals("jdbc:database", databaseConfig.url);
        assertEquals("root", databaseConfig.username);
        assertEquals("password", databaseConfig.password);
    }

    @Test
    public void testBadConfig() throws Exception {
        String path = "/mpc/database/bad";

        String data = """
                url: jdbc:database
                usernamo: root
                password: password
                """;
        getCurator().create().creatingParentsIfNeeded().forPath(path);
        getCurator().setData().forPath(path, data.getBytes());

        ZkBasedNodeResource<DatabaseConfig> zkBasedNodeResource =
                ZkBasedNodeResource.<DatabaseConfig>newBuilder(path)
                        .withDefaultResourceSupplier(() -> new DatabaseConfig("", "", ""))
                        .withDeserializer((bytes, stat) -> fromYaml(new String(bytes), DatabaseConfig.class))
                        .withCuratorFactory(ZkTestingClusterTestsBase::getCurator)
                        .build();

        assertThrows(RuntimeException.class, zkBasedNodeResource::get);
    }

    @Test
    public void testListenable() throws Exception {
        String path = "/mpc/database/listenable";
        String data = """
                url: jdbc:database
                username: root
                password: password
                """;
        getCurator().create().creatingParentsIfNeeded().forPath(path);
        getCurator().setData().forPath(path, data.getBytes());

        List<Object> targets = new LinkedList<>();

         ZkBasedNodeResource<DatabaseConfig> zkBasedNodeResource =
                ZkBasedNodeResource.<DatabaseConfig>newBuilder(path)
                        .withDefaultResourceSupplier(() -> new DatabaseConfig("", "", ""))
                        .withDeserializer((bytes, stat) -> fromYaml(new String(bytes), DatabaseConfig.class))
                        .withCuratorFactory(ZkTestingClusterTestsBase::getCurator)
                        .withOnResourceChangedListener((oldNode, newNode) -> {
                            targets.add(new Object());
                            DatabaseConfig config = fromYaml(new String(newNode.getData()), DatabaseConfig.class);
                            assertNotNull(config);
                            assertEquals("nevermore", config.username);
                        })
                        .build();

        String newData = """
                url: jdbc:database
                username: nevermore
                password: password
                """;
        getCurator().setData().forPath(path, newData.getBytes());
        waitZkSync();

        assertEquals(1, targets.size());
        DatabaseConfig databaseConfig = zkBasedNodeResource.get();
        assertNotNull(databaseConfig);
        assertEquals("nevermore", databaseConfig.username);
    }

    private record DatabaseConfig(String url, String username, String password) {
    }

    @AfterAll
    public static void tearDown() throws Exception {
        tearDownTestingCluster();
    }
}
