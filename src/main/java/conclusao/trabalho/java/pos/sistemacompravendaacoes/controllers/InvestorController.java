package conclusao.trabalho.java.pos.sistemacompravendaacoes.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Sell;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.SellP;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.services.ActionService;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.services.InvestorService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(InvestorController.BASE_URL)
public class InvestorController {

	public static final String BASE_URL = "/api/v1/investors";
	
	private final InvestorService investorService;
	private final ActionService actionService;
	
	public InvestorController(InvestorService investorService,
							  ActionService actionService) {
        this.investorService = investorService;
        this.actionService = actionService;
    }
	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "View List of Investors", notes="These endpoint will return all investors")
    public Set<Investor> getAll(){
        return investorService.getAll();
    }
	
	@GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Investor by Id")
    public Investor getById(@PathVariable String id){
        return investorService.getInvestorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new Investor")
    public Investor create(@RequestBody Investor investor){
        return investorService.createNewInvestor(investor);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update a new Investor")
    public Investor updateInvestor(@PathVariable String id, @RequestBody Investor investor){
        return investorService.saveInvestor(id, investor);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete a new Investor")
    public void deleteInvestor(@PathVariable String id){
    	investorService.deleteInvestorById(id);
    }

	@GetMapping({"/{id}/action"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Search actions by investor")
    public List<Company> getActionByInvestor(@PathVariable String id){
		Investor investor = new Investor();
		return investorService.getActionByInvestor(investor);
    }
	
	@GetMapping({"/{id}/company/{name}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Search actions company by investor")
    public List<Company> getActionCompanyByInvestor(@PathVariable String id, @PathVariable String name){
		return investorService.getActionCompanyByInvestor(id, name);
    }

    @PostMapping({"/{id}/sell"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Sell a Action by Investor")
    public void sellActionByCompany(@PathVariable String id, @RequestBody SellP sellP){
        actionService.sellActionByInvestor(id, sellP);
    }

    @PostMapping({"/{id}/buyAction"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Buy a Action by Investor")
    public void buyAction(@PathVariable String id, @RequestBody SellP sellP){
    	actionService.buyActionAllByInvestor(id, sellP);
    }
}
