package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.domain.Beer.BeerBuilder;
import guru.springframework.msscbeerservice.web.model.v2.BeerDtoV2;
import guru.springframework.msscbeerservice.web.model.v2.BeerDtoV2.BeerDtoV2Builder;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-03T18:18:55-0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (AdoptOpenJDK)"
)
@Component
public class BeerMapperImpl implements BeerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerDtoV2 beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDtoV2Builder beerDtoV2 = BeerDtoV2.builder();

        beerDtoV2.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDtoV2.version( beer.getVersion().intValue() );
        }
        beerDtoV2.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDtoV2.lastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDtoV2.beerName( beer.getBeerName() );
        beerDtoV2.beerStyle( beer.getBeerStyle() );
        beerDtoV2.upc( beer.getUpc() );
        beerDtoV2.price( beer.getPrice() );

        return beerDtoV2.build();
    }

    @Override
    public Beer beerDtoToBeer(BeerDtoV2 dto) {
        if ( dto == null ) {
            return null;
        }

        BeerBuilder beer = Beer.builder();

        beer.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            beer.version( dto.getVersion().longValue() );
        }
        beer.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        beer.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        beer.beerName( dto.getBeerName() );
        beer.beerStyle( dto.getBeerStyle() );
        beer.upc( dto.getUpc() );
        beer.price( dto.getPrice() );

        return beer.build();
    }
}
