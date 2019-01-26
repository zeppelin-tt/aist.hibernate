package aist.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tribe_commands")
public class TribeCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    // TODO: 19.01.2019 тут по всей логике вещей должен быть @NonNull, но он может быть не нужен для апдейта одного имени... Проблема промискуитета.... Решается в валидаторе...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tribe_id")
    private Tribe tribe;

    @OneToMany(mappedBy = "tribeCommand", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

}
