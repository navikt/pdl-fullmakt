package no.nav.pdl.fullmakt.app.fullmakt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
@Api(value = "Fullmakt", description = "REST API for fullmakt", tags = { "Fullmakt" })
public class FullmaktController {

	private static final Logger logger = LoggerFactory.getLogger(FullmaktController.class);

	@Autowired
	private FullmaktService service;

	@ApiOperation(value = "Get all fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt fetched", response = String.class),
			@ApiResponse(code = 404, message = "Fullmakt not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping()
	public List<Fullmakt> getAllFullmakt() {
		logger.info("Received a request for all fullmaktsgiver");
		return service.getAllFullmakt();
	}

	@ApiOperation(value = "Get all fullmakt for fullmaktsgiver", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt fetched", response = String.class),
			@ApiResponse(code = 404, message = "Fullmakt not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/fullmaktsgiver/{fullmaktsgiver}")
	public List<Fullmakt> getFullmaktForFullmaktsgiver(@PathVariable String fullmaktsgiver) {
		logger.info("Received a request for all fullmakt for fullmaktsgiver");
		return service.getFullmaktForFullmaktsgiver(fullmaktsgiver);
	}


	@ApiOperation(value = "Get all fullmakt for fullmektig", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt fetched", response = String.class),
			@ApiResponse(code = 404, message = "Fullmakt not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/fullmektig/{fullmektig}")
	public List<Fullmakt> getFullmaktForFullmektig(@PathVariable String fullmektig) {
		logger.info("Received a request for all fullmakt for fullmektig");
		return service.getFullmaktForFullmektig(fullmektig);
	}



	@ApiOperation(value = "Get fullmakt for fullmaktId", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt fetched", response = String.class),
			@ApiResponse(code = 404, message = "Fullmakt not found"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping("/{fullmaktId}")

	public Fullmakt getFullmakt(@PathVariable Long fullmaktId) {
		logger.info("Received a request for the fullmaktId={}", fullmaktId);
		return service.getFullmakt(fullmaktId);
	}

	@ApiOperation(value = "Create Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Fullmakt successfully created", response = Fullmakt.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Illegal arguments"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Fullmakt save(@Valid @RequestBody Fullmakt request) {
		logger.info("Received a requests to create fullmakt");
		return service.save(request);
	}

	@ApiOperation(value = "Update Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Fullmakt updated", response = Fullmakt.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Illegal arguments"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Fullmakt update(@Valid @RequestBody Fullmakt request) {
		logger.info("Received a request to update Fullmakt");
		return service.update(request);
	}

	@ApiOperation(value = "Delete Fullmakt", tags = {"Fullmakt"})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fullmakt deleted"),
			@ApiResponse(code = 400, message = "Illegal arguments"),
			@ApiResponse(code = 500, message = "Internal server error")})
	@DeleteMapping("/{fullmaktId}")
	@Transactional
	public void delete(@PathVariable Long fullmaktId) {
		logger.info("Received a request to delete fullmaktId={} ", fullmaktId);
		service.delete(fullmaktId);
		logger.info("Deleted fullmaktId={} ", fullmaktId);
	}

}
