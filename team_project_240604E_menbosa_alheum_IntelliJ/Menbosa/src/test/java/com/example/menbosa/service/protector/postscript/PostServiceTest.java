package com.example.menbosa.service.protector.postscript;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    void removePost() {
        Long postnum = 28L;
        postService.removePost(postnum);
    }

    @Test
    void modifyImgFile() {
    }
}