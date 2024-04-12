package com.cs5324.monitorbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Length(min = 8)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Length(min = 4, max = 20)
    private String username;

    @Email
    private String email;
    private boolean enabled;
    private boolean verified; //may need to remove or modify to support spring security

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "name"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "dateOfEvent"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Event> events = new HashSet<>();

    //Removed to match with removal of reference in MEDIA entity
//    @OneToMany(mappedBy = "user")
//    @ToString.Exclude
//    @JsonIdentityInfo(
//            generator = ObjectIdGenerators.PropertyGenerator.class,
//            property = "title"
//    )
//    @JsonIdentityReference(alwaysAsId = true)
//    private Set<Media> media = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "title"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Page> pages = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "title"
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Post> posts = new HashSet<>();

    public void addRoleToUser(Role role) {
        this.roles.add(role);
    }
    public void addPost(Post post) {
        this.posts.add(post);
        post.setUser(this);
    }
}
