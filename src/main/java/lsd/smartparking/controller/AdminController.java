package lsd.smartparking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api")
public class AdminController {
	
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

	
    @GetMapping("/admin/login")
    public @ResponseBody String loginAdmin() {
        LOG.info("GET successfully called on /secured resource");
        return "You are logged in";
    }

}