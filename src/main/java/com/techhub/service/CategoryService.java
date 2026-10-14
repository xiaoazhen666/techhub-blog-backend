package com.techhub.service;

import com.techhub.dto.CategoryVO;
import com.techhub.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryVO> list() {
        return categoryRepository.findAll().stream()
                .map(CategoryVO::from)
                .collect(Collectors.toList());
    }
}
