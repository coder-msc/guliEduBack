package com.atguigu.tools.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ESSearchService {

    List<Map<String, Object>> searchBlogsPage(String keyword, int pageNo, int pageSize) throws Exception;
}
