package android.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR})
public @interface TargetApi
{
  int value();
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     android.annotation.TargetApi
 * JD-Core Version:    0.7.0.1
 */