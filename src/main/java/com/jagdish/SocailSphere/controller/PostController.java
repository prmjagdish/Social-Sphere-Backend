package com.jagdish.SocailSphere.controller;
import com.jagdish.SocailSphere.model.dto.PostDto;
import com.jagdish.SocailSphere.model.entity.Post;
import com.jagdish.SocailSphere.model.entity.User;
import com.jagdish.SocailSphere.repository.PostRepository;
import com.jagdish.SocailSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{username}")
    public ResponseEntity<PostDto> createPost(@PathVariable String username, @RequestBody PostDto dto) {
        User user = userRepository.findByUsername(username).orElseThrow();

        Post post = new Post();
        post.setImageUrl(dto.getImageUrl());
        post.setCaption(dto.getCaption());
        post.setUser(user);
        postRepository.save(post);

        // Create response DTO
        PostDto responseDto = new PostDto();
        responseDto.setId(post.getId());
        responseDto.setImageUrl(post.getImageUrl());
        responseDto.setCaption(post.getCaption());
        responseDto.setUser(user.getUsername());
        responseDto.setAvatarUrl(user.getAvatarUrl()); // Assuming User has getAvatar()
        responseDto.setLikes(0); // default
        responseDto.setComments(new ArrayList<>()); // empty initially

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto dto) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setImageUrl(dto.getImageUrl());
        post.setCaption(dto.getCaption());
        postRepository.save(post);
        dto.setId(post.getId());
        return ResponseEntity.ok(dto);
    }

    // ✅ DELETE POST
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postRepository.deleteById(postId);
        return ResponseEntity.noContent().build();
    }

    // ✅ SAVE POST
    @PutMapping("/save/{username}/{postId}")
    public ResponseEntity<Void> savePost(@PathVariable String username, @PathVariable Long postId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        user.getSavedPosts().add(post);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    // ✅ UNSAVE POST
    @PutMapping("/unsave/{username}/{postId}")
    public ResponseEntity<Void> unsavePost(@PathVariable String username, @PathVariable Long postId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        user.getSavedPosts().remove(post);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
