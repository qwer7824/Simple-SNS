package com.sns.controller;


import com.sns.controller.request.PostCrateRequest;
import com.sns.controller.response.Response;
import com.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCrateRequest request, Authentication authentication){
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return Response.success();
    }

}

