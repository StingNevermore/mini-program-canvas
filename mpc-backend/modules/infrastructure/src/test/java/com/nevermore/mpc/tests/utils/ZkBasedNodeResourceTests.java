package com.nevermore.mpc.tests.utils;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.nevermore.mpc.infra.utils.ZkBasedNodeResource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.nevermore.mpc.infra.utils.ObjectMapperUtils.fromYaml;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.getCurator;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.setupTestingCluster;
import static com.nevermore.mpc.tests.utils.ZkTestingClusterTestsBase.tearDownTestingCluster;
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

    private record DatabaseConfig(String url, String username, String password) {
    }

    @AfterAll
    public static void tearDown() throws Exception {
        tearDownTestingCluster();
    }
}
