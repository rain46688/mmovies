package mmovie.mmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResult {

    private Long id;
    private String name;
    private int count;
    private List<ContentThumbCateDto> contents;
}
