package com.dev.qlda.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class PageRequest {
    private int pageNumber;
    private int pageSize;

    public Pageable getPageable(){
        return org.springframework.data.domain.PageRequest.of(pageNumber, pageSize);
    }
}
