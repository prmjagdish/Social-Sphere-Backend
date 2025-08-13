package com.jagdish.SocailSphere.Config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dgaf7tyvp",
                "api_key", "777679477426459",
                "api_secret", "oTKwVXF18Sm9RQngQgLYra8oYmo"
        ));
    }

}
