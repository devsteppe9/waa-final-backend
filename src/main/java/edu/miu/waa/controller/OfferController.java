package edu.miu.waa.controller;

import java.util.List;

import edu.miu.waa.dto.request.OfferPatchRequestDto;
import edu.miu.waa.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import edu.miu.waa.dto.request.OfferRequestDto;
import edu.miu.waa.dto.response.OfferResponseDto;
import edu.miu.waa.service.OfferService;

@RestController
@RequestMapping("/api/v1/offers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OfferController {

    private final OfferService offerService;
    @Autowired
    private SecurityUtils securityUtils;// TODO: get current userId from context


    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OfferResponseDto> getAllOffers() {
        return offerService.findAllOffers(securityUtils.getCurrentUserId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody OfferRequestDto offerRequestDto) throws Exception {
        return offerService.create(securityUtils.getCurrentUserId(), offerRequestDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long update(@PathVariable long id, @RequestBody OfferPatchRequestDto offerRequestDto) {
        offerService.update(securityUtils.getCurrentUserId(), id, offerRequestDto);
        return id;
    }
}
