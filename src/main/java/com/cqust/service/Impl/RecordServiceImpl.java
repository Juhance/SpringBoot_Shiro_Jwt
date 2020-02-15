package com.cqust.service.Impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqust.enitiy.Record;
import com.cqust.mapper.RecordMapper;
import com.cqust.service.RecordService;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper,Record> implements RecordService {
}
