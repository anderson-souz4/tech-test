package com.asouza.techtest.model.dto;

import com.asouza.techtest.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

    public TaskDTO() {
        this.status = Status.PENDING;
    }


}
