package com.example.demo.user;

import com.example.demo.departments.Department;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    
    @Column(nullable = false)
    @Getter
    private String firstName;
    
    @Column(nullable = false)
    @Getter
    private String lastName;
    
    @Column(unique = true, nullable = false)
    @Getter
    private String email;
    
    @Column
    private LocalDate dob;
    
    @Column
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Transient
    private Integer age;
    
    @Column
    @Getter
    private LocalDate startDate;
    
    @Column
    @Getter
    private String phoneNumber;
    
    @Column
    @Getter
    private String address;
    
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    @Getter
    private Department department;
    
    @Column
    private String position;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    
     public int getAge() {
        if (this.dob != null) {
            return Period
                    .between(this.dob, LocalDate.now())
                    .getYears();
        }
        return 0;
    }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    
    @Override
    public String getPassword() {
        return passwordHash;
    }
    
    public void setPassword(String password) {
        this.passwordHash = password;
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
