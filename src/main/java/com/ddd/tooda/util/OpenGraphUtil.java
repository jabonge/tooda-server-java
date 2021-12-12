package com.ddd.tooda.util;

import com.ddd.tooda.domain.diary.model.DiaryLink;
import com.github.siyoon210.ogparser4j.OgParser;
import com.github.siyoon210.ogparser4j.OpenGraph;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenGraphUtil {

    @Getter
    @ToString
    public static class OGResponse {
        private String ogImage;
        private String ogTitle;
        private String ogDescription;
        private String ogUrl;

        @Builder
        public OGResponse(String ogImage, String ogTitle, String ogDescription, String ogUrl) {
            this.ogImage = ogImage;
            this.ogTitle = ogTitle;
            this.ogDescription = ogDescription;
            this.ogUrl = ogUrl;
        }

        public DiaryLink toEntity() {
            return new DiaryLink(null,this.ogImage,this.ogTitle,this.ogDescription,this.ogUrl,null);
        }
    }

    public static OGResponse getOgInfo(String targetUrl) {
        OgParser ogParser = new OgParser();

        try {
            OpenGraph openGraph = ogParser.getOpenGraphOf(targetUrl);

            OpenGraph.Content title = openGraph.getContentOf("title");
            OpenGraph.Content description = openGraph.getContentOf("description");
            OpenGraph.Content image = openGraph.getContentOf("image");
            OpenGraph.Content url = openGraph.getContentOf("url");

            String spliceDescription = description.getValue();

            if (spliceDescription != null && spliceDescription.length() > 30) {
                spliceDescription.substring(0,30);
            }

            return OGResponse.builder()
                    .ogDescription(spliceDescription)
                    .ogTitle(title.getValue())
                    .ogImage(image.getValue())
                    .ogUrl(url.getValue()).build();

        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            return null;
        }

    }
}
