package lsd.smartparking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class FrontendController {

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    // PROBLEM: chrome doesn't detect pwa	
    @RequestMapping(value = "{_:^(?!index\\.html|api|manifest\\.json|service-worker\\.js|precache).*$}")
    public String redirectFrontend() {
        return "forward:/";
    }
    
}