package com.tongering.backend.service.exercise;

import com.tongering.backend.pojo.Exercise;

import java.util.List;

public interface SearchExerciseService {
    public List<Exercise> search (String searchName);
}
