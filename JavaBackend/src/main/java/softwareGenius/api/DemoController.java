package softwareGenius.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/demo")
@RestController
public class DemoController {

    @GetMapping(path = "{msg}")
    public String demoApi(@PathVariable("msg") String msg) {
        return msg;
    }

}
