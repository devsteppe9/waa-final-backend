package edu.miu.waa.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import edu.miu.waa.dto.request.UserInDto;
import edu.miu.waa.dto.request.OfferRequestDto;
import edu.miu.waa.service.OfferService;
import edu.miu.waa.service.PropertyService;
import edu.miu.waa.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import edu.miu.waa.dto.response.OfferResponseDto;
import edu.miu.waa.model.Property;
import edu.miu.waa.model.User;

// TODO: implement testss
public class OfferControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();
    }

    @Test
    public void testCreateOffer() throws Exception {
        OfferRequestDto offerRequestDto = new OfferRequestDto();
        Property p = createProperty("Test Property");
        offerRequestDto.setPropertyId(p.getId());
        offerRequestDto.setOfferAmount(1000.0);
        offerRequestDto.setMessage("Offer Message");

        when(offerService.create(any(Long.class), any(OfferRequestDto.class))).thenReturn(1L);

        mockMvc.perform(post("/api/v1/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"New Offer\", \"description\": \"Offer Description\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllOffers() throws Exception {
        OfferResponseDto offerResponseDto = new OfferResponseDto();
        when(offerService.findAllOffers(any(Long.class))).thenReturn(Collections.singletonList(offerResponseDto));

        MvcResult res = mockMvc.perform(get("/api/v1/offers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()).andReturn();

        OfferResponseDto[] offerResponseDtos = objectMapper.readValue(res.getResponse().getContentAsString(),
                OfferResponseDto[].class);

        assert (offerResponseDtos.length == 0);

    }

    // @Test
    public void testGetOfferById() throws Exception {
        OfferResponseDto offerResponseDto = new OfferResponseDto();
        offerResponseDto.setId(1L);
        when(offerService.findOfferById(any(Long.class), eq(1L))).thenReturn(offerResponseDto);

        mockMvc.perform(get("/api/v1/offers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offerResponseDto.getId()));
    }

    @Test
    public void testUpdateOffer() throws Exception {
        OfferRequestDto offerRequestDto = new OfferRequestDto();

        mockMvc.perform(put("/api/v1/offers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Offer\", \"description\": \"Updated Description\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOffer() throws Exception {
        mockMvc.perform(delete("/api/v1/offers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private User createOwner() {
        UserInDto u = new UserInDto();
        u.setFirstName("Jane");
        u.setEmail("jdoe@waa.com");
        u.setLastName("Doe");
        u.setUsername("jjdoe");
        u.setPassword("password");
        u.setEnabled(true);
        u.setRole("OWNER");
        return userService.addUser(u);
    }

    private User createBuyer() {
        UserInDto u = new UserInDto();
        u.setFirstName("John");
        u.setEmail("jdoe@waa.com");
        u.setLastName("Doe");
        u.setUsername("jdoe");
        u.setPassword("password");
        u.setEnabled(true);
        u.setRole("BUYER");
        return userService.addUser(u);
    }
    
    private OfferResponseDto createOffer(User u) {
        OfferRequestDto offerRequestDto = new OfferRequestDto();
        Property p = createProperty("Test Property");
        offerRequestDto.setPropertyId(p.getId());
        offerRequestDto.setOfferAmount(1000.0);
        offerRequestDto.setMessage("Offer Message");
        Long offerId = offerService.create(u.getId(), offerRequestDto);
        return offerService.findOfferById(u.getId(), offerId);
    }

}