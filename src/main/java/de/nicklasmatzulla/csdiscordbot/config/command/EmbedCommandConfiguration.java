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

package de.nicklasmatzulla.csdiscordbot.config.command;

import lombok.AccessLevel;
import lombok.Getter;
import net.punishcon.commons.config.JsonConfiguration;
import net.punishcon.commons.config.annotations.ConfigurationOptions;
import net.punishcon.commons.config.annotations.ConfigurationValue;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Getter
@Service
@ConfigurationOptions(relativeFilePath = "commands/embed.json")
public class EmbedCommandConfiguration extends JsonConfiguration {

    @Getter
    private static EmbedCommandConfiguration instance;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.placeholder")
    private String embedEmbedBuilderSelectionMenuPlaceholder;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.authorIconUrl")
    private String embedEmbedBuilderSelectionMenuEntryAuthorIconUrl;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.authorTextAndUrl")
    private String embedEmbedBuilderSelectionMenuEntryAuthorTextAndUrl;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.bodyThumbnailUrl")
    private String embedEmbedBuilderSelectionMenuEntryBodyThumbnailUrl;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.bodyTitleAndUrl")
    private String embedEmbedBuilderSelectionMenuEntryBodyTitleAndUrl;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.bodyDescription")
    private String embedEmbedBuilderSelectionMenuEntryBodyDescription;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.bodyImageUrl")
    private String embedEmbedBuilderSelectionMenuEntryBodyImageUrl;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.footerIconUrl")
    private String embedEmbedBuilderSelectionMenuEntryFooterIconUrl;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.footerText")
    private String embedEmbedBuilderSelectionMenuEntryFooterText;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.footerTimestamp")
    private String embedEmbedBuilderSelectionMenuEntryFooterTimestamp;

    @ConfigurationValue(key = "embed.embedBuilder.selectionMenu.entry.hexColor")
    private String embedEmbedBuilderSelectionMenuEntryHexColor;

    @ConfigurationValue(key = "embed.embedBuilder.button.fieldSelectorBack")
    private String embedEmbedBuilderButtonFieldSelectorBack;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "embed.embedBuilder.button.fieldSelectorEdit")
    private String embedEmbedBuilderButtonFieldSelectorEdit;

    @ConfigurationValue(key = "embed.embedBuilder.button.fieldSelectorNext")
    private String embedEmbedBuilderButtonFieldSelectorNext;

    @ConfigurationValue(key = "embed.embedBuilder.button.create")
    private String embedEmbedBuilderButtonCreate;

    @ConfigurationValue(key = "embed.embedBuilder.modal.authorIconUrl.modalTitle")
    private String embedEmbedBuilderModalAuthorIconUrlModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.authorIconUrl.field.authorIconUrl")
    private String embedEmbedBuilderModalAuthorIconUrlFieldAuthorIconUrl;

    @ConfigurationValue(key = "embed.embedBuilder.modal.authorTextAndUrl.modalTitle")
    private String embedEmbedBuilderModalAuthorTextAndUrlModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.authorTextAndUrl.field.authorText")
    private String embedEmbedBuilderModalAuthorTextAndUrlFieldAuthorText;

    @ConfigurationValue(key = "embed.embedBuilder.modal.authorTextAndUrl.field.authorUrl")
    private String embedEmbedBuilderModalAuthorTextAndUrlFieldAuthorUrl;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyThumbnailUrl.modalTitle")
    private String embedEmbedBuilderModalBodyThumbnailUrlModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyThumbnailUrl.field.bodyThumbnailUrl")
    private String embedEmbedBuilderModalBodyThumbnailUrlFieldBodyThumbnailUrl;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyTitleAndUrl.modalTitle")
    private String embedEmbedBuilderModalBodyTitleAndUrlModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyTitleAndUrl.field.bodyTitle")
    private String embedEmbedBuilderModalBodyTitleAndUrlFieldBodyTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyTitleAndUrl.field.bodyUrl")
    private String embedEmbedBuilderModalBodyTitleAndUrlFieldBodyUrl;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyDescription.modalTitle")
    private String embedEmbedBuilderModalBodyDescriptionModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyDescription.field.bodyDescription")
    private String embedEmbedBuilderModalBodyDescriptionFieldBodyDescription;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyImageUrl.modalTitle")
    private String embedEmbedBuilderModalBodyImageUrlModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.bodyImageUrl.field.bodyImageUrl")
    private String embedEmbedBuilderModalBodyImageUrlFieldBodyImageUrl;

    @ConfigurationValue(key = "embed.embedBuilder.modal.footerIconUrl.modalTitle")
    private String embedEmbedBuilderModalFooterIconUrlModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.footerIconUrl.field.footerIconUrl")
    private String embedEmbedBuilderModalFooterIconUrlFieldFooterIconUrl;

    @ConfigurationValue(key = "embed.embedBuilder.modal.footerText.modalTitle")
    private String embedEmbedBuilderModalFooterTextModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.footerText.field.footerText")
    private String embedEmbedBuilderModalFooterTextFieldFooterText;

    @ConfigurationValue(key = "embed.embedBuilder.modal.footerTimestamp.modalTitle")
    private String embedEmbedBuilderModalFooterTimestampModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.footerTimestamp.field.footerTimestamp")
    private String embedEmbedBuilderModalFooterTimestampFieldFooterTimestamp;

    @ConfigurationValue(key = "embed.embedBuilder.modal.hexColor.modalTitle")
    private String embedEmbedBuilderModalHexColorModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.hexColor.field.hexColor")
    private String embedEmbedBuilderModalHexColorFieldHexColor;

    @Getter(AccessLevel.NONE)
    @ConfigurationValue(key = "embed.embedBuilder.modal.editField.modalTitle")
    private String embedEmbedBuilderModalEditFieldModalTitle;

    @ConfigurationValue(key = "embed.embedBuilder.modal.editField.field.fieldName")
    private String embedEmbedBuilderModalEditFieldFieldName;

    @ConfigurationValue(key = "embed.embedBuilder.modal.editField.field.fieldValue")
    private String embedEmbedBuilderModalEditFieldFieldValue;

    @ConfigurationValue(key = "embed.embedBuilder.modal.editField.field.fieldInline")
    private String embedEmbedBuilderModalEditFieldFieldInline;

    public EmbedCommandConfiguration() {
        EmbedCommandConfiguration.instance = this;
    }

    public String getEmbedEmbedBuilderButtonFieldSelectorEdit(final String fieldId) {
        return this.embedEmbedBuilderButtonFieldSelectorEdit.replace("<fieldId>", fieldId);
    }

    public String getEmbedEmbedBuilderModalEditFieldModalTitle(final String fieldId) {
        return this.embedEmbedBuilderModalEditFieldModalTitle.replace("<fieldId>", fieldId);
    }

}
