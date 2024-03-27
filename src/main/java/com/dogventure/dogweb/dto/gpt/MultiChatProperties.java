package com.dogventure.dogweb.dto.gpt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultiChatProperties {

    private String url = "https://api.openai.com/v1/chat/completions";

    private String model = "gpt-3.5-turbo";

    private Integer maxTokens = 500;

    private Double temperature = 1.0;

    private Double topP = 1.0;
}
