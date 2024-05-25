package com.nevermore.mpc.unitests.utils;

import org.junit.jupiter.api.Test;

import com.nevermore.mpc.infra.distruibuted.FileBasedNodeResource;

import static com.nevermore.mpc.infra.utils.ObjectMapperUtils.fromPrefixYaml;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author nevermore
 * @since 0.0.1
 */
public class NodeResourceTests {

    @Test
    public void test() {
        FileBasedNodeResource<DataBaseConfig> resource = FileBasedNodeResource.newBuilder("classpath:data/node_resource_test.yml")
                .withPath("spring.database")
                .withDeserializer(bytes -> fromPrefixYaml("spring.database", new String(bytes), DataBaseConfig.class))
                .withDefaultResource(new DataBaseConfig())
                .build();

        DataBaseConfig config = resource.getValue();
        assertNotNull(config);
        assertEquals(config.url, "jdbc:mysql://localhost:3306/mydb");
        assertEquals(config.user, "root");
        assertEquals(config.password, "password");
        resource.close();
    }

    @SuppressWarnings("unused")
    private static class DataBaseConfig {
        private String url;
        private String user;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "DataBaseConfig{" +
                    "url='" + url + '\'' +
                    ", user='" + user + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
