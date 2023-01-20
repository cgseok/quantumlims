package lims.core.common.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADM_ACCESS_TOKEN")
//@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenEntity extends BaseEntity {

    @Id
    @Column(name="userId", length=20)
    private String userId;

    @Column(name="accessToken")
    private String accessToken;

}
