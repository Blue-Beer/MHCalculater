package org.mh;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.mh.domain.Action;
import org.mh.domain.HitData;
import org.mh.domain.Weapon;

public class CSVParser {

  // 解析 CSV 文件，返回一个包含 Weapon 对象的列表
  public static List<Weapon> parseCSV(String filePath) {
    Map<String, Weapon> weaponMap = new HashMap<>();
    Map<String, Action> actionMap = new HashMap<>();

    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      String[] nextLine;
      reader.readNext(); // Skip header row

      while ((nextLine = reader.readNext()) != null) {
        // 检查每行的列数是否符合要求
        if (nextLine.length < 8) {
          System.out.println("Skipping row with insufficient columns: " + Arrays.toString(nextLine));
          continue; // 跳过列数不足的行
        }

        String weaponCode = nextLine[0].trim(); // 去除空格
        String actionId = nextLine[1].trim();   // 去除空格
        String actionName = nextLine[2].trim(); // 去除空格
        int hits = Integer.parseInt(nextLine[3].trim()); // 去除空格
        BigDecimal physicalValue = new BigDecimal(nextLine[4].trim()); // 去除空格
        BigDecimal elementCorrection = new BigDecimal(nextLine[5].trim()); // 去除空格
        int maxFrame = Integer.parseInt(nextLine[6].trim()); // 去除空格
        int minFrame = Integer.parseInt(nextLine[7].trim()); // 去除空格

        // 获取 Weapon 对象，若不存在则创建
        Weapon weapon = weaponMap.get(weaponCode);
        if (weapon == null) {
          weapon = new Weapon();
          weapon.setWeaponCode(weaponCode);
          weapon.setActionList(new ArrayList<>());
          weaponMap.put(weaponCode, weapon);
        }

        // 获取 Action 对象，若不存在则创建
        Action action = actionMap.get(actionId);
        if (action == null) {
          action = new Action();
          action.setId(actionId);
          action.setName(actionName);
          action.setHits(hits); // 设置 hits 为该 Action 的打击次数
          action.setMaxFrame(maxFrame);
          action.setMinFrame(minFrame);
          action.setHitDataList(new ArrayList<>());
          actionMap.put(actionId, action);
        } else {
          // 如果 Action 已存在，累加 hits
          action.setHits(action.getHits() + hits);
        }

        // 将当前行的 HitData 添加到 Action 的 hitDataList 中
        for (int i = 0; i < hits; i++) {
          HitData hitData = new HitData();
          hitData.setPhysicalValue(physicalValue); // 每个 hit 的 physicalValue
          hitData.setElementCorrection(elementCorrection); // 每个 hit 的 elementCorrection
          action.getHitDataList().add(hitData);
        }

        // 将 Action 添加到 Weapon 的 actionList 中（若尚未添加）
        if (!weapon.getActionList().contains(action)) {
          weapon.getActionList().add(action);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CsvValidationException e) {
      throw new RuntimeException(e);
    }

    return new ArrayList<>(weaponMap.values());
  }
}