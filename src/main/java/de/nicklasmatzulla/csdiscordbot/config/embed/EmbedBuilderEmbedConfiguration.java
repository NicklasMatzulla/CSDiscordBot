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

package de.nicklasmatzulla.csdiscordbot.config.embed;

import lombok.Getter;
import net.punishcon.commons.config.EmbedConfiguration;
import net.punishcon.commons.config.annotations.ConfigurationOptions;
import org.springframework.stereotype.Service;

@Service
@ConfigurationOptions(relativeFilePath = "embed/embedBuilder.json")
public class EmbedBuilderEmbedConfiguration extends EmbedConfiguration {

    @Getter
    private static EmbedBuilderEmbedConfiguration instance;

    public EmbedBuilderEmbedConfiguration() {
        EmbedBuilderEmbedConfiguration.instance = this;
    }
}