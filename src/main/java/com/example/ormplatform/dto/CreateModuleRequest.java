package com.example.ormplatform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateModuleRequest {

    @NotBlank
    private String title;

    @NotNull
    @Min(0)
    private Integer orderIndex;

    private String description;

    public CreateModuleRequest() {}

    public String getTitle() {
        return title;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
