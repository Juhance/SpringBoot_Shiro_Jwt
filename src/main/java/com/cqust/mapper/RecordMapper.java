package com.cqust.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqust.enitiy.Record;
import com.cqust.enitiy.User;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
