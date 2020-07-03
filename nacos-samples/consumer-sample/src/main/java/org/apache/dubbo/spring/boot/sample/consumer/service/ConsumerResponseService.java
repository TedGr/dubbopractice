package org.apache.dubbo.spring.boot.sample.consumer.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.spring.boot.sample.consumer.DemoService2;
import org.springframework.beans.factory.annotation.Value;

/**
 * Description:
 * Define a  {@code ConsumerSendProvider} implementations {@link InterfaceName}.
 *
 * @author: gongran
 * @date: 2020/7/3
 */
@DubboService(version = "${demo.service.version}")
public class ConsumerResponseService implements DemoService2 {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;


    @Override
    public String sayHello2(String name) {
        return String.format("[%s] : Hello, %s let's do it", serviceName,name);
    }
}
