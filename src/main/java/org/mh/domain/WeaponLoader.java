package org.mh.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class WeaponLoader {

  private final List<Weapon> weapons = new ArrayList<>();

  public WeaponLoader() throws IOException {
    YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:weapon*.yml");
    ConfigurableEnvironment environment = new StandardEnvironment();
    MutablePropertySources propertySources = environment.getPropertySources();

    for (Resource resource : resources) {
      PropertySource<?> propertySource = loader.load(resource.getFilename(), resource).get(0);
      propertySources.addLast(propertySource);  // Add PropertySource to the environment

      Binder binder = Binder.get(environment);  // Create Binder with the environment
      Weapon weapon = binder.bind("weapon", Weapon.class).get();
      weapons.add(weapon);
    }
  }

  public List<Weapon> getWeapons() {
    return weapons;
  }
}
