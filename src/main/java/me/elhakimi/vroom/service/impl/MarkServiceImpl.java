package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Mark;
import me.elhakimi.vroom.repository.MarkRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MarkServiceImpl {

    private final MarkRepository markRepository;

    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    public Optional<Mark> getMarkById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Mark ID");
        }
        return markRepository.findById(id);
    }

    public Mark createMark(Mark mark) {
        if (mark == null || !StringUtils.hasText(mark.getName())) {
            throw new IllegalArgumentException("Mark name cannot be empty");
        }
        mark.setCreated_at(LocalDateTime.now());
        mark.setUpdated_at(LocalDateTime.now());
        return markRepository.save(mark);
    }

    public Mark updateMark(Long id, Mark markDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Mark ID");
        }
        if (markDetails == null || !StringUtils.hasText(markDetails.getName())) {
            throw new IllegalArgumentException("Mark name cannot be empty");
        }

        return markRepository.findById(id).map(mark -> {
            mark.setName(markDetails.getName());
            mark.setUpdated_at(LocalDateTime.now());
            return markRepository.save(mark);
        }).orElseThrow(() -> new RuntimeException("Mark not found with id " + id));
    }

    public void deleteMark(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Mark ID");
        }
        if (!markRepository.existsById(id)) {
            throw new RuntimeException("Mark not found with id " + id);
        }
        markRepository.deleteById(id);
    }
}
