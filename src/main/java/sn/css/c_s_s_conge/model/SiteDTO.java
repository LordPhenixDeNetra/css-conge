package sn.css.c_s_s_conge.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SiteDTO {

    private Long id;

    @NotNull
    private String numSite;

//    @NotNull
//    private Integer numSite;

    @Size(max = 255)
    private String nomSite;

}
