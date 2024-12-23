package com.atguigu.daijia.dispatch.service.impl;

import com.atguigu.daijia.dispatch.service.NewOrderService;
import com.atguigu.daijia.model.vo.dispatch.NewOrderTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class NewOrderServiceImpl implements NewOrderService {

    @Override
    public Long addAndStartTask(NewOrderTaskVo newOrderTaskVo) {
        return 0L;
    }
}
