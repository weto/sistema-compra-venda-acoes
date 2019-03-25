package conclusao.trabalho.java.pos.sistemacompravendaacoes.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.services.ActionService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(ActionController.BASE_URL)
public class ActionController {

	public static final String BASE_URL = "/api/v1/actions";
	
	private final ActionService actionService;
	
	public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }
	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "View List of Action", notes="These endpoint will return all actions")
    public Set<Action> getAll(){
        return actionService.getAll();
    }
	
	@GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Action by Id")
    public Action getById(@PathVariable String id){
        return actionService.getActionById(id);
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new Action")
    public Action create(@RequestBody Action action){
        return actionService.createNewAction(action);
    }

    @PostMapping({"launch"})
    @ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Launch a new Action")
    public Action launch(@RequestBody String idCompany, Integer numberActions){
        return new Action();
    }
    
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update a new Action")
    public Action updateAction(@PathVariable String id, @RequestBody Action action){
        return actionService.saveAction(id, action);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete a new Action")
    public void deleteAction(@PathVariable String id){
    	actionService.deleteActionById(id);
    }*/
    
}
