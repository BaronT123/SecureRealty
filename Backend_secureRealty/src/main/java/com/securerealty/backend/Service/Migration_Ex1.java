package com.securerealty.backend.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Migration_Ex1 {
	private RestTemplate restTemplate = new RestTemplate();
	@Value("${chat.server.url}")
    private String chatServerUrl;

    public String getMessages(String conversationId){
    	

        String url = chatServerUrl + "/messages/" + conversationId;

        return restTemplate.getForObject(url, String.class);
    }

    public String getConversations(){

        String url = chatServerUrl + "/conversations";

        return restTemplate.getForObject(url, String.class);
    }

    public String getUsers(){

        String url = chatServerUrl + "/users";

        return restTemplate.getForObject(url, String.class);
    }
}
