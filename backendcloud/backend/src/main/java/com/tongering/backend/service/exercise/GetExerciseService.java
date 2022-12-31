package com.tongering.backend.service.exercise;

import com.tongering.backend.pojo.Exercise;

import java.util.List;
import java.util.Map;

public interface GetExerciseService {
    public List<Exercise> getexercise(Map<String, String> data);
}
