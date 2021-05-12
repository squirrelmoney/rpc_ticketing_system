package com.lnsf.rpc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author money
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SysActorMovie implements Serializable {
    private static final Long SerialVersionUID = 1L;

    private Long movieId;

    private Long actorId;

    private Long actorRoleId;
}
