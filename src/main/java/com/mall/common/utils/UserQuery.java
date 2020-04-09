package com.mall.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author chenxf
 * Created on 2019/2/16
 */
public class UserQuery extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    //
    private int offset;
    // 每页条数
    private int limit;

    private String userName;

    private Long created;

    private Long questionId;

    private Integer status;

    public UserQuery(Map<String, Object> params) {
        this.putAll(params);
        // 分页参数
        this.offset = Integer.parseInt(params.get("offset").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        if (null != params.get("userName")) {
            this.userName = params.get("userName").toString();
        }
        if(null != params.get("created")) {
            this.created = (Long) params.get("created");
        }
        if(null != params.get("questionId")) {
            this.questionId = (Long) params.get("questionId");
        }
        if(null != params.get("status")) {
            this.status = Integer.parseInt(params.get("status").toString());
        }
        this.put("offset", offset);
        this.put("page", offset / limit + 1);
        this.put("limit", limit);
        this.put("userName", userName);
        this.put("created", created);
        this.put("questionId", questionId);
        this.put("status", status);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.put("offset", offset);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
