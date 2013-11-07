package processing.test.android_fft_minim;

public abstract class FourierTransform
{
  protected static final int LINAVG = 1;
  protected static final int LOGAVG = 2;
  protected static final int NOAVG = 3;
  protected static final float TWO_PI = 6.283186F;
  protected float[] averages;
  protected int avgPerOctave;
  protected float bandWidth;
  protected float[] imag;
  protected int octaves;
  protected float[] real;
  protected int sampleRate;
  protected float[] spectrum;
  protected int timeSize;
  protected int whichAverage;
  
  FourierTransform(int paramInt, float paramFloat)
  {
    this.timeSize = paramInt;
    this.sampleRate = ((int)paramFloat);
    this.bandWidth = (2.0F / this.timeSize * (this.sampleRate / 2.0F));
    noAverages();
    allocateArrays();
  }
  
  protected abstract void allocateArrays();
  
  public int avgSize()
  {
    return this.averages.length;
  }
  
  public float calcAvg(float paramFloat1, float paramFloat2)
  {
    int i = freqToIndex(paramFloat1);
    int j = freqToIndex(paramFloat2);
    float f = 0.0F;
    for (int k = i; k <= j; k++) {
      f += this.spectrum[k];
    }
    return f / (1 + (j - i));
  }
  
  protected void fillSpectrum()
  {
    for (int i = 0; i < this.spectrum.length; i++) {
      this.spectrum[i] = ((float)Math.sqrt(this.real[i] * this.real[i] + this.imag[i] * this.imag[i]));
    }
    if (this.whichAverage == 1)
    {
      int n = this.spectrum.length / this.averages.length;
      for (int i1 = 0; i1 < this.averages.length; i1++)
      {
        float f4 = 0.0F;
        for (int i2 = 0; i2 < n; i2++)
        {
          int i3 = i2 + i1 * n;
          if (i3 >= this.spectrum.length) {
            break;
          }
          f4 += this.spectrum[i3];
        }
        float f5 = f4 / (i2 + 1);
        this.averages[i1] = f5;
      }
    }
    if (this.whichAverage == 2) {
      for (int j = 0; j < this.octaves; j++)
      {
        if (j == 0) {}
        for (float f1 = 0.0F;; f1 = this.sampleRate / 2 / (float)Math.pow(2.0D, this.octaves - j))
        {
          float f2 = (this.sampleRate / 2 / (float)Math.pow(2.0D, -1 + (this.octaves - j)) - f1) / this.avgPerOctave;
          float f3 = f1;
          for (int k = 0; k < this.avgPerOctave; k++)
          {
            int m = k + j * this.avgPerOctave;
            this.averages[m] = calcAvg(f3, f3 + f2);
            f3 += f2;
          }
        }
      }
    }
  }
  
  public abstract void forward(float[] paramArrayOfFloat);
  
  public void forward(float[] paramArrayOfFloat, int paramInt)
  {
    if (paramArrayOfFloat.length - paramInt < this.timeSize) {
      return;
    }
    float[] arrayOfFloat = new float[this.timeSize];
    System.arraycopy(paramArrayOfFloat, paramInt, arrayOfFloat, 0, arrayOfFloat.length);
    forward(arrayOfFloat);
  }
  
  public int freqToIndex(float paramFloat)
  {
    if (paramFloat < getBandWidth() / 2.0F) {
      return 0;
    }
    if (paramFloat > this.sampleRate / 2 - getBandWidth() / 2.0F) {
      return -1 + this.spectrum.length;
    }
    return Math.round(paramFloat / this.sampleRate * this.timeSize);
  }
  
  public float getAverageBandWidth(int paramInt)
  {
    if (this.whichAverage == 1) {
      return this.spectrum.length / this.averages.length * getBandWidth();
    }
    if (this.whichAverage == 2)
    {
      int i = paramInt / this.avgPerOctave;
      if (i == 0) {}
      for (float f = 0.0F;; f = this.sampleRate / 2 / (float)Math.pow(2.0D, this.octaves - i)) {
        return (this.sampleRate / 2 / (float)Math.pow(2.0D, -1 + (this.octaves - i)) - f) / this.avgPerOctave;
      }
    }
    return 0.0F;
  }
  
