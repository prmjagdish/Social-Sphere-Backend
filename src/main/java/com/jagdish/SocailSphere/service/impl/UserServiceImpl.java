package com.jagdish.SocailSphere.service.impl;
import com.jagdish.SocailSphere.model.dto.*;
import com.jagdish.SocailSphere.model.entity.Post;
import com.jagdish.SocailSphere.model.entity.Reel;
import com.jagdish.SocailSphere.model.entity.User;
import com.jagdish.SocailSphere.repository.PostRepository;
import com.jagdish.SocailSphere.repository.ReelRepository;
import com.jagdish.SocailSphere.repository.UserRepository;
import com.jagdish.SocailSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReelRepository reelRepository;

    @Override
    public UserDto updateProfile(String username, EditProfileRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setBio(request.getBio());
        user.setAvatarUrl(request.getAvatar());

        userRepository.save(user);

        return mapToDto(user);
    }

    @Override
    public UserProfileResponse getFullUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                mapToDto(user),
                postRepository.findByUser(user).stream()
                        .map(this::mapToPostDto)
                        .collect(Collectors.toList())
                ,
                reelRepository.findByUser(user).stream().map(this::mapToReelDto).collect(Collectors.toList()),
                user.getSavedPosts().stream().map(this::mapToPostDto).collect(Collectors.toList()),
                user.getFollowers().stream().map(User::getUsername).collect(Collectors.toList()),
                user.getFollowing().stream().map(User::getUsername).collect(Collectors.toList())
        );
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        dto.setAvatar(user.getAvatarUrl());
        return dto;
    }

    private PostDto mapToPostDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getImageUrl(),
                post.getCaption(),
                post.getUser().getUsername(),           // ✅ username string
                post.getUser().getAvatarUrl(),          // ✅ avatar string
                post.getLikedByUsers().size(),          // ✅ int likes count
                post.getComments().stream()
                        .map(comment -> new CommentDto(
                                comment.getId(),
                                comment.getContent(),
                                comment.getUser().getUsername()
                        ))
                        .collect(Collectors.toList()));
    }

    private ReelDto mapToReelDto(Reel reel) {
        return new ReelDto(reel.getId(), reel.getCaption(), reel.getVideoUrl());
    }
}
