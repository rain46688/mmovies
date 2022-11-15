package mmovie.mmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentThumbDto {

     private Long id;
     private String name;
     private String cateName;
     private Long cateId;
     private String thumbnailSrc;

}
