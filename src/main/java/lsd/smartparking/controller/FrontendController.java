package lsd.smartparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:8081")
@Controller()
public class FrontendController {

    @RequestMapping(value = "{_:^(?!index\\.html|api|css|js|img|static|manifest\\.json|service-worker\\.js|precache).*$}/**")
    public String redirectFrontend() {
        return "forward:/";
    }
    
}