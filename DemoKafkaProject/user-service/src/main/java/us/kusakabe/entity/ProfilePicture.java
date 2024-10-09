package us.kusakabe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_pictures")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String type;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

}
