package com.tencent.ied.bk.domain;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiancheng
 */
@Data
public class SearchEntry {

    /**
     * 搜索次数
     */
    private AtomicInteger searchCount = new AtomicInteger(0);

    /**
     * 包含匹配搜索次数
     */
    private AtomicInteger containsMatchCount = new AtomicInteger(0);
}
