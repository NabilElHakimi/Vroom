package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.Mark;
import me.elhakimi.vroom.domain.Model;
import me.elhakimi.vroom.repository.MarkRepository;
import me.elhakimi.vroom.repository.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModelServiceImpl {

    private final MarkRepository markRepository;
    private final ModelRepository modelRepository;

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Optional<Model> getModelById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Model ID");
        }
        return modelRepository.findById(id);
    }

    public Model createModel(Model model) {
        if (model == null || !StringUtils.hasText(model.getName())) {
            throw new IllegalArgumentException("Model name cannot be empty");
        }
        if (model.getMark() == null || model.getMark().getId() == null) {
            throw new IllegalArgumentException("Model must be associated with a valid Mark");
        }

        Mark mark = markRepository.findById(model.getMark().getId())
                .orElseThrow(() -> new RuntimeException("Mark not found with id " + model.getMark().getId()));

        model.setMark(mark);
        model.setCreated_at(LocalDateTime.now());
        model.setUpdated_at(LocalDateTime.now());
        return modelRepository.save(model);
    }

    public Model updateModel(Long id, Model modelDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Model ID");
        }
        if (modelDetails == null || !StringUtils.hasText(modelDetails.getName())) {
            throw new IllegalArgumentException("Model name cannot be empty");
        }

        return modelRepository.findById(id).map(model -> {
            model.setName(modelDetails.getName());
            model.setUpdated_at(LocalDateTime.now());

            if (modelDetails.getMark() != null && modelDetails.getMark().getId() != null) {
                Mark mark = markRepository.findById(modelDetails.getMark().getId())
                        .orElseThrow(() -> new RuntimeException("Mark not found with id " + modelDetails.getMark().getId()));
                model.setMark(mark);
            }

            return modelRepository.save(model);
        }).orElseThrow(() -> new RuntimeException("Model not found with id " + id));
    }

    public void deleteModel(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Model ID");
        }
        if (!modelRepository.existsById(id)) {
            throw new RuntimeException("Model not found with id " + id);
        }
        modelRepository.deleteById(id);
    }


}
