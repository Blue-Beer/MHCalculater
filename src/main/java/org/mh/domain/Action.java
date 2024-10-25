package org.mh.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {

  private String id;

  private String name;

  private int hits;

  private List<HitData> hitDataList;

}
