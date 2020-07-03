package org.apache.dubbo.spring.boot.sample.provider.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.spring.boot.sample.consumer.DemoService2;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Define a  {@code ProviderResponseService} implementations {@link InterfaceName}.
 *
 * @author: gongran
 * @date: 2020/7/3
 */
@Component
public class ProviderSendConsumerServices{

    @DubboReference(version = "${demo.service.version}")
    private DemoService2 demoService;

    public void hello(){
        demoService.sayHello2("consumer!");
    }

}
