package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.v2.BeerDtoV2;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDtoV2 beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDtoV2 dto);
}
