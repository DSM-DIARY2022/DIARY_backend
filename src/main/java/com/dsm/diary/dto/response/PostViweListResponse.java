package com.dsm.diary.dto.response;

import java.util.List;

public class PostViweListResponse {

    List<PostViweResponse> postResponses;

    public PostViweListResponse(List<PostViweResponse> postViweResponses){
        this.postResponses = postViweResponses;
    }

}
