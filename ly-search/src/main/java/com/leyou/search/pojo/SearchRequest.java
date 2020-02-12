package com.leyou.search.pojo;

/**
 * @author hx   <br>
 * @Title: 2342<br>
 * @Package <br>
 * @Description: <br>
 * @date 2020/2/1120:13
 */
public class SearchRequest {

    /**搜索条件*/
    private String key;
    /**当前页*/
    private Integer page;
    /**每页大小，不从页面接收，而是固定大小*/
    private static final Integer DEFAULT_SIZE = 20;
    /**默认页*/
    private static final Integer DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}
