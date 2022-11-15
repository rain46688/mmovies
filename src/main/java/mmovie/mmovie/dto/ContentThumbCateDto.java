package mmovie.mmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentThumbCateDto {

     private Long id;
     private String name;
     private String thumbnailSrc;
     
}
