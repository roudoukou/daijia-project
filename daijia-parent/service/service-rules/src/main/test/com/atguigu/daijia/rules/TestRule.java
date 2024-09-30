package com.atguigu.daijia.rules;

import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 肉豆蔻吖
 * @date 2024/9/30
 */
@SpringBootTest
public class TestRule {

    @Autowired
    private KieContainer kieContainer;

    @Test
    public void test01() {
        // 开启会话
        KieSession kieSession = kieContainer.newKieSession();

        // 触发规则
        kieSession.fireAllRules();
        // 中止会话
        kieSession.dispose();
    }
}
