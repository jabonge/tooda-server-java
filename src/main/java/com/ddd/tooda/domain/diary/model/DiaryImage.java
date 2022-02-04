package com.ddd.tooda.domain.diary.model;


import com.ddd.tooda.common.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    Diary diary;

    public DiaryImage(String image) {
        this.image = image;
        setMeta();
    }

    public void setMeta() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(this.image));
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
        } catch (MalformedURLException ex) {
            throw new BadRequestException("올바르지 않은 URL 형식의 이미지입니다.");
        } catch (IOException ex) {
            throw new BadRequestException(ex.getLocalizedMessage());
        }
    }

    void setDiary(Diary diary){
        this.diary = diary;
    }


}
