package com.lib.audio.events;


import com.lib.audio.core.CustomMediaPlayer;

public class AudioProgressEvent {

  public CustomMediaPlayer.Status mStatus;
  public int progress;
  public int maxLength;

  public AudioProgressEvent(CustomMediaPlayer.Status status, int progress, int maxLength) {
    this.mStatus = status;
    this.progress = progress;
    this.maxLength = maxLength;
  }
}
