package edu.miu.waa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import edu.miu.waa.dto.request.OfferRequestDto;
import edu.miu.waa.dto.response.OfferResponseDto;
import edu.miu.waa.service.OfferService;

@RestController
@RequestMapping("/api/v1/offers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OfferController {

    private final OfferService offerService;
    private final Long currentUserId = 12L; // TODO: get current user id
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    Long userId = auth.getDetails().getId();

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponseDto> getAllOffers() {
        return offerService.findAllOffers(currentUserId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferResponseDto getOfferById(@PathVariable long id) {
        return offerService.findOfferById(currentUserId, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(OfferRequestDto offerRequestDto) throws Exception {
        return offerService.create(currentUserId, offerRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody OfferRequestDto offerRequestDto) {
        offerService.update(currentUserId, id, offerRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        offerService.delete(currentUserId, id);
    }

}
