package com.finalpbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;

@SpringBootApplication
public class FinalpblApplication {

	public static void main(String[] args) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dp5nmrbwu", // insert here you cloud name
                "api_key", "892445371743878", // insert here your api code
                "api_secret", "gqMDQzTCcF_CqBgKS_1lgufsUl0")); // insert here your api secret
        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
        manager.init();
		SpringApplication.run(FinalpblApplication.class, args);
	}

}
