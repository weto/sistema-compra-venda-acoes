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
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.CompanyDTO;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.services.ActionService;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.services.CompanyService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(CompanyController.BASE_URL)
public class CompanyController {
	
	public static final String BASE_URL = "/api/v1/companies";
	
	private final CompanyService companyService;
	private final ActionService actionService;
	
	public CompanyController(CompanyService companyService, ActionService actionService) {
        this.companyService = companyService;
        this.actionService = actionService;
    }
	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "View List of Companies", notes="These endpoint will return all companies")
    public Set<Company> getAll(){
        return companyService.getAll();
    }
	
	@GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Company by Id")
    public Company getById(@PathVariable String id){
        return companyService.getCompanyById(id);
    }

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new Company")
    public Company create(@RequestBody Company Company){
        return companyService.createNewCompany(Company);
    }

	@PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update an existing Company")
    public Company updateCompany(@PathVariable String id, @RequestBody CompanyDTO companyDTO){
        return companyService.saveCompany(id, companyDTO);
    }

	@DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update a Company Property")
    public void deleteCompany(@PathVariable String id){
    	companyService.deleteCompanyById(id);
    }

	@GetMapping({"/{nameCompany}/investor/{nameInvestor}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Company by Investor")
    public List<Company> getByCompanyInvestor(@PathVariable String nameCompany, @PathVariable String nameInvestor){
		return companyService.getByCompanyInvestor(nameCompany, nameInvestor);
    }

	@PutMapping({"/{id}/launch/{numberActions}"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Launch Action by Company")
    public void launchAction(@PathVariable String id, @PathVariable Integer numberActions){
		this.actionService.launch(numberActions, id);
    }
	
	@GetMapping({"/{nameCompany}/action"})
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Action by Company")
    public List<Company> getByActionInvestor(@PathVariable String nameCompany){
		return companyService.getByActionCompany(nameCompany);
    }

	
}
