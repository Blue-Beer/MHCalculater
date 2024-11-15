package org.mh;
import java.util.List;
import org.mh.domain.Action;
import org.mh.domain.HitData;
import org.mh.domain.Weapon;

public class Main {
  public static void main(String[] args) {
    List<Weapon> weapons = CSVParser.parseCSV("src/main/resources/dataCsv/weapon1.csv");

    // 打印结果
    for (Weapon weapon : weapons) {
      System.out.println("Weapon: " + weapon.getWeaponCode());
      for (Action action : weapon.getActionList()) {
        System.out.println("  Action: " + action.getName() + " - Hits: " + action.getHits());
        for (HitData hitData : action.getHitDataList()) {
          System.out.println("    HitData: PhysicalValue = " + hitData.getPhysicalValue() +
              ", ElementCorrection = " + hitData.getElementCorrection());
        }
      }
    }
  }
}