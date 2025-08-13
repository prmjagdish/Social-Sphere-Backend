package com.jagdish.SocailSphere.controller;

import com.jagdish.SocailSphere.model.dto.ReelDto;
import com.jagdish.SocailSphere.model.entity.Reel;
import com.jagdish.SocailSphere.model.entity.User;
import com.jagdish.SocailSphere.repository.ReelRepository;
import com.jagdish.SocailSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reels")
public class ReelController {

    @Autowired
    private ReelRepository reelRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping("/{username}")
    public ResponseEntity<ReelDto> createReel(@PathVariable String username, @RequestBody ReelDto dto) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Reel reel = new Reel(dto.getCaption(), dto.getVideoUrl(), user);
        reelRepository.save(reel);
        dto.setId(reel.getId());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{reelId}")
    public ResponseEntity<ReelDto> updateReel(@PathVariable Long reelId, @RequestBody ReelDto dto) {
        Reel reel = reelRepository.findById(reelId).orElseThrow();
        reel.setCaption(dto.getCaption());
        reel.setVideoUrl(dto.getVideoUrl());
        reelRepository.save(reel);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{reelId}")
    public ResponseEntity<Void> deleteReel(@PathVariable Long reelId) {
        reelRepository.deleteById(reelId);
        return ResponseEntity.noContent().build();
    }
}

