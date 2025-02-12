package me.elhakimi.vroom.web.controllers.admin;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Mark;
import me.elhakimi.vroom.service.impl.MarkServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marks")
@AllArgsConstructor
public class MarkController {

    private final MarkServiceImpl markService;

    @GetMapping
    public ResponseEntity<List<Mark>> getAllMarks() {
        List<Mark> marks = markService.getAllMarks();
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mark> getMarkById(@PathVariable Long id) {
        Optional<Mark> mark = markService.getMarkById(id);
        return mark.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mark> createMark(@RequestBody Mark mark) {
        try {
            Mark createdMark = markService.createMark(mark);
            return ResponseEntity.ok(createdMark);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mark> updateMark(@PathVariable Long id, @RequestBody Mark markDetails) {
        try {
            Mark updatedMark = markService.updateMark(id, markDetails);
            return ResponseEntity.ok(updatedMark);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long id) {
        try {
            markService.deleteMark(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
