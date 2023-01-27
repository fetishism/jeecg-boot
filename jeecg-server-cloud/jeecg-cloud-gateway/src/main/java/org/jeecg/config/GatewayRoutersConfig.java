package org.jeecg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author scott
 * @date 2020/05/26
 * 路由配置信息
 */
@Configuration
@RefreshScope
public class GatewayRoutersConfig {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public String serverAddr;

    @Value("${spring.cloud.nacos.discovery.namespace:#{null}}")
    public String namespace;

    /**
     * 路由配置方式：database，yml，nacos
     */
    @Value("${jeecg.route.config.data-type:#{null}}")
    public String dataType;

    @Value("${jeecg.route.config.data-id:#{null}}")
    public String dataId;

    @Value("${jeecg.route.config.group:DEFAULT_GROUP:#{null}}")
    public String routeGroup;

    @Value("${spring.cloud.nacos.config.username:#{null}}")
    public String username;

    @Value("${spring.cloud.nacos.config.password:#{null}}")
    public String password;

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setRouteDataId(String dataId) {
        this.dataId = dataId + ".json";
    }

    public void setRouteGroup(String routeGroup) {
        this.routeGroup = routeGroup;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataType() {
        return dataType;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getDataId() {
        return dataId;
    }

    public String getRouteGroup() {
        return routeGroup;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
