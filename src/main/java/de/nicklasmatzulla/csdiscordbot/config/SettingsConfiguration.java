/*
 * Copyright 2023 Nicklas Matzulla
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.nicklasmatzulla.csdiscordbot.config;

import lombok.AccessLevel;
import lombok.Getter;
import net.punishcon.commons.config.JsonConfiguration;
import net.punishcon.commons.config.annotations.ConfigurationOptions;
import net.punishcon.commons.config.annotations.ConfigurationValue;

import java.util.Properties;

@SuppressWarnings("unused")
@Getter
@ConfigurationOptions(relativeFilePath = "settings.json")
public class SettingsConfiguration extends JsonConfiguration {

    @Getter
    private static SettingsConfiguration instance;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "mysql.driverClass")
    private String mysqlDriverClass;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "mysql.platform")
    private String mysqlPlatform;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "mysql.url")
    private String mysqlUrl;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "mysql.username")
    private String mysqlUser;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "mysql.password")
    private String mysqlPass;

    private Properties springProperties;

    public SettingsConfiguration() {
        SettingsConfiguration.instance = this;
        initializeSpringProperties();
    }

    private void initializeSpringProperties() {
        final Properties properties = new Properties();
        properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");
        properties.setProperty("spring.datasource.url", this.mysqlUrl);
        properties.setProperty("spring.datasource.username", this.mysqlUser);
        properties.setProperty("spring.datasource.password", this.mysqlPass);
        properties.setProperty("spring.datasource.driver-class-name", this.mysqlDriverClass);
        properties.setProperty("spring.jpa.database-platform", this.mysqlPlatform);
        this.springProperties = properties;
    }

}
