package no.acntech.hexapetclinic.infra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.VisitCreationDto;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.VisitResponseDto;
import no.acntech.hexapetclinic.app.service.AppService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * VisitController is responsible for handling API requests related to visits in the application.
 * It provides endpoints to create, retrieve, and list visits.
 *
 * This controller is annotated with @RestController to indicate it's a Spring REST controller
 * and @RequestMapping to define the base URL for all endpoints in this controller.
 *
 * Dependencies:
 * - AppService: A service layer that encapsulates the business logic related to visits.
 *
 * Endpoints:
 * - POST /visits: Handles the creation of new visit records.
 * - GET /visits/{id}: Retrieves a visit by its unique identifier.
 * - GET /visits: Retrieves the list of all visits.
 */
@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController extends BaseController {

  // Dependency injection for AppService
  private final AppService appService;

  @Operation(summary = "Create a new visit")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Visit successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  public ResponseEntity<ResponseDto<VisitResponseDto>> createVisit(@RequestBody VisitCreationDto visitCreationDto) {
    // Delegate to service to handle visit creation
    VisitResponseDto visitResponseDto = appService.handleVisitCreationDto(visitCreationDto);

    // Create location header
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.LOCATION, "/visits/" + visitResponseDto.id());

    // Wrap the response with ResponseDto and include location header
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .headers(headers)
        .body(new ResponseDto<>(visitResponseDto));
  }

  @Operation(summary = "Get visit by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Visit found"),
      @ApiResponse(responseCode = "404", description = "Visit not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<VisitResponseDto>> getVisitById(@PathVariable("id") Long id) {
    // Retrieve a single visit by ID using the service
    VisitResponseDto visitResponseDto = appService.getVisitById(id);

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(visitResponseDto));
  }

  @Operation(summary = "Get all visits")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Visits retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public ResponseEntity<ResponseDto<List<VisitResponseDto>>> getAllVisits() {
    // Retrieve all visits using the service
    List<VisitResponseDto> visits = appService.getAllVisits();

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(visits));
  }
}