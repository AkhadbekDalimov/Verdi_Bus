package uz.asbt.digid.common.models.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GridResponse<T> {

    private T list;
    private long totalItemsCount;
    private long totalPages;
}
