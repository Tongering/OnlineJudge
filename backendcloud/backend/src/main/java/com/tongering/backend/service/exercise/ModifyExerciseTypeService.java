package com.tongering.backend.service.exercise;

import java.util.Map;

public interface ModifyExerciseTypeService {
    public Map<String, String> modify(String typeName, Integer id);
}
