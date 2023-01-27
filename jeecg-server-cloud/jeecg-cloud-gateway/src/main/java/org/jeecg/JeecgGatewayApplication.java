package org.jeecg;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.loader.DynamicRouteLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author jeecg
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class JeecgGatewayApplication implements CommandLineRunner {
    @Resource
    private DynamicRouteLoader dynamicRouteLoader;

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(JeecgGatewayApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = StringUtils.isEmpty(env.getProperty("server.servlet.context-path")) ? "" : env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Jeecg-Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/doc.html\n" +
                "External: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

    /**
     * 容器初始化后加载路由
     *
     * @param strings
     */
    @Override
    public void run(String... strings) {
        dynamicRouteLoader.refresh(null);
    }

    /**
     * 接口地址（通过9999端口直接访问）
     *
     * @param indexHtml
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> indexRouter(@Value("classpath:/META-INF/resources/doc.html") final org.springframework.core.io.Resource indexHtml) {
        return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(indexHtml));
    }
}
