package org.mh.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weapon")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaponConfig {

  private String weaponCode;
  private List<Action> actionList;


}
