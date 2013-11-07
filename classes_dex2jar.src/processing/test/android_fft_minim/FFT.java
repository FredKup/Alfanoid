package processing.test.android_fft_minim;

public class FFT
  extends FourierTransform
{
  private float[] coslookup;
  private int[] reverse;
  private float[] sinlookup;
  
  public FFT(int paramInt, float paramFloat)
  {
    super(paramInt, paramFloat);
    if ((paramInt & paramInt - 1) != 0) {
      throw new IllegalArgumentException("FFT: timeSize must be a power of two.");
    }
    buildReverseTable();
    buildTrigTables();
  }
  
  private void bitReverseComplex()
  {
    float[] arrayOfFloat1 = new float[this.real.length];
    float[] arrayOfFloat2 = new float[this.imag.length];
    for (int i = 0; i < this.real.length; i++)
    {
      arrayOfFloat1[i] = this.real[this.reverse[i]];
      arrayOfFloat2[i] = this.imag[this.reverse[i]];
    }
    this.real = arrayOfFloat1;
    this.imag = arrayOfFloat2;
  }
  
  private void bitReverseSamples(float[] paramArrayOfFloat, int paramInt)
  {
    for (int i = 0; i < this.timeSize; i++)
    {
      this.real[i] = paramArrayOfFloat[(paramInt + this.reverse[i])];
      this.imag[i] = 0.0F;
    }
  }
  
  private void buildReverseTable()
  {
    int i = this.timeSize;
    this.reverse = new int[i];
    this.reverse[0] = 0;
    int j = 1;
    int k = i / 2;
    while (j < i)
    {
      for (int m = 0; m < j; m++) {
        this.reverse[(m + j)] = (k + this.reverse[m]);
      }
      j <<= 1;
      k >>= 1;
    }
  }
  
  private void buildTrigTables()
  {
    int i = this.timeSize;
    this.sinlookup = new float[i];
    this.coslookup = new float[i];
    for (int j = 0; j < i; j++)
    {
      this.sinlookup[j] = ((float)Math.sin(-3.141593F / j));
      this.coslookup[j] = ((float)Math.cos(-3.141593F / j));
    }
  }
  
  private float cos(int paramInt)
  {
    return this.coslookup[paramInt];
  }
  
  private void fft()
  {
    int i = 1;
    while (i < this.real.length)
    {
      float f1 = cos(i);
      float f2 = sin(i);
      float f3 = 1.0F;
      float f4 = 0.0F;
      for (int j = 0; j < i; j++)
      {
        int k = j;
        while (k < this.real.length)
        {
          int m = k + i;
          float f6 = f3 * this.real[m] - f4 * this.imag[m];
          float f7 = f3 * this.imag[m] + f4 * this.real[m];
          this.real[k] -= f6;
          this.imag[k] -= f7;
          float[] arrayOfFloat1 = this.real;
          arrayOfFloat1[k] = (f6 + arrayOfFloat1[k]);
          float[] arrayOfFloat2 = this.imag;
          arrayOfFloat2[k] = (f7 + arrayOfFloat2[k]);
          k += i * 2;
        }
        float f5 = f3;
        f3 = f5 * f1 - f4 * f2;
        f4 = f5 * f2 + f4 * f1;
      }
      i *= 2;
    }
  }
  
  private float sin(int paramInt)
  {
    return this.sinlookup[paramInt];
  }
  
  protected void allocateArrays()
  {
    this.spectrum = new float[1 + this.timeSize / 2];
    this.real = new float[this.timeSize];
    this.imag = new float[this.timeSize];
  }
  
  public void forward(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length != this.timeSize) {
      return;
    }
    bitReverseSamples(paramArrayOfFloat, 0);
    fft();
    fillSpectrum();
  }
  
  public void forward(float[] paramArrayOfFloat, int paramInt)
  {
    if (paramArrayOfFloat.length - paramInt < this.timeSize) {
      return;
    }
    bitReverseSamples(paramArrayOfFloat, paramInt);
    fft();
    fillSpectrum();
  }
  
  public void forward(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if ((paramArrayOfFloat1.length != this.timeSize) || (paramArrayOfFloat2.length != this.timeSize)) {
      return;
    }
    setComplex(paramArrayOfFloat1, paramArrayOfFloat2);
    bitReverseComplex();
    fft();
    fillSpectrum();
  }
  
  public void inverse(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length > this.real.length) {}
    for (;;)
    {
      return;
      for (int i = 0; i < this.timeSize; i++)
      {
        float[] arrayOfFloat = this.imag;
        arrayOfFloat[i] = (-1.0F * arrayOfFloat[i]);
      }
      bitReverseComplex();
      fft();
      for (int j = 0; j < paramArrayOfFloat.length; j++) {
        paramArrayOfFloat[j] = (this.real[j] / this.real.length);
      }
    }
  }
  
  public void scaleBand(int paramInt, float paramFloat)
  {
    if (paramFloat < 0.0F) {}
    do
    {
      return;
      float[] arrayOfFloat1 = this.real;
      arrayOfFloat1[paramInt] = (paramFloat * arrayOfFloat1[paramInt]);
      float[] arrayOfFloat2 = this.imag;
      arrayOfFloat2[paramInt] = (paramFloat * arrayOfFloat2[paramInt]);
      float[] arrayOfFloat3 = this.spectrum;
      arrayOfFloat3[paramInt] = (paramFloat * arrayOfFloat3[paramInt]);
    } while ((paramInt == 0) || (paramInt == this.timeSize / 2));
    this.real[(this.timeSize - paramInt)] = this.real[paramInt];
    this.imag[(this.timeSize - paramInt)] = (-this.imag[paramInt]);
  }
  
  public void setBand(int paramInt, float paramFloat)
  {
    if (paramFloat < 0.0F) {}
    for (;;)
    {
      return;
      if ((this.real[paramInt] == 0.0F) && (this.imag[paramInt] == 0.0F))
      {
        this.real[paramInt] = paramFloat;
        this.spectrum[paramInt] = paramFloat;
      }
      while ((paramInt != 0) && (paramInt != this.timeSize / 2))
      {
        this.real[(this.timeSize - paramInt)] = this.real[paramInt];
        this.imag[(this.timeSize - paramInt)] = (-this.imag[paramInt]);
        return;
        float[] arrayOfFloat1 = this.real;
        arrayOfFloat1[paramInt] /= this.spectrum[paramInt];
        float[] arrayOfFloat2 = this.imag;
        arrayOfFloat2[paramInt] /= this.spectrum[paramInt];
        this.spectrum[paramInt] = paramFloat;
        float[] arrayOfFloat3 = this.real;
        arrayOfFloat3[paramInt] *= this.spectrum[paramInt];
        float[] arrayOfFloat4 = this.imag;
        arrayOfFloat4[paramInt] *= this.spectrum[paramInt];
      }
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.test.android_fft_minim.FFT
 * JD-Core Version:    0.7.0.1
 */