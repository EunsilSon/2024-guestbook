package com.eunsil.guestbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
public class CardDTO {

    public Long cardId;

    public String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate postDate;

    public String content;

}
