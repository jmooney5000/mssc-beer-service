package guru.springframework.msscbeerservice.web.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private UUID customerId;
    private String name;

    public UUID getCustomerId() {
        return this.customerId;
    }

    public String getName() {
        return this.name;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CustomerDto)) return false;
        final CustomerDto other = (CustomerDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$customerId = this.getCustomerId();
        final Object other$customerId = other.getCustomerId();
        if (this$customerId == null ? other$customerId != null : !this$customerId.equals(other$customerId))
            return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CustomerDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $customerId = this.getCustomerId();
        result = result * PRIME + ($customerId == null ? 43 : $customerId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "CustomerDto(customerId=" + this.getCustomerId() + ", name=" + this.getName() + ")";
    }
}
