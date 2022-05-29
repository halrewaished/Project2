package com.example.project2.service;

import com.example.project2.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ArrayList<Comment> comments = new ArrayList<>();

    public ArrayList<Comment> getComments(){

        return comments;
    }

    public String getComments(String userId) {
        for (Comment comment : comments) {
            if(comment.getUserId().equals(userId)){
                return comment.getMessage();
            }
        }
        return null;
    }

}
