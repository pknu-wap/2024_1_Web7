package com.dogventure.dogweb.dto.gpt.response;

import com.dogventure.dogweb.dto.gpt.Choice;
import com.dogventure.dogweb.dto.gpt.Usage;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ChatResponse {
    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
