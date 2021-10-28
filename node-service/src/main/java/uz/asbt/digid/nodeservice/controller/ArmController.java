package uz.asbt.digid.nodeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asbt.digid.common.models.rest.Response;
import uz.asbt.digid.nodeservice.model.Arm;
import uz.asbt.digid.nodeservice.service.ArmService;

import java.util.List;

@RestController
@RequestMapping("/arm/")
public class ArmController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ArmService armService;

    @Autowired
    public ArmController(ArmService armService) {
        this.armService = armService;
    }

    @PostMapping(value = "list")
    public ResponseEntity<Response<List<Arm>>> list() {
        Response<List<Arm>> response = new Response<>();
        try {
            List<Arm> arms = armService.list();
            response.setCode(0);
            response.setMessage("OK");
            response.setResponse(arms);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(-9999);
            response.setMessage("Can't getting list of arms");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "start")
    public ResponseEntity<Response<Arm>> start(@RequestBody Arm arm) {
        Response<Arm> response = new Response<>();
        try {
            Arm result = armService.start(arm);
            response.setCode(0);
            response.setMessage("OK");
            response.setResponse(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(-9999);
            response.setMessage("Can't start arm");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "all")
    public ResponseEntity<Response<List<Arm>>> startAll() {
        Response<List<Arm>> response = new Response();
        try {
            List<Arm> arms = armService.startAll();
            response.setCode(0);
            response.setMessage("OK");
            response.setResponse(arms);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            response.setCode(-9999);
            response.setMessage("Can't start arms");
        }
        return ResponseEntity.ok(response);
    }

}
