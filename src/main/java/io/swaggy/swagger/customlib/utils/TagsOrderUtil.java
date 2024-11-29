package io.swaggy.swagger.customlib.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagsOrderUtil {
    public static void sortTagList(OpenAPI openAPI, List<Tag> tags, List<String> customTags) {
        if (tags != null && !tags.isEmpty() && customTags != null && !customTags.isEmpty()) {
            Map<String, Tag> tagMap = new HashMap<>();
            for (Tag tag : tags) {
                tagMap.put(tag.getName(), tag);
            }

            List<Tag> sortedTags = new ArrayList<>();
            for (String tagName : customTags) {
                if (tagMap.containsKey(tagName)) {
                    sortedTags.add(tagMap.remove(tagName));
                }
            }
            sortedTags.addAll(tagMap.values());

            openAPI.setTags(sortedTags);
        }
    }
}
