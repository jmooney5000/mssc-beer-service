package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.snippet.Attributes.key;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.msscbeerservice")
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;


    @Test
    public void getBeerById() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(get("/api/v2/beer/{beerId}", UUID.randomUUID().toString())
                .param("iscold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v2/beer",
                                pathParameters(
                                        parameterWithName("beerId").description("UUID of desired beer to get.")
                                ),
                                requestParameters(
                                        parameterWithName("iscold").description("Is Beer Cold Query param")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("Id of Beer").type(UUID.class),
                                        fieldWithPath("version").description("Version number"),
                                        fieldWithPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                        fieldWithPath("lastModifiedDate").description("Date Updated"),
                                        fieldWithPath("beerName").description("Beer Name"),
                                        fieldWithPath("beerStyle").description("Beer Style"),
                                        fieldWithPath("upc").description("UPC of Beer"),
                                        fieldWithPath("price").description("Price"),
                                        fieldWithPath("quantityOnHand").description("Quantity On hand")
                                )
                ));
    }

    @Test
    public void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDtoV2();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v2/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v2/beer",
                                requestFields(
                                        fields.withPath("id").ignored(),
                                        fields.withPath("version").ignored(),
                                        fields.withPath("createdDate").ignored(),
                                        fields.withPath("lastModifiedDate").ignored(),
                                        fields.withPath("beerName").description("Name of the beer"),
                                        fields.withPath("beerStyle").description("Style of Beer"),
                                        fields.withPath("upc").description("Beer UPC").attributes(),
                                        fields.withPath("price").description("Beer Price"),
                                        fields.withPath("quantityOnHand").ignored()
                                )
                ));
        Object o = fields.withPath("upc").description("Beer UPC").attributes();
        System.out.println(o);
    }

    @Test
    public void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDtoV2();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v2/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDtoV2(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(12121232434324L)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            FieldDescriptor fd;
            fd = fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
            return fd;
        }
    }
}