package bigth.myserver.domain;

import bigth.myserver.domain.config.BaseTimes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UNQ_Role_Name", columnNames = "name"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Role extends BaseTimes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Type name;

    @Getter
    public enum Type {
        ROLE_USER("USER"), ROLE_ADMIN("ADMIN");

        private final String role;
        Type(String role) {
            this.role = role;
        }
    }
}
