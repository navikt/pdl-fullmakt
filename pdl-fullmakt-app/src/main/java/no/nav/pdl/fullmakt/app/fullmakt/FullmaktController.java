package no.nav.pdl.fullmakt.app.fullmakt;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/fullmakt")
@Api(value = "Fullmakt", description = "REST API for common list of values", tags = { "Fullmakt" })
public class FullmaktController {

	private static final Logger logger = LoggerFactory.getLogger(FullmaktController.class);

	@Autowired
	private FullmaktService service;

	@ApiOperation(value = "Get the entire Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Entire Fullmakt fetched", response = Map.class),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping
	public Map findAll() {
		logger.info("Received a request for and returned the entire Fullmakt");
		return FullmaktService.fullmakter;
	}

	@ApiOperation(value = "Get codes and descriptions for listName", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fetched codes with description for listName", response = Map.class),
			@ApiResponse(code = 404, message = "ListName not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/{listName}")
	public Map getFullmaktByListName(@PathVariable String listName) {
		logger.info("Received a request for the Fullmakt with listName={}", listName);
		service.validateListNameExists(listName);
		return FullmaktService.fullmakter.get(ListName.valueOf(listName.toUpperCase()));
	}

	@ApiOperation(value = "Get description for code in listName", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Description fetched", response = String.class),
			@ApiResponse(code = 404, message = "Code or listName not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/{listName}/{code}")
	public String getDescriptionByListNameAndCode(@PathVariable String listName, @PathVariable String code) {
		logger.info("Received a request for the description of code={} in list={}", code, listName);
		service.validateListNameAndCodeExists(listName, code);
		return FullmaktService.fullmakter.get(ListName.valueOf(listName.toUpperCase())).get(code.toUpperCase());
	}

	@ApiOperation(value = "Create Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Fullmakt successfully created", response = Fullmakt.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Illegal arguments"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<Fullmakt> save(@Valid @RequestBody List<FullmaktRequest> requests) {
		logger.info("Received a requests to create Fullmakt");
		service.validateRequests(requests, false);

		return service.save(requests);
	}

	@ApiOperation(value = "Update Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Fullmakt updated", response = Fullmakt.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Illegal arguments"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<Fullmakt> update(@Valid @RequestBody List<FullmaktRequest> requests) {
		logger.info("Received a request to update Fullmakt");
		service.validateRequests(requests, true);

		return service.update(requests);
	}

	@ApiOperation(value = "Delete Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt deleted"),
			@ApiResponse(code = 400, message = "Illegal arguments"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@DeleteMapping("/{listName}/{code}")
	@Transactional
	public void delete(@PathVariable String listName, @PathVariable String code) {
		listName = listName.toUpperCase().trim();
		code = code.toUpperCase().trim();
		logger.info("Received a request to delete code={} in the list={}", code, listName);
		service.validateListNameAndCodeExists(listName, code);
		service.delete(ListName.valueOf(listName), code);
		logger.info("Deleted code={} in the list={}", code, listName);
	}

	@ApiOperation(value = "Refresh Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt refreshed"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/refresh")
	public ResponseEntity refresh() {
		logger.info("Refreshed the fullmakt");
		service.refreshCache();
		return new ResponseEntity(HttpStatus.OK);
	}
}