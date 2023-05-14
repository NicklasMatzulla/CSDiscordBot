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

package de.nicklasmatzulla.csdiscordbot.jda.command;

import de.nicklasmatzulla.csdiscordbot.config.command.EmbedCommandConfiguration;
import de.nicklasmatzulla.csdiscordbot.config.embed.EmbedBuilderEmbedConfiguration;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.punishcon.commons.config.EmbedConfiguration;
import org.apache.commons.validator.routines.UrlValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EmbedCommand extends ListenerAdapter {

    private final MessageEmbed embed = EmbedBuilderEmbedConfiguration.getInstance().getEmbedBuilder().build();
    private final EmbedCommandConfiguration embedCommandConfiguration = EmbedCommandConfiguration.getInstance();
    private final HashMap<Long, EmbedConfiguration> embedCache = new HashMap<>();
    private final HashMap<Long, List<MessageEmbed.Field>> fieldsCache = new HashMap<>();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) {
            return;
        }
        if (!event.getName().equals("embed")) {
            return;
        }
        event.deferReply()
                .addEmbeds(this.embed)
                .addActionRow(
                        StringSelectMenu.create("embedCommand")
                                .setPlaceholder(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuPlaceholder())
                                .addOptions(
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryAuthorIconUrl(), "embedBuilderAuthorIconUrl"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryAuthorTextAndUrl(), "embedBuilderAuthorTextAndUrl"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryBodyThumbnailUrl(), "embedBuilderBodyThumbnailUrl"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryBodyTitleAndUrl(), "embedBuilderBodyTitleAndUrl"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryBodyDescription(), "embedBuilderBodyDescription"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryBodyImageUrl(), "embedBuilderBodyImageUrl"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryFooterIconUrl(), "embedBuilderFooterIconUrl"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryFooterText(), "embedBuilderFooterText"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryFooterTimestamp(), "embedBuilderFooterTimestamp"),
                                        SelectOption.of(this.embedCommandConfiguration.getEmbedEmbedBuilderSelectionMenuEntryHexColor(), "embedBuilderHexColor")
                                )
                                .build()
                )
                .addActionRow(
                        Button.of(ButtonStyle.SECONDARY, "embedBuilderFieldSelectorBack", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorBack()).asDisabled(),
                        Button.of(ButtonStyle.PRIMARY, "embedBuilderFieldSelectorEdit1", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorEdit("1")),
                        Button.of(ButtonStyle.SECONDARY, "embedBuilderFieldSelectorNext", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorNext())
                )
                .addActionRow(
                        Button.of(ButtonStyle.DANGER, "embedBuilderCreate", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonCreate())
                )
                .queue();
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (!event.isFromGuild()) {
            return;
        }
        if (!event.getComponentId().equals("embedCommand")) {
            return;
        }
        for (SelectOption selectOption : event.getSelectedOptions()) {
            switch (selectOption.getValue()) {
                case "embedBuilderAuthorIconUrl" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalAuthorIconUrlModalTitle(), "embedBuilderEditAuthorIconUrl", "authorIconUrl", this.embedCommandConfiguration.getEmbedEmbedBuilderModalAuthorIconUrlFieldAuthorIconUrl(), null, TextInputStyle.SHORT, 1, 2048);
                case "embedBuilderAuthorTextAndUrl" -> showTextAndUrlInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalAuthorTextAndUrlModalTitle(), "embedBuilderEditAuthorTextAndUrl", "authorText", this.embedCommandConfiguration.getEmbedEmbedBuilderModalAuthorTextAndUrlFieldAuthorText(), null, TextInputStyle.PARAGRAPH, 1 ,256, "authorUrl", this.embedCommandConfiguration.getEmbedEmbedBuilderModalAuthorTextAndUrlFieldAuthorUrl());
                case "embedBuilderBodyThumbnailUrl" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyThumbnailUrlModalTitle(), "embedBuilderEditThumbnailUrl", "thumbnailUrl", this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyThumbnailUrlFieldBodyThumbnailUrl(), null, TextInputStyle.SHORT, 1, 2048);
                case "embedBuilderBodyTitleAndUrl" -> showTextAndUrlInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyTitleAndUrlModalTitle(), "embedBuilderEditBodyTitleAndUrl", "bodyTitle", this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyTitleAndUrlFieldBodyTitle(), null, TextInputStyle.PARAGRAPH, 1 ,256, "bodyUrl", this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyTitleAndUrlFieldBodyUrl());
                case "embedBuilderBodyDescription" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyDescriptionModalTitle(), "embedBuilderEditBodyDescription", "bodyDescription", this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyDescriptionFieldBodyDescription(), null, TextInputStyle.PARAGRAPH, 1, 2048);
                case "embedBuilderBodyImageUrl" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyImageUrlModalTitle(), "embedBuilderEditBodyImageUrl", "bodyImageUrl", this.embedCommandConfiguration.getEmbedEmbedBuilderModalBodyImageUrlFieldBodyImageUrl(), null, TextInputStyle.SHORT, 1, 2048);
                case "embedBuilderFooterIconUrl" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalFooterIconUrlModalTitle(), "embedBuilderEditFooterIconUrl", "footerIconUrl", this.embedCommandConfiguration.getEmbedEmbedBuilderModalFooterIconUrlFieldFooterIconUrl(), null, TextInputStyle.SHORT, 1, 2048);
                case "embedBuilderFooterText" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalFooterTextModalTitle(), "embedBuilderEditFooterText", "footerText", this.embedCommandConfiguration.getEmbedEmbedBuilderModalFooterTextFieldFooterText(), null, TextInputStyle.PARAGRAPH, 1, 2048);
                case "embedBuilderFooterTimestamp" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalFooterTimestampModalTitle(), "embedBuilderEditFooterTimestamp", "footerTimestamp", this.embedCommandConfiguration.getEmbedEmbedBuilderModalFooterTimestampFieldFooterTimestamp(), "<now>/00.00.0000 00:00", TextInputStyle.SHORT, 5, 16);
                case "embedBuilderHexColor" -> showTextInputModal(event, this.embedCommandConfiguration.getEmbedEmbedBuilderModalHexColorModalTitle(), "embedBuilderEditHexColor", "hexColor", this.embedCommandConfiguration.getEmbedEmbedBuilderModalHexColorFieldHexColor(), "#FFFFFF", TextInputStyle.SHORT, 7, 7);
            }
        }
    }

    private void showTextInputModal(
            @NotNull final StringSelectInteractionEvent event,
            @NotNull final String modalTitle,
            @NotNull final String modalId,
            @NotNull final String textInputFieldId,
            @NotNull final String textInputFieldName,
            @Nullable final String textInputFieldPlaceholder,
            @NotNull final TextInputStyle textInputFieldStyle,
            final int textInputFieldMinChars,
            final int textInputFieldMaxChars
    ) {
        final TextInput textInputField = TextInput.create(textInputFieldId, textInputFieldName, textInputFieldStyle)
                .setPlaceholder(textInputFieldPlaceholder)
                .setRequiredRange(textInputFieldMinChars, textInputFieldMaxChars)
                .build();
        final Modal modal = Modal.create(modalId, modalTitle)
                .addComponents(ActionRow.of(textInputField))
                .build();
        event.replyModal(modal).queue();
    }

    @SuppressWarnings("SameParameterValue")
    private void showTextAndUrlInputModal(
            @NotNull final StringSelectInteractionEvent event,
            @NotNull final String modalTitle,
            @NotNull final String modalId,
            @NotNull final String textInputFieldId,
            @NotNull final String textInputFieldName,
            @Nullable final String textInputFieldPlaceholder,
            @NotNull final TextInputStyle textInputFieldStyle,
            final int textInputFieldMinChars,
            final int textInputFieldMaxChars,
            @NotNull final String urlInputFieldId,
            @NotNull final String urlInputFieldName
    ) {
        final TextInput textInputField = TextInput.create(textInputFieldId, textInputFieldName, textInputFieldStyle)
                .setPlaceholder(textInputFieldPlaceholder)
                .setRequiredRange(textInputFieldMinChars, textInputFieldMaxChars)
                .build();
        final TextInput urlInputField = TextInput.create(urlInputFieldId, urlInputFieldName, TextInputStyle.SHORT)
                .setRequired(false)
                .build();
        final Modal modal = Modal.create(modalId, modalTitle)
                .addComponents(
                        ActionRow.of(textInputField),
                        ActionRow.of(urlInputField)
                )
                .build();
        event.replyModal(modal).queue();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.isFromGuild()) {
            return;
        }
        final String componentId = event.getComponentId();
        final Long userId = event.getUser().getIdLong();
        if (componentId.equals("embedBuilderCreate")) {
            final EmbedConfiguration embedConfiguration = this.embedCache.remove(userId);
            if (embedConfiguration == null) {
                return;
            }
            final List<MessageEmbed.Field> fields = this.fieldsCache.remove(userId);
            if (fields != null) {
                for (final MessageEmbed.Field field : fields) {
                    if (field != null) {
                        embedConfiguration.addField(field.getName(), field.getValue(), field.isInline());
                    }
                }
            }
            event.getChannel().sendMessage("")
                    .addEmbeds(embedConfiguration.getEmbedBuilder().build())
                    .queue();
            event.getMessage().delete().queue();
            event.deferEdit().queue();
        }
        if (componentId.contains("embedBuilderFieldSelector")) {
            final Button editFieldButton = event.getMessage().getActionRows().stream()
                    .map(LayoutComponent::getButtons)
                    .filter(buttons -> buttons.contains(event.getButton()))
                    .flatMap(List::stream)
                    .toList()
                    .get(1);
            final int fieldIdAsInt = Integer.decode(editFieldButton.getId().replace("embedBuilderFieldSelectorEdit", ""));
            final String fieldIdAsString = String.valueOf(fieldIdAsInt);
            final String shortComponentId = componentId.replace(fieldIdAsString, "");
            switch (shortComponentId) {
                case "embedBuilderFieldSelectorBack" -> updateFieldSelectorField(fieldIdAsInt, fieldIdAsInt-1, event);
                case "embedBuilderFieldSelectorEdit" -> {
                    final TextInput fieldName = TextInput.create("fieldName", this.embedCommandConfiguration.getEmbedEmbedBuilderModalEditFieldFieldName(), TextInputStyle.SHORT)
                            .setRequired(false)
                            .setRequiredRange(1, 256)
                            .build();
                    final TextInput fieldValue = TextInput.create("fieldValue", this.embedCommandConfiguration.getEmbedEmbedBuilderModalEditFieldFieldValue(), TextInputStyle.PARAGRAPH)
                            .setRequired(false)
                            .setMaxLength(2048)
                            .build();
                    final TextInput fieldInline = TextInput.create("fieldInline", this.embedCommandConfiguration.getEmbedEmbedBuilderModalEditFieldFieldInline(), TextInputStyle.SHORT)
                            .setPlaceholder("true/false")
                            .setRequired(true)
                            .build();
                    event.replyModal(
                                    Modal.create("embedBuilderEditField" + fieldIdAsString, this.embedCommandConfiguration.getEmbedEmbedBuilderModalEditFieldModalTitle(fieldIdAsString))
                                            .addComponents(
                                                    ActionRow.of(fieldName),
                                                    ActionRow.of(fieldValue),
                                                    ActionRow.of(fieldInline)
                                            )
                                            .build()
                            )
                            .queue();
                }
                case "embedBuilderFieldSelectorNext" -> updateFieldSelectorField(fieldIdAsInt, fieldIdAsInt+1, event);
            }
        }
    }

    private void updateFieldSelectorField(final int oldFieldId, final int newFieldId, @NotNull final ButtonInteractionEvent event) {
        final List<LayoutComponent> components = event.getMessage().getComponents();
        final LayoutComponent fieldSelectorComponent = components.get(1);
        if (newFieldId > 1) {
            fieldSelectorComponent.updateComponent("embedBuilderFieldSelectorBack", Button.of(ButtonStyle.SECONDARY, "embedBuilderFieldSelectorBack", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorBack()).asEnabled());
        } else {
            fieldSelectorComponent.updateComponent("embedBuilderFieldSelectorBack", Button.of(ButtonStyle.SECONDARY, "embedBuilderFieldSelectorBack", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorBack()).asDisabled());
        }
        if (newFieldId < 25) {
            fieldSelectorComponent.updateComponent("embedBuilderFieldSelectorNext", Button.of(ButtonStyle.SECONDARY, "embedBuilderFieldSelectorNext", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorNext()).asEnabled());
        } else {
            fieldSelectorComponent.updateComponent("embedBuilderFieldSelectorNext", Button.of(ButtonStyle.SECONDARY, "embedBuilderFieldSelectorNext", this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorNext()).asDisabled());
        }
        fieldSelectorComponent.updateComponent("embedBuilderFieldSelectorEdit" + oldFieldId, Button.of(ButtonStyle.PRIMARY, "embedBuilderFieldSelectorEdit" + newFieldId, this.embedCommandConfiguration.getEmbedEmbedBuilderButtonFieldSelectorEdit(String.valueOf(newFieldId))));
        event.getMessage().editMessageComponents(components).queue();
        event.deferEdit().queue();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (!event.isFromGuild()) {
            return;
        }
        final String modalId = event.getModalId();
        final Long userId = event.getUser().getIdLong();
        final EmbedConfiguration embedConfiguration = this.embedCache.computeIfAbsent(userId, key -> new EmbedConfiguration());
        final UrlValidator urlValidator = new UrlValidator();
        if (modalId.contains("embedBuilderEditField")) {
            final List<MessageEmbed.Field> fields = this.fieldsCache.computeIfAbsent(userId, key -> new ArrayList<>(Collections.nCopies(25, null)));
            final int fieldId = Integer.parseInt(modalId.replace("embedBuilderEditField", ""))-1;
            final String fieldName = event.getValue("fieldName").getAsString();
            final String fieldValue = event.getValue("fieldValue").getAsString();
            final boolean fieldInline = event.getValue("fieldInline").getAsString().equalsIgnoreCase("true");
            fields.set(fieldId, new MessageEmbed.Field(fieldName, fieldValue, fieldInline));
        }
        switch (modalId) {
            case "embedBuilderEditAuthorIconUrl" -> {
                final String authorIconUrl = event.getValue("authorIconUrl").getAsString();
                if (!authorIconUrl.equals("") && urlValidator.isValid(authorIconUrl)) {
                    embedConfiguration.setAuthorIconUrl(authorIconUrl);
                }
            }
            case "embedBuilderEditAuthorTextAndUrl" -> {
                final String authorText = event.getValue("authorText").getAsString();
                final String authorUrl = event.getValue("authorUrl").getAsString();
                if (!authorText.equals("")) {
                    embedConfiguration.setAuthorText(authorText);
                    if (!authorUrl.equals("") && urlValidator.isValid(authorUrl)) {
                        embedConfiguration.setAuthorUrl(authorUrl);
                    }
                }
            }
            case "embedBuilderEditThumbnailUrl" -> {
                final String thumbnailUrl = event.getValue("thumbnailUrl").getAsString();
                if (!thumbnailUrl.equals("") && urlValidator.isValid(thumbnailUrl)) {
                    embedConfiguration.setBodyThumbnailUrl(thumbnailUrl);
                }
            }
            case "embedBuilderEditBodyTitleAndUrl" -> {
                final String bodyTitle = event.getValue("bodyTitle").getAsString();
                final String bodyUrl = event.getValue("bodyUrl").getAsString();
                if (!bodyTitle.equals("")) {
                    embedConfiguration.setBodyTitle(bodyTitle);
                    if (!bodyUrl.equals("") && urlValidator.isValid(bodyUrl)) {
                        embedConfiguration.setBodyTitleUrl(bodyUrl);
                    }
                }
            }
            case "embedBuilderEditBodyDescription" -> {
                final String bodyDescription = event.getValue("bodyDescription").getAsString();
                if (!bodyDescription.equals("")) {
                    embedConfiguration.setBodyDescription(bodyDescription);
                }
            }
            case "embedBuilderEditBodyImageUrl" -> {
                final String bodyImageUrl = event.getValue("bodyImageUrl").getAsString();
                if (!bodyImageUrl.equals("") && urlValidator.isValid(bodyImageUrl)) {
                    embedConfiguration.setBodyImageUrl(bodyImageUrl);
                }
            }
            case "embedBuilderEditFooterIconUrl" -> {
                final String footerIconUrl = event.getValue("footerIconUrl").getAsString();
                if (!footerIconUrl.equals("") && urlValidator.isValid(footerIconUrl)) {
                    embedConfiguration.setFooterIconUrl(footerIconUrl);
                }
            }
            case "embedBuilderEditFooterText" -> {
                final String footerText = event.getValue("footerText").getAsString();
                if (!footerText.equals("")) {
                    embedConfiguration.setFooterText(footerText);
                }
            }
            case "embedBuilderEditFooterTimestamp" -> {
                final String footerTimestamp = event.getValue("footerTimestamp").getAsString();
                if (footerTimestamp.equalsIgnoreCase("<now>") || footerTimestamp.matches("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}")) {
                    embedConfiguration.setFooterTimestamp(footerTimestamp);
                }
            }
            case "embedBuilderEditHexColor" -> {
                final String hexColor = event.getValue("hexColor").getAsString();
                if (!hexColor.equals("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
                    embedConfiguration.setHexColor(hexColor);
                }
            }
        }
        event.deferEdit().queue();
    }
}
