package io.borys.webshop.product;

//@Embeddable
////@Entity
//public record Specification(
////        @GeneratedValue(strategy = GenerationType.AUTO) @Id String id,
//        String name,
//        String value) {
//}

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Specification {
    private String name;
    private String value;
}
