package bigth.myserver.domain;

import bigth.myserver.domain.config.BaseTimes;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class User extends BaseTimes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
}
