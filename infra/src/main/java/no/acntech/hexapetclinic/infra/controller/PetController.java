package no.acntech.hexapetclinic.infra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.PetCreationDto;
import no.acntech.hexapetclinic.app.dto.DataTransferObjects.PetResponseDto;
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
 * PetController is a REST controller responsible for handling HTTP requests related to pets.
 *
 * It provides endpoints to perform CRUD operations on pets, such as creating a new pet, retrieving pet details by ID or identifier,
 * and fetching all pets. This controller delegates the heavy lifting to the associated service layer.
 */
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController extends BaseController {

  private final AppService appService;

  @Operation(summary = "Create a new pet")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Pet successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  public ResponseEntity<ResponseDto<PetResponseDto>> createPet(@RequestBody PetCreationDto petCreationDto) {
    // Delegate to service to handle pet creation
    PetResponseDto petResponseDto = appService.handlePetCreationDto(petCreationDto);

    // Create location header
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.LOCATION, "/pets/" + petResponseDto.identifier());

    // Wrap the response with ResponseDto and include location header
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .headers(headers)
        .body(new ResponseDto<>(petResponseDto));
  }

  @Operation(summary = "Get pet by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pet found"),
      @ApiResponse(responseCode = "404", description = "Pet not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<PetResponseDto>> getPetById(@PathVariable("id") Long id) {
    // Delegate to service to retrieve a single pet
    PetResponseDto petResponseDto = appService.getPetById(id);

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(petResponseDto));
  }

  @Operation(summary = "Get pet by identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pet found"),
      @ApiResponse(responseCode = "404", description = "Pet not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/identifier/{identifier}")
  public ResponseEntity<ResponseDto<PetResponseDto>> getPetByIdentifier(@PathVariable("identifier") String identifier) {
    // Delegate to service to retrieve pet by identifier
    PetResponseDto petResponseDto = appService.getPetByIdentifier(identifier);

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(petResponseDto));
  }

  @Operation(summary = "Get all pets")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pets retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public ResponseEntity<ResponseDto<List<PetResponseDto>>> getAllPets() {
    // Delegate to service to retrieve all pets
    List<PetResponseDto> petResponseDtos = appService.getAllPets();

    // Wrap the response with ResponseDto
    return ResponseEntity.ok(new ResponseDto<>(petResponseDtos));
  }
}