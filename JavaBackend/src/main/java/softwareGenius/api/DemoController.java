package softwareGenius.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.DemoModel;
import softwareGenius.service.DemoModelService;

import java.util.List;

@RequestMapping("api/demo")
@RestController
public class DemoController {

    private DemoModelService demoModelService;

    @Autowired
    public DemoController(DemoModelService demoModelService) {
        this.demoModelService = demoModelService;
    }

    @GetMapping("/get/{id}")
    public DemoModel get(@PathVariable Integer id){
        return demoModelService.getOne(id);
    }

    @GetMapping("getAll")
    public List<DemoModel> getAll(){
        return demoModelService.getAll();
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody DemoModel user){
        return demoModelService.add(user);
    }

    @GetMapping(path = "{msg}")
    public String demoApi(@PathVariable("msg") String msg) {
        return msg;
    }

}
