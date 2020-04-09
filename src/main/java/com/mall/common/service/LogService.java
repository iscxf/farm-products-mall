package com.mall.common.service;

import org.springframework.stereotype.Service;

import com.mall.common.domain.LogDO;
import com.mall.common.domain.PageDO;
import com.mall.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
