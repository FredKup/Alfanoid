package processing.test.android_fft_minim;

import android.media.AudioRecord;
import processing.core.PApplet;

public class android_fft_minim
  extends PApplet
{
  int MAX_FREQ = this.RECORDER_SAMPLERATE / 2;
  final int PEAK_THRESH = 20;
  final int RECORDER_AUDIO_ENCODING = 2;
  final int RECORDER_CHANNELS = 16;
  int RECORDER_SAMPLERATE = 44100;
  boolean aRecStarted = false;
  AudioRecord audioRecord = null;
  short[] buffer = null;
  int bufferReadResult = 0;
  int bufferSize = 2048;
  int drawBaseLine = 0;
  float drawScaleH = 1.5F;
  float drawScaleW = 1.0F;
  int drawStepW = 2;
  FFT fft = null;
  float[] fftRealArray = null;
  int mainFreq = 0;
  float maxFreqToDraw = 2500.0F;
  int minBufferSize = 0;
  float volume = 0.0F;
  
  public void draw()
  {
    background(128);
    fill(0);
    noStroke();
    if (this.aRecStarted)
    {
      this.bufferReadResult = this.audioRecord.read(this.buffer, 0, this.bufferSize);
      this.volume = 0.0F;
      for (int i = 0; i < this.bufferReadResult; i++)
      {
        this.fftRealArray[i] = (this.buffer[i] / 32767.0F);
        this.volume += Math.abs(this.fftRealArray[i]);
      }
      this.volume = ((float)Math.log10(this.volume / this.bufferReadResult));
      for (int j = 0; j < this.bufferReadResult / 2; j++)
      {
        float f6 = (float)(0.5D + 0.5D * Math.cos(3.141592653589793D * j / (this.bufferReadResult / 2)));
        if (j > this.bufferReadResult / 2) {
          f6 = 0.0F;
        }
        float[] arrayOfFloat1 = this.fftRealArray;
        int i2 = j + this.bufferReadResult / 2;
        arrayOfFloat1[i2] = (f6 * arrayOfFloat1[i2]);
        float[] arrayOfFloat2 = this.fftRealArray;
        int i3 = this.bufferReadResult / 2 - j;
        arrayOfFloat2[i3] = (f6 * arrayOfFloat2[i3]);
      }
      this.fftRealArray[0] = 0.0F;
      this.fft.forward(this.fftRealArray);
      fill(255);
      stroke(100);
      pushMatrix();
      rotate(radians(90.0F));
      translate(-3 + this.drawBaseLine, 0.0F);
      textAlign(21, 3);
      for (float f1 = -1 + this.RECORDER_SAMPLERATE / 2; f1 > 0.0F; f1 -= 150.0F)
      {
        int i1 = -(int)(this.fft.freqToIndex(f1) * this.drawScaleW);
        line(-this.displayHeight, i1, 0.0F, i1);
        text(Math.round(f1) + " Hz", 10.0F, i1);
      }
      popMatrix();
      noStroke();
      float f2 = 0.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;
      int k = 0;
      for (int m = 0; m < this.fft.specSize(); m++)
      {
        f3 += this.fft.getBand(m);
        if (m % this.drawStepW == 0)
        {
          float f5 = f3 / this.drawStepW;
          int n = m - this.drawStepW;
          stroke(255);
          line(n * this.drawScaleW, this.drawBaseLine, n * this.drawScaleW, this.drawBaseLine - f2 * this.drawScaleH);
          if (f5 - f2 > 20.0F)
          {
            stroke(255.0F, 0.0F, 0.0F);
            fill(255.0F, 128.0F, 128.0F);
            ellipse(m * this.drawScaleW, this.drawBaseLine - f5 * this.drawScaleH, 20.0F, 20.0F);
            stroke(255);
            fill(255);
            if (f5 > f4)
            {
              f4 = f5;
              k = m;
            }
          }
          line(n * this.drawScaleW, this.drawBaseLine - f2 * this.drawScaleH, m * this.drawScaleW, this.drawBaseLine - f5 * this.drawScaleH);
          f2 = f5;
          f3 = 0.0F;
        }
      }
      if (k - this.drawStepW > 0)
      {
        fill(255.0F, 0.0F, 0.0F);
        ellipse(k * this.drawScaleW, this.drawBaseLine - f4 * this.drawScaleH, 20.0F, 20.0F);
        fill(0.0F, 0.0F, 255.0F);
        text(" " + this.fft.indexToFreq(k - this.drawStepW / 2) + "Hz", 25.0F + k * this.drawScaleW, this.drawBaseLine - f4 * this.drawScaleH);
      }
      fill(255);
      pushMatrix();
      translate(this.displayWidth / 2, this.drawBaseLine);
      text("buffer readed: " + this.bufferReadResult, 20.0F, 80.0F);
      text("fft spec size: " + this.fft.specSize(), 20.0F, 100.0F);
      text("volume: " + this.volume, 20.0F, 120.0F);
      popMatrix();
    }
    for (;;)
    {
      fill(255);
      pushMatrix();
      translate(0.0F, this.drawBaseLine);
      text("sample rate: " + this.RECORDER_SAMPLERATE + " Hz", 20.0F, 80.0F);
      text("displaying freq: 0 Hz  to  " + this.maxFreqToDraw + " Hz", 20.0F, 100.0F);
      text("buffer size: " + this.bufferSize, 20.0F, 120.0F);
      popMatrix();
      return;
      fill(255.0F, 0.0F, 0.0F);
      text("AUDIO RECORD NOT INITIALIZED!!!", 100.0F, this.height / 2);
    }
  }
  
  public void setup()
  {
    this.drawBaseLine = (-150 + this.displayHeight);
    this.minBufferSize = AudioRecord.getMinBufferSize(this.RECORDER_SAMPLERATE, 16, 2);
    if (this.minBufferSize == -2)
    {
      this.RECORDER_SAMPLERATE = 8000;
      this.MAX_FREQ = (this.RECORDER_SAMPLERATE / 2);
      this.bufferSize = (2 << (int)(log(this.RECORDER_SAMPLERATE) / log(2.0F) - 1.0F));
    }
    for (;;)
    {
      this.buffer = new short[this.bufferSize];
      this.audioRecord = new AudioRecord(6, this.RECORDER_SAMPLERATE, 16, 2, this.bufferSize);
      if ((this.audioRecord != null) && (this.audioRecord.getState() == 1)) {}
      try
      {
        this.audioRecord.startRecording();
        this.aRecStarted = true;
        if (this.aRecStarted)
        {
          this.bufferReadResult = this.audioRecord.read(this.buffer, 0, this.bufferSize);
          if (this.bufferReadResult % 2 != 0) {
            this.bufferReadResult = (2 << (int)(log(this.bufferReadResult) / log(2.0F)));
          }
          this.fft = new FFT(this.bufferReadResult, this.RECORDER_SAMPLERATE);
          this.fftRealArray = new float[this.bufferReadResult];
          this.drawScaleW = (this.drawScaleW * this.displayWidth / this.fft.freqToIndex(this.maxFreqToDraw));
        }
        fill(0);
        noStroke();
        return;
        this.bufferSize = this.minBufferSize;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          this.aRecStarted = false;
        }
      }
    }
  }
  
  public int sketchHeight()
  {
    return this.displayHeight;
  }
  
  public int sketchWidth()
  {
    return this.displayWidth;
  }
  
  public void stop()
  {
    this.audioRecord.stop();
    this.audioRecord.release();
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.test.android_fft_minim.android_fft_minim
 * JD-Core Version:    0.7.0.1
 */