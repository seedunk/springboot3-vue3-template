package com.seedunk.template.controller;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.seedunk.template.common.ObjectResponse;
import com.seedunk.template.tool.TemplateTool;

@RestController 
public class DeepseekController {
    

   private final ChatClient chatClient;

    private final TemplateTool templateTool ;
 


    public DeepseekController(DeepSeekChatModel chatModel, TemplateTool templateTool) {
        chatClient=ChatClient.builder(chatModel).defaultSystem("""
                       你是一个车间生产智能助手。用户会用自然语言提出需求，你需要：
                         1. 判断用户意图，选择合适的工具调用；
                         2. 如果完成需求需要多个步骤，按顺序调用多个工具（例如先生成数据，再汇总，再导出报表）；
                         3. 工具返回的结果要整合成通顺的中文，可以输出HTML,JS片段但不要直接吐 JSON,不要给出进一步建议；
                         4. 如果用户的需求不明确（例如没说车间名、没说日期），主动询问澄清，不要猜测；
                         5. 回答当前问题即可，不要给出进一步建议。
                      """)
                 .build(); 
        this.templateTool = templateTool;
    }

    @PostMapping("/api/chat")
    public ObjectResponse chat(@Valid @RequestBody Map<String, String> params) {

        ObjectResponse res=new ObjectResponse(); 
                       res.put("message",chatClient.prompt()
                                            .user( params.get("message") )
                                            .tools(templateTool)
                                            .call()
                                            .content());
        return res.build( );
    }
  
}
