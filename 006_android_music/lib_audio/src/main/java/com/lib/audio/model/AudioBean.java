package com.lib.audio.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * 1.歌曲实体
 * 2.引入greendao以后扩展了许多
 */
@Entity
public class AudioBean implements Serializable {

  private static final long serialVersionUID = -8849228294348905620L;

  @Generated(hash = 1701787808)
  public AudioBean(String id, @NotNull String mUrl, @NotNull String name, @NotNull String author,
                   @NotNull String album, @NotNull String albumInfo, @NotNull String albumPic,
                   @NotNull String totalTime) {
      this.id = id;
      this.mUrl = mUrl;
      this.name = name;
      this.author = author;
      this.album = album;
      this.albumInfo = albumInfo;
      this.albumPic = albumPic;
      this.totalTime = totalTime;
  }

  @Generated(hash = 1628963493) public AudioBean() {
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMUrl() {
    return this.mUrl;
  }

  public void setMUrl(String mUrl) {
    this.mUrl = mUrl;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAlbum() {
    return this.album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getAlbumPic() {
    return this.albumPic;
  }

  public void setAlbumPic(String albumPic) {
    this.albumPic = albumPic;
  }

  public String getAlbumInfo() {
    return this.albumInfo;
  }

  public void setAlbumInfo(String albumInfo) {
    this.albumInfo = albumInfo;
  }

  public String getTotalTime() {
    return this.totalTime;
  }

  public void setTotalTime(String totalTime) {
    this.totalTime = totalTime;
  }

  @Id
  public String id;
  //地址
  @NotNull @Unique
  public String mUrl;

  //歌名
  @NotNull public String name;

  //作者
  @NotNull public String author;

  //所属专辑
  @NotNull public String album;

  @NotNull public String albumInfo;

  //专辑封面
  @NotNull public String albumPic;

  //时长
  @NotNull public String totalTime;

  @Override public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!(other instanceof AudioBean)) {
      return false;
    }
    return ((AudioBean) other).id.equals(this.id);
  }
}
