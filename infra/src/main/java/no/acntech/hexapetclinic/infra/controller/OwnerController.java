package no.acntech.hexapetclinic.infra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.OwnerCreationDto;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.OwnerResponseDto;
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
 * The OwnerController is a REST controller that manages operations related to Owner entities.
 * <p>
 * This controller exposes endpoints for creating a new owner, retrieving an owner by ID,
 * and fetching all owners. It utilizes the {@link AppService} to handle business logic pertaining
 * to owner-related operations.
 * <p>
 * The controller implements the following main functionality:
 * - Creating a new owner.
 * - Retrieving a specific owner by their ID.
 * - Fetching a list of all owners.
 * <p>
 * Responses for each operation are wrapped in a {@link ResponseDto} for consistent response
 * formatting. HTTP status codes and headers are used to reflect the outcome of each operation.
 *
 * This controller extends the {@link BaseController}, inheriting logging capabilities.
 * It uses constructor injection, with {@link RequiredArgsConstructor} to manage dependencies.
 */
@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController extends BaseController {

  // Autowired AppService through constructor injection (using lombok's @RequiredArgsConstructor).
  private final AppService appService;

  @Operation(summary = "Create a new owner")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Owner successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  public ResponseEntity<ResponseDto<OwnerResponseDto>> createOwner(@RequestBody OwnerCreationDto ownerCreationDto) {
    // Delegate to service to handle creation
    OwnerResponseDto ownerResponseDto = appService.handleOwnerCreationDto(ownerCreationDto);

    // Create location header
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/owners/" + ownerResponseDto.id());

    // Wrap the response with ResponseDto and include location header
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .headers(headers)
        .body(new ResponseDto<>(ownerResponseDto));
  }

  @Operation(summary = "Get owner by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Owner found"),
      @ApiResponse(responseCode = "404", description = "Owner not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<OwnerResponseDto>> getOwnerById(@PathVariable("id") Long id) {
    // Delegate to service to retrieve an owner by ID
    OwnerResponseDto ownerResponseDto = appService.getOwnerById(id);

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(ownerResponseDto));
  }

  @Operation(summary = "Get all owners")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Owners retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public ResponseEntity<ResponseDto<List<OwnerResponseDto>>> getAllOwners() {
    // Delegate to service to retrieve all owners
    List<OwnerResponseDto> ownerResponseDtos = appService.getAllOwners();

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(ownerResponseDtos));
  }
}