  public float getAverageCenterFrequency(int paramInt)
  {
    if (this.whichAverage == 1)
    {
      int k = this.spectrum.length / this.averages.length;
      return indexToFreq(paramInt * k + k / 2);
    }
    if (this.whichAverage == 2)
    {
      int i = paramInt / this.avgPerOctave;
      int j = paramInt % this.avgPerOctave;
      if (i == 0) {}
      for (float f1 = 0.0F;; f1 = this.sampleRate / 2 / (float)Math.pow(2.0D, this.octaves - i))
      {
        float f2 = (this.sampleRate / 2 / (float)Math.pow(2.0D, -1 + (this.octaves - i)) - f1) / this.avgPerOctave;
        return f1 + f2 * j + f2 / 2.0F;
      }
    }
    return 0.0F;
  }
  
  public float getAvg(int paramInt)
  {
    if (this.averages.length > 0) {
      return this.averages[paramInt];
    }
    return 0.0F;
  }
  
  public float getBand(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    if (paramInt > -1 + this.spectrum.length) {
      paramInt = -1 + this.spectrum.length;
    }
    return this.spectrum[paramInt];
  }
  
  public float getBandWidth()
  {
    return this.bandWidth;
  }
  
  public float getFreq(float paramFloat)
  {
    return getBand(freqToIndex(paramFloat));
  }
  
  public float[] getSpectrumImaginary()
  {
    return this.imag;
  }
  
  public float[] getSpectrumReal()
  {
    return this.real;
  }
  
  public float indexToFreq(int paramInt)
  {
    float f = getBandWidth();
    if (paramInt == 0) {
      return f * 0.25F;
    }
    if (paramInt == -1 + this.spectrum.length) {
      return this.sampleRate / 2 - f / 2.0F + f * 0.25F;
    }
    return f * paramInt;
  }
  
  public abstract void inverse(float[] paramArrayOfFloat);
  
  public void inverse(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3)
  {
    setComplex(paramArrayOfFloat1, paramArrayOfFloat2);
    inverse(paramArrayOfFloat3);
  }
  
  public void linAverages(int paramInt)
  {
    if (paramInt > this.spectrum.length / 2) {
      return;
    }
    this.averages = new float[paramInt];
    this.whichAverage = 1;
  }
  
  public void logAverages(int paramInt1, int paramInt2)
  {
    float f = this.sampleRate / 2.0F;
    for (this.octaves = 1;; this.octaves = (1 + this.octaves))
    {
      f /= 2.0F;
      if (f <= paramInt1) {
        break;
      }
    }
    this.avgPerOctave = paramInt2;
    this.averages = new float[paramInt2 * this.octaves];
    this.whichAverage = 2;
  }
  
  public void noAverages()
  {
    this.averages = new float[0];
    this.whichAverage = 3;
  }
  
  public abstract void scaleBand(int paramInt, float paramFloat);
  
  public void scaleFreq(float paramFloat1, float paramFloat2)
  {
    scaleBand(freqToIndex(paramFloat1), paramFloat2);
  }
  
  public abstract void setBand(int paramInt, float paramFloat);
  
  protected void setComplex(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if ((this.real.length != paramArrayOfFloat1.length) && (this.imag.length != paramArrayOfFloat2.length)) {
      return;
    }
    System.arraycopy(paramArrayOfFloat1, 0, this.real, 0, paramArrayOfFloat1.length);
    System.arraycopy(paramArrayOfFloat2, 0, this.imag, 0, paramArrayOfFloat2.length);
  }
  
  public void setFreq(float paramFloat1, float paramFloat2)
  {
    setBand(freqToIndex(paramFloat1), paramFloat2);
  }
  
  public int specSize()
  {
    return this.spectrum.length;
  }
  
  public int timeSize()
  {
    return this.timeSize;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.test.android_fft_minim.FourierTransform
 * JD-Core Version:    0.7.0.1
 */