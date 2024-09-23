package com.example.demo.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> map = new HashMap<>();
        map.put("cloud_name", "dvqxlfkrh");
        map.put("api_key", "673641894652412");
        map.put("api_secret", "VTxX1FeRz7yTNeiRS7D8VBOoKVI");
        return new Cloudinary(map);
    }

}
