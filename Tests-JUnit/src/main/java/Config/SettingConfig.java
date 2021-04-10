package Config;

import org.aeonbits.owner.Config;

@Config.Sources({"file:src/main/resources/SettingConfig.properties"})
public interface SettingConfig extends Config {
    String url();
}
