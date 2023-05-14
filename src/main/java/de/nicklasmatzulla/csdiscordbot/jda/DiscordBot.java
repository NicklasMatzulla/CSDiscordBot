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

package de.nicklasmatzulla.csdiscordbot.jda;

import de.nicklasmatzulla.csdiscordbot.config.SettingsConfiguration;
import de.nicklasmatzulla.csdiscordbot.jda.command.EmbedCommand;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Getter
@Service
public class DiscordBot extends ListenerAdapter {

    @Getter
    private static DiscordBot instance;

    private ShardManager shardManager;

    public DiscordBot() {
        DiscordBot.instance = this;
        start();
    }

    private void start() {
        final SettingsConfiguration settingsConfiguration = SettingsConfiguration.getInstance();
        final Collection<GatewayIntent> gatewayIntents = List.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.MESSAGE_CONTENT);
        this.shardManager = DefaultShardManagerBuilder.create(settingsConfiguration.getDiscordBotToken(), gatewayIntents)
                .disableCache(CacheFlag.SCHEDULED_EVENTS)
                .setAutoReconnect(true)
                .setIdle(true)
                .setActivity(Activity.playing(settingsConfiguration.getDiscordActivity()))
                .addEventListeners(
                        this,
                        new EmbedCommand())
                .build();
    }

    @Override
    public void onReady(ReadyEvent event) {
        final JDA jda = event.getJDA();
        registerCommands(jda);
    }

    private void registerCommands(final JDA jda) {
        jda.upsertCommand(Commands.slash("embed", "Create an embed message")
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED))
                .queue();
    }

}
