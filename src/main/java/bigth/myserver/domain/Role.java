package bigth.myserver.domain;

import bigth.myserver.domain.config.BaseTimes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Role extends BaseTimes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Type name;

    public enum Type {
        ROLE_USER, ROLE_ADMIN
    }
}
