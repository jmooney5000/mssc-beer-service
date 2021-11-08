package guru.springframework.msscbeerservice.web.mappers;

import guru.springframework.msscbeerservice.domain.Customer;
import guru.springframework.msscbeerservice.domain.Customer.CustomerBuilder;
import guru.springframework.msscbeerservice.web.model.CustomerDto;
import guru.springframework.msscbeerservice.web.model.CustomerDto.CustomerDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-03T18:18:55-0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (AdoptOpenJDK)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer customerDtoToCustomer(CustomerDto dto) {
        if ( dto == null ) {
            return null;
        }

        CustomerBuilder customer = Customer.builder();

        customer.customerId( dto.getCustomerId() );
        customer.name( dto.getName() );

        return customer.build();
    }

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDtoBuilder customerDto = CustomerDto.builder();

        customerDto.customerId( customer.getCustomerId() );
        customerDto.name( customer.getName() );

        return customerDto.build();
    }
}
