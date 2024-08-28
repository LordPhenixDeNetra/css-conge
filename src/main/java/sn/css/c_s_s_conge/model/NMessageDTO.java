package sn.css.c_s_s_conge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NMessageDTO {

    private Long id;

    @NotNull
    private String message;

    private Long salarier;
}
