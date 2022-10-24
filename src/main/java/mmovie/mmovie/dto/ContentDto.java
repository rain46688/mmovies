package mmovie.mmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {

    private Long id;
    private String name;
    private String type;
    private String cateName;
    private byte[] src;
}
