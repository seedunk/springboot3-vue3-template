package com.seedunk.template.common; 
import java.util.HashMap; 

import org.springframework.util.StringUtils;

public class ObjectResponse extends HashMap<String, Object>{
        public ObjectResponse build(){
            return this.build(200,"");
        }
        public ObjectResponse build(String message){
            return this.build(-2,message );
        }
        public ObjectResponse build(int responseCode,String message){

            ObjectResponse response=new ObjectResponse();
                response. put("code", responseCode);
                if( StringUtils.hasText(message)){
                       response. put("data", message); 
                }else  response. put("data", this);  
                return response ; 
        }
        public ObjectResponse build404(){

            return build(-404,"无效的资源");
        }
        public <T1> ObjectResponse build(T1 data){

             ObjectResponse response=new ObjectResponse();
                            response. put("code", 200);
                            response. put("data", data);  
                return response ; 
        }
   
}
