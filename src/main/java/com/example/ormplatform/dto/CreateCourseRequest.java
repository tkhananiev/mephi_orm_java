package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class CreateCourseRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long teacherId;

    private Integer durationHours;

    private LocalDate startDate;

    private Set<String> tags;

    public CreateCourseRequest() {
        // for Jackson
    }

    public CreateCourseRequest(
            String title,
            String description,
            Long categoryId,
            Long teacherId,
            Integer durationHours,
            LocalDate startDate,
            Set<String> tags
    ) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.teacherId = teacherId;
        this.durationHours = durationHours;
        this.startDate = startDate;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